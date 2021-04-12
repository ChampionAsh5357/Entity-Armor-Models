/*
 * Entity Armor Models
 * Copyright (c) 2021-2021 ChampionAsh5357.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.ashwork.entityarmormodels.client.renderer.entity.model;

import java.util.Objects;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.datafixers.util.Pair;

import net.ashwork.entityarmormodels.util.TriConsumer;
import net.minecraft.client.renderer.entity.model.ElytraModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.HandSide;

public class EntityModelData<T extends LivingEntity, M extends EntityModel<T>> {

    private final M model, innerModel, outerModel;
    private final EntityModel<T> elytraModel;
    private final BiConsumer<M, EquipmentSlotType> setPartVisibility;
    private final TriConsumer<M, HandSide, MatrixStack> translateToHand;
    private final BiFunction<M, Random, ModelRenderer> getRandomModelPart;
    private final TriConsumer<M, MatrixStack, Boolean> translateAndRotateHead;

    private EntityModelData(M baseModel, Function<Float, M> modelFactory, EntityModel<T> elytraModel, BiConsumer<M, EquipmentSlotType> setPartVisibility, TriConsumer<M, HandSide, MatrixStack> translateToHand, BiFunction<M, Random, ModelRenderer> getRandomModelPart, TriConsumer<M, MatrixStack, Boolean> translateAndRotateHead) {
        this.model = baseModel;
        this.innerModel = modelFactory.apply(0.5f);
        this.outerModel = modelFactory.apply(1f);
        this.elytraModel = elytraModel;
        this.setPartVisibility = setPartVisibility;
        this.translateToHand = translateToHand;
        this.getRandomModelPart = getRandomModelPart;
        this.translateAndRotateHead = translateAndRotateHead;
    }

    public void prepare(EntityModel<T> parentModel, T entity, float animationPosition, float animationSpeed, float partialTick, float bob, float yRotation, float xRotation) {
        parentModel.copyPropertiesTo(model);
        this.model.prepareMobModel(entity, animationPosition, animationSpeed, partialTick);
        this.model.setupAnim(entity, animationPosition, animationSpeed, bob, yRotation, xRotation);
    }

    public void prepareArmor(EntityModel<T> parentModel, T entity, float animationPosition, float animationSpeed, float partialTick, float bob, float yRotation, float xRotation) {
        parentModel.copyPropertiesTo(innerModel);
        parentModel.copyPropertiesTo(outerModel);
        this.innerModel.prepareMobModel(entity, animationPosition, animationSpeed, partialTick);
        this.outerModel.prepareMobModel(entity, animationPosition, animationSpeed, partialTick);
        this.innerModel.setupAnim(entity, animationPosition, animationSpeed, bob, yRotation, xRotation);
        this.outerModel.setupAnim(entity, animationPosition, animationSpeed, bob, yRotation, xRotation);
    }

    public M getInnerModel() {
        return innerModel;
    }

    public M getOuterModel() {
        return outerModel;
    }

    public EntityModel<T> getElytraModel() {
        return elytraModel;
    }

    public void setPartVisibility(M model, EquipmentSlotType slot) {
        this.setPartVisibility.accept(model, slot);
    }

    public ModelRenderer getRandomModelPart(Random random) {
        return this.getRandomModelPart.apply(model, random);
    }

    public void translateToHand(HandSide hand, MatrixStack matrixStack) {
        this.translateToHand.accept(model, hand, matrixStack);
    }

    public void translateAndRotateHead(MatrixStack stack, boolean isChild) {
        this.translateAndRotateHead.accept(model, stack, isChild);
    }

    public static class Builder<T extends LivingEntity, M extends EntityModel<T>> {

        private M model;
        private EntityModel<T> elytraModel = new ElytraModel<>();
        private BiConsumer<M, EquipmentSlotType> setPartVisibility;
        private TriConsumer<M, HandSide, MatrixStack> translateToHand;
        private BiFunction<M, Random, ModelRenderer> getRandomModelPart;
        private TriConsumer<M, MatrixStack, Boolean> translateAndRotateHead;

        public static <V extends LivingEntity, X extends EntityModel<V>> Builder<V, X> of(Class<V> entityClass, Class<X> modelClass) {
            return new Builder<>();
        }

        private Builder() {}

        public Builder<T, M> setBaseModel(M model) {
            this.model = model;
            return this;
        }

        public Builder<T, M> setElytraModel(EntityModel<T> elytraModel) {
            this.elytraModel = elytraModel;
            return this;
        }

        public Builder<T, M> setPartVisibility(BiConsumer<M, EquipmentSlotType> func) {
            this.setPartVisibility = func;
            return this;
        }

        public Builder<T, M> translateToHand(TriConsumer<M, HandSide, MatrixStack> func) {
            this.translateToHand = func;
            return this;
        }

        public Builder<T, M> getRandomModelPart(BiFunction<M, Random, ModelRenderer> func) {
            this.getRandomModelPart = func;
            return this;
        }

        public Builder<T, M> translateAndRotateHead(TriConsumer<M, MatrixStack, Boolean> func) {
            this.translateAndRotateHead = func;
            return this;
        }

        public Pair<EntityModelData<T, M>, Byte> build(Function<Float, M> modelFactory) {
            Objects.requireNonNull(modelFactory);
            byte disabledLayers = 0;
            if (model == null) model = modelFactory.apply(0f);
            if (elytraModel == null) disabledLayers |= 0b10000;
            if (setPartVisibility == null) disabledLayers |= 0b1;
            if (translateToHand == null) disabledLayers |= 0b1100;
            if (getRandomModelPart == null) disabledLayers |= 0b1100000;
            if (translateAndRotateHead == null) disabledLayers |= 0b10;
            return new Pair<>(new EntityModelData<>(model, modelFactory, elytraModel, setPartVisibility, translateToHand, getRandomModelPart, translateAndRotateHead), disabledLayers);
        }
    }
}
