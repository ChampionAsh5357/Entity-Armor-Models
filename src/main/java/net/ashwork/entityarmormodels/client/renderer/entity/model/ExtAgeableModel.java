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

public abstract class ExtAgeableModel<T extends LivingEntity> extends LivingEntityModel<T> {

    private final boolean scaleHead;
    private final float yHeadOffset;
    private final float zHeadOffset;
    private final float babyHeadScale;
    private final float babyBodyScale;
    private final float bodyYOffset;

    protected ExtAgeableModel(int texWidth, int texHeight) {
        this(texWidth, texHeight, false, 5f, 2f);
    }

    protected ExtAgeableModel(int texWidth, int texHeight, boolean scaleHead, float yHeadOffset, float zHeadOffset) {
        this(texWidth, texHeight, scaleHead, yHeadOffset, zHeadOffset, 2f, 2f, 24f);
    }

    protected ExtAgeableModel(int texWidth, int texHeight, boolean scaleHead, float yHeadOffset, float zHeadOffset, float babyHeadScale, float babyBodyScale, float bodyYOffset) {
        this(RenderType::entityCutoutNoCull, texWidth, texHeight, scaleHead, yHeadOffset, zHeadOffset, babyHeadScale, babyBodyScale, bodyYOffset);
    }

    protected ExtAgeableModel(Function<ResourceLocation, RenderType> renderType, int texWidth, int texHeight, boolean scaleHead, float yHeadOffset, float zHeadOffset, float babyHeadScale, float babyBodyScale, float bodyYOffset) {
        super(renderType, texWidth, texHeight);
        this.scaleHead = scaleHead;
        this.yHeadOffset = yHeadOffset;
        this.zHeadOffset = zHeadOffset;
        this.babyHeadScale = babyHeadScale;
        this.babyBodyScale = babyBodyScale;
        this.bodyYOffset = bodyYOffset;
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder builder, int packedLight, int overlayCoords, float red, float green, float blue, float alpha) {
        if (this.young) {
            matrixStack.pushPose();
            if (this.scaleHead) {
                float headScale = 1.5f / this.babyHeadScale;
                matrixStack.scale(headScale, headScale, headScale);
            }

            matrixStack.translate(0d, this.yHeadOffset / 16f, this.zHeadOffset / 16f);
            this.headParts().forEach(renderer -> renderer.render(matrixStack, builder, packedLight, overlayCoords, red, green, blue, alpha));
            matrixStack.popPose();
            matrixStack.pushPose();
            float bodyScale = 1f / this.babyBodyScale;
            matrixStack.scale(bodyScale, bodyScale, bodyScale);
            matrixStack.translate(0d, this.bodyYOffset / 16f, 0d);
            this.bodyParts().forEach(renderer -> renderer.render(matrixStack, builder, packedLight, overlayCoords, red, green, blue, alpha));
            matrixStack.popPose();
        } else {
            this.headParts().forEach(renderer -> renderer.render(matrixStack, builder, packedLight, overlayCoords, red, green, blue, alpha));
            this.bodyParts().forEach(renderer -> renderer.render(matrixStack, builder, packedLight, overlayCoords, red, green, blue, alpha));
        }
    }

    // TODO: When entity requires scaled head, check to see if still works
    @Override
    public void translateAndRotateHead(MatrixStack matrixStack, boolean isChild) {
        if (isChild) matrixStack.translate(0d, this.yHeadOffset / 16f, this.zHeadOffset / 16f);
        super.translateAndRotateHead(matrixStack, isChild);
    }

    protected abstract Iterable<ModelRenderer> headParts();

    protected abstract Iterable<ModelRenderer> bodyParts();
}
