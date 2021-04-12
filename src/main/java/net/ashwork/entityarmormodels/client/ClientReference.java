/*
 * Entity Armor Models
 * Copyright (c) 2021-2021 ChampionAsh5357.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.ashwork.entityarmormodels.client;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import com.mojang.datafixers.util.Pair;

import net.ashwork.entityarmormodels.client.renderer.ArmorRendererManager;
import net.ashwork.entityarmormodels.client.renderer.entity.layers.LayerRendererData;
import net.ashwork.entityarmormodels.client.renderer.entity.model.EntityModelData;
import net.ashwork.entityarmormodels.client.renderer.entity.model.ExtCreeperModel;
import net.ashwork.entityarmormodels.client.renderer.entity.model.ExtPigModel;
import net.ashwork.entityarmormodels.client.renderer.entity.model.LivingEntityModel;
import net.ashwork.entityarmormodels.data.client.ArmorRenderers;
import net.ashwork.entityarmormodels.util.Helper;
import net.ashwork.entityarmormodels.util.ISidedReference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.data.DataGenerator;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;

public final class ClientReference implements ISidedReference {

    private static final String DISABLE_LAYERS = "disable_layers";
    private static final String REGISTER_MODELS = "register_models";

    private static ClientReference instance;
    private final ArmorRendererManager armorRendererManager;
    private Map<EntityType<?>, Byte> disabledLayers;
    private Map<EntityType<?>, EntityModelData<?, ?>> modelData;
    private Minecraft mc;

    public ClientReference() {
        instance = this;
        this.armorRendererManager = new ArmorRendererManager();
        this.disabledLayers = new HashMap<>();
        this.modelData = new HashMap<>();
    }

    public static ClientReference getInstance() {
        return instance;
    }

    public ArmorRendererManager getArmorRendererManager() {
        return armorRendererManager;
    }

    @Override
    public void setup(IEventBus mod, IEventBus forge) {
        mod.addListener(this::construct);
        mod.addListener(this::clientSetup);
        mod.addListener(this::proccessIMC);
        mod.addListener(this::loadComplete);
        mod.addListener(this::dataGen);
    }

    @SuppressWarnings("unchecked")
    private void clientSetup(FMLClientSetupEvent event) {
        constructModelData(CreeperEntity.class, ExtCreeperModel.class, EntityType.CREEPER, ExtCreeperModel::new, (byte) 0b1100);
        constructModelData(PigEntity.class, ExtPigModel.class, EntityType.PIG, ExtPigModel::new, (byte) 0b1100);
    }

    private <T extends LivingEntity, M extends LivingEntityModel<T>> void constructModelData(Class<T> entityClass, Class<M> modelClass, EntityType<T> type, Function<Float, M> modelFactory, byte disabledLayers) {
        Pair<EntityModelData<T, M>, Byte> data = EntityModelData.Builder.of(entityClass, modelClass)
                .setPartVisibility(M::setPartVisibility)
                .translateToHand(M::translateToHand)
                .getRandomModelPart(M::getRandomModelPart)
                .translateAndRotateHead(M::translateAndRotateHead)
                .build(modelFactory);
        this.modelData.put(type, data.getFirst());
        this.disabledLayers.put(type, (byte) (data.getSecond() | disabledLayers));
    }

    private void construct(FMLConstructModEvent event) {
        this.mc = Minecraft.getInstance();
        event.enqueueWork(() -> {
            ((IReloadableResourceManager) this.mc().getResourceManager()).registerReloadListener(this.getArmorRendererManager());
        });
    }

    private void proccessIMC(InterModProcessEvent event) {
        event.getIMCStream(str -> str.equals(DISABLE_LAYERS)).forEach(msg -> {
            Map<EntityType<?>, Byte> disables = Helper.getMessageData(msg);
            disables.forEach((type, b) -> {
                if (this.disabledLayers.putIfAbsent(type, b) != null)
                    this.disabledLayers.computeIfPresent(type, (t, by) -> (byte) (by | b));
            });
        });
        event.getIMCStream(str -> str.equals(REGISTER_MODELS)).forEach(msg -> {
            Map<EntityType<?>, EntityModelData<?, ?>> data = Helper.getMessageData(msg);
            data.forEach((type, d) -> {
                if (this.modelData.putIfAbsent(type, d) != null)
                    throw new IllegalStateException("Model Data for " + type.getRegistryName() + " has already been added.");
            });
        });
    }

    @SuppressWarnings("resource")
    private void loadComplete(FMLLoadCompleteEvent event) {
        this.mc().getEntityRenderDispatcher().renderers.entrySet().stream()
        .filter(entry -> this.modelData.containsKey(entry.getKey()))
        .filter(entry -> entry.getValue() instanceof LivingRenderer<?, ?>)
        .forEach(entry -> {
            EntityType<?> type = entry.getKey();
            @SuppressWarnings("unchecked")
            LayerRendererData<?, ?> layer = new LayerRendererData<>((LivingRenderer<LivingEntity, EntityModel<LivingEntity>>) entry.getValue(), (EntityModelData<LivingEntity, EntityModel<LivingEntity>>) this.modelData.get(type), this.disabledLayers.getOrDefault(type, (byte) 0));
            this.getArmorRendererManager().registerCallback(type, layer.getCallback());
        });
        this.modelData = null;
        this.disabledLayers = null;
        this.getArmorRendererManager().handleInitialCallbacks();
    }

    private void dataGen(GatherDataEvent event) {
        if (event.includeClient()) {
            DataGenerator generator = event.getGenerator();
            generator.addProvider(new ArmorRenderers(generator));
        }
    }

    private Minecraft mc() {
        return Objects.requireNonNull(mc, "Minecraft instance has not been initialized yet.");
    }
}
