/*
 * Entity Armor Models
 * Copyright (c) 2021-2021 ChampionAsh5357.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.ashwork.entityarmormodels.client.renderer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;

import net.ashwork.entityarmormodels.util.Helper;
import net.ashwork.entityarmormodels.util.MultiJsonReloadListener;
import net.minecraft.entity.EntityType;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraftforge.registries.ForgeRegistries;

public class ArmorRendererManager extends MultiJsonReloadListener {

    public static final String NAME = "entity_armor_renderers";
    private boolean isInitialized;
    private final Map<EntityType<?>, RendererInformation> armorsToRender;
    private final Map<EntityType<?>, Consumer<RendererInformation>> callbacks;

    public ArmorRendererManager() {
        super(Helper.GSON, NAME);
        this.armorsToRender = new HashMap<>();
        this.callbacks = new HashMap<>();
    }

    @Override
    protected void apply(Map<ResourceLocation, List<JsonElement>> resources, IResourceManager manager, IProfiler profiler) {
        this.armorsToRender.clear();
        resources.forEach((loc, jsons) -> {
            @Nullable EntityType<?> type = ForgeRegistries.ENTITIES.getValue(loc);
            if (type == null) LOGGER.error("Entity type {} does not exist, ignoring any associated data", loc);
            else jsons.forEach(json -> parseRendererInformation(type, JSONUtils.convertToJsonObject(json, "renderer_information")));
            this.callbacks.getOrDefault(type, info -> {}).accept(this.armorsToRender.getOrDefault(type, RendererInformation.EMPTY));
        });
    }

    public void handleInitialCallbacks() {
        if (this.isInitialized) throw new IllegalStateException("Callbacks have already been initialized, somebody is doing something wrong.");
        this.armorsToRender.forEach((type, info) -> this.callbacks.getOrDefault(type, a -> {}).accept(info));
        this.isInitialized = true;
    }

    private void parseRendererInformation(EntityType<?> type, JsonObject json) {
        boolean replace = JSONUtils.getAsBoolean(json, "replace", false);
        json.remove("replace");
        RendererInformation.CODEC.parse(JsonOps.INSTANCE, json)
        .resultOrPartial(Util.prefix("Error reading renderer information for {}: ", str -> LOGGER.error(str, type.getRegistryName())))
        .ifPresent(info -> this.armorsToRender.computeIfAbsent(type, e -> new RendererInformation()).merge(info, replace));
    }

    public void registerCallback(EntityType<?> type, BiConsumer<EntityType<?>, RendererInformation> consumer) {
        if (this.callbacks.putIfAbsent(type, info -> consumer.accept(type, info)) != null)
            throw new IllegalArgumentException("Entity type " + type.getRegistryName() + " has already been registered with a callback.");
    }
}
