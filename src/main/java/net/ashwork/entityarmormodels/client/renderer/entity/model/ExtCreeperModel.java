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

public class ExtCreeperModel<T extends LivingEntity> extends ExtSegmentedModel<T> {

    protected final ModelRenderer head;
    protected final ModelRenderer hair;
    protected final ModelRenderer body;
    protected final ModelRenderer leg0;
    protected final ModelRenderer leg1;
    protected final ModelRenderer leg2;
    protected final ModelRenderer leg3;

    public ExtCreeperModel(float inflation) {
        this(inflation, 64, 32);
    }

    protected ExtCreeperModel(float inflation, int texWidth, int texHeight) {
        super(texWidth, texHeight);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.addBox(-4f, -8f, -4f, 8f, 8f, 8f, inflation);
        this.head.setPos(0f, 6f, 0f);
        this.hair = new ModelRenderer(this, 32, 0);
        this.hair.addBox(-4f, -8f, -4f, 8f, 8f, 8f, inflation + 0.5F);
        this.hair.setPos(0f, 6f, 0f);
        this.body = new ModelRenderer(this, 16, 16);
        this.body.addBox(-4f, 0f, -2f, 8f, 12f, 4f, inflation);
        this.body.setPos(0f, 6f, 0f);
        this.leg0 = new ModelRenderer(this, 0, 16);
        this.leg0.addBox(-2f, 0f, -2f, 4f, 6f, 4f, inflation);
        this.leg0.setPos(-2f, 18f, 4f);
        this.leg1 = new ModelRenderer(this, 0, 16);
        this.leg1.addBox(-2f, 0f, -2f, 4f, 6f, 4f, inflation);
        this.leg1.setPos(2f, 18f, 4f);
        this.leg2 = new ModelRenderer(this, 0, 16);
        this.leg2.addBox(-2f, 0f, -2f, 4f, 6f, 4f, inflation);
        this.leg2.setPos(-2f, 18f, -4f);
        this.leg3 = new ModelRenderer(this, 0, 16);
        this.leg3.addBox(-2f, 0f, -2f, 4f, 6f, 4f, inflation);
        this.leg3.setPos(2f, 18f, -4f);
    }

    @Override
    public Iterable<ModelRenderer> parts() {
        return ImmutableList.of(this.head, this.body, this.leg0, this.leg1, this.leg2, this.leg3);
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
            this.body.visible = true;
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
        // TODO: Implement
    }

    @Override
    public ModelRenderer getHead() {
        return this.head;
    }

    @Override
    public void setupAnim(T entity, float animationPosition, float animationSpeed, float bob, float yRotation, float xRotation) {
        this.head.xRot = xRotation * ((float)Math.PI / 180F);
        this.head.yRot = yRotation * ((float)Math.PI / 180F);
        this.leg0.xRot = MathHelper.cos(animationPosition * 0.6662F) * 1.4F * animationSpeed;
        this.leg1.xRot = MathHelper.cos(animationPosition * 0.6662F + (float)Math.PI) * 1.4F * animationSpeed;
        this.leg2.xRot = MathHelper.cos(animationPosition * 0.6662F + (float)Math.PI) * 1.4F * animationSpeed;
        this.leg3.xRot = MathHelper.cos(animationPosition * 0.6662F) * 1.4F * animationSpeed;
    }
}
