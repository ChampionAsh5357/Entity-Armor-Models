/*
 * Entity Armor Models
 * Copyright (c) 2021-2021 ChampionAsh5357.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.ashwork.entityarmormodels.client.renderer.entity.layers;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.BiConsumer;

import net.ashwork.entityarmormodels.client.renderer.RendererInformation;
import net.ashwork.entityarmormodels.client.renderer.entity.model.EntityModelData;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class LayerRendererData<T extends LivingEntity, M extends EntityModel<T>> {

    private static final Field LAYERS = ObfuscationReflectionHelper.findField(LivingRenderer.class, "field_177097_h");
    private final List<LayerRenderer<T, M>> layers;
    private final EntityArmorLayer<T, M, EntityModel<T>> armorLayer;

    public LayerRendererData(LivingRenderer<T, M> renderer, EntityModelData<T, EntityModel<T>> modelData, byte disabledLayers) {
        this.armorLayer = new EntityArmorLayer<>(renderer, this, modelData, disabledLayers);
        this.layers = getLayers(renderer);
        this.updateLayerLocation(this.armorLayer.shouldLayerFirst());
    }

    public BiConsumer<EntityType<?>, RendererInformation> getCallback() {
        return this.armorLayer::setRenderInfo;
    }

    protected void updateLayerLocation(boolean shouldLayerFirst) {
        this.layers.remove(armorLayer);
        if (shouldLayerFirst) this.layers.add(0, armorLayer);
        else this.layers.add(armorLayer);
    }

    @SuppressWarnings("unchecked")
    private static <V extends LivingEntity, X extends EntityModel<V>> List<LayerRenderer<V, X>> getLayers(LivingRenderer<V, X> renderer) {
        try {
            return (List<LayerRenderer<V, X>>) LAYERS.get(renderer);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
