/*
 * Entity Armor Models
 * Copyright (c) 2021-2021 ChampionAsh5357.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.ashwork.entityarmormodels.client.renderer.entity.model;

import java.util.function.Function;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

public abstract class ExtSegmentedModel<T extends LivingEntity> extends LivingEntityModel<T> {

    protected ExtSegmentedModel(int texWidth, int texHeight) {
        this(RenderType::entityCutoutNoCull, texWidth, texHeight);
    }

    protected ExtSegmentedModel(Function<ResourceLocation, RenderType> renderType, int texWidth, int texHeight) {
        super(renderType, texWidth, texHeight);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder builder, int packedLight, int overlayCoords, float red, float green, float blue, float alpha) {
        this.parts().forEach(renderer -> renderer.render(matrixStack, builder, packedLight, overlayCoords, red, green, blue, alpha));
    }

    public abstract Iterable<ModelRenderer> parts();
}
