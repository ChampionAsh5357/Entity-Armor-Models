/*
 * Entity Armor Models
 * Copyright (c) 2021-2021 ChampionAsh5357.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.ashwork.entityarmormodels.client.renderer.entity.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;

public abstract class LivingEntityModel<T extends LivingEntity> extends EntityModel<T> {

    protected final List<ModelRenderer> cubes = new ArrayList<>();

    protected LivingEntityModel(int texWidth, int texHeight) {
        this(RenderType::entityCutoutNoCull, texWidth, texHeight);
    }

    protected LivingEntityModel(Function<ResourceLocation, RenderType> renderType, int texWidth, int texHeight) {
        super(renderType);
        this.texWidth = texWidth;
        this.texHeight = texHeight;
    }

    public abstract void setPartVisibility(EquipmentSlotType slot);

    public abstract void translateToHand(HandSide hand, MatrixStack matrixStack);

    protected abstract ModelRenderer getHead();

    public void translateAndRotateHead(MatrixStack matrixStack, boolean isChild) {
        this.getHead().translateAndRotate(matrixStack);
    }

    public ModelRenderer getRandomModelPart(Random random) {
        return this.cubes.get(random.nextInt(this.cubes.size()));
    }

    @Override
    public void accept(ModelRenderer renderer) {
        this.cubes.add(renderer);
    }
}
