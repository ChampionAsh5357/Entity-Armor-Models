/*
 * Entity Armor Models
 * Copyright (c) 2021-2021 ChampionAsh5357.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.ashwork.entityarmormodels.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;

public abstract class ExtQuadrupedModel<T extends LivingEntity> extends ExtAgeableModel<T> {

    protected ModelRenderer head;
    protected ModelRenderer body;
    protected ModelRenderer leg0;
    protected ModelRenderer leg1;
    protected ModelRenderer leg2;
    protected ModelRenderer leg3;

    protected ExtQuadrupedModel(float inflation, int texWidth, int texHeight, boolean scaleHead, float yHeadOffset, float zHeadOffset, float babyHeadScale, float babyBodyScale, float bodyYOffset, float yLegHeight) {
        super(texWidth, texHeight, scaleHead, yHeadOffset, zHeadOffset, babyHeadScale, babyBodyScale, bodyYOffset);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.addBox(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F, inflation);
        this.head.setPos(0.0F, 18 - yLegHeight, -6.0F);
        this.body = new ModelRenderer(this, 28, 8);
        this.body.addBox(-5.0F, -10.0F, -7.0F, 10.0F, 16.0F, 8.0F, inflation);
        this.body.setPos(0.0F, 17 - yLegHeight, 2.0F);
        this.leg0 = new ModelRenderer(this, 0, 16);
        this.leg0.addBox(-2.0F, 0.0F, -2.0F, 4.0F, yLegHeight, 4.0F, inflation);
        this.leg0.setPos(-3.0F, 24 - yLegHeight, 7.0F);
        this.leg1 = new ModelRenderer(this, 0, 16);
        this.leg1.addBox(-2.0F, 0.0F, -2.0F, 4.0F, yLegHeight, 4.0F, inflation);
        this.leg1.setPos(3.0F, 24 - yLegHeight, 7.0F);
        this.leg2 = new ModelRenderer(this, 0, 16);
        this.leg2.addBox(-2.0F, 0.0F, -2.0F, 4.0F, yLegHeight, 4.0F, inflation);
        this.leg2.setPos(-3.0F, 24 - yLegHeight, -5.0F);
        this.leg3 = new ModelRenderer(this, 0, 16);
        this.leg3.addBox(-2.0F, 0.0F, -2.0F, 4.0F, yLegHeight, 4.0F, inflation);
        this.leg3.setPos(3.0F, 24 - yLegHeight, -5.0F);
    }

    @Override
    protected Iterable<ModelRenderer> headParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    protected Iterable<ModelRenderer> bodyParts() {
        return ImmutableList.of(this.body, this.leg0, this.leg1, this.leg2, this.leg3);
    }

    @Override
    public void setPartVisibility(EquipmentSlotType slot) {
        switch(slot) {
        case HEAD:
            this.head.visible = true;
            this.body.visible = false;
            this.leg0.visible = false;
            this.leg1.visible = false;
            this.leg2.visible = false;
            this.leg3.visible = false;
            break;
        case CHEST:
            this.head.visible = false;
            this.body.visible = true;
            this.leg0.visible = false;
            this.leg1.visible = false;
            this.leg2.visible = false;
            this.leg3.visible = false;
            break;
        case LEGS:
            this.head.visible = false;
            this.body.visible = false;
            this.leg0.visible = true;
            this.leg1.visible = true;
            this.leg2.visible = true;
            this.leg3.visible = true;
            break;
        case FEET:
            this.head.visible = false;
            this.body.visible = false;
            this.leg0.visible = true;
            this.leg1.visible = true;
            this.leg2.visible = true;
            this.leg3.visible = true;
            break;
        default:
            break;
        }
    }

    @Override
    public void translateToHand(HandSide hand, MatrixStack matrixStack) {
        // TODO: Implement, might be able to do here
    }

    @Override
    public ModelRenderer getHead() {
        return this.head;
    }

    @Override
    public void setupAnim(T entity, float animationPosition, float animationSpeed, float bob, float yRotation, float xRotation) {
        this.head.xRot = xRotation * ((float)Math.PI / 180F);
        this.head.yRot = yRotation * ((float)Math.PI / 180F);
        this.body.xRot = ((float)Math.PI / 2F);
        this.leg0.xRot = MathHelper.cos(animationPosition * 0.6662F) * 1.4F * animationSpeed;
        this.leg1.xRot = MathHelper.cos(animationPosition * 0.6662F + (float)Math.PI) * 1.4F * animationSpeed;
        this.leg2.xRot = MathHelper.cos(animationPosition * 0.6662F + (float)Math.PI) * 1.4F * animationSpeed;
        this.leg3.xRot = MathHelper.cos(animationPosition * 0.6662F) * 1.4F * animationSpeed;
    }
}
