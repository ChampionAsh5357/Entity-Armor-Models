/*
 * Entity Armor Models
 * Copyright (C) 2020 ChampionAsh5357
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation version 3.0 of the License.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.github.championash5357.entityarmormodels.api.client.renderer.entity.model;

import java.util.Random;
import java.util.function.Consumer;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;

import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.vanilla.ExtendedSegmentedModel;
import net.minecraft.client.renderer.entity.model.SlimeModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;

/**
 * An extended version of {@link SlimeModel}. 
 * Extend this model to apply custom entity armors 
 * to a Slime.
 * */
public class ExtendedSlimeModel<T extends LivingEntity> extends ExtendedSegmentedModel<T, SlimeModel<T>> {

	protected final ModelRenderer slimeBodies;
	protected final ModelRenderer slimeRightEye;
	protected final ModelRenderer slimeLeftEye;
	protected final ModelRenderer slimeMouth;
	private final Consumer<MatrixStack> offset;
	private final double armorOffset;

	public ExtendedSlimeModel(float modelSize) {
		this(modelSize, 0);
	}

	public ExtendedSlimeModel(float modelSize, int slimeBodyTexOffY) {
		this(modelSize, 64, 32, slimeBodyTexOffY);
	}

	public ExtendedSlimeModel(float modelSize, int textureWidth, int textureHeight, int slimeBodyTexOffY) {
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;
		this.slimeBodies = new ModelRenderer(this, 0, slimeBodyTexOffY);
		this.slimeRightEye = new ModelRenderer(this, 32, 0);
		this.slimeLeftEye = new ModelRenderer(this, 32, 4);
		this.slimeMouth = new ModelRenderer(this, 32, 8);
		if (slimeBodyTexOffY > 0) {
			this.slimeBodies.addBox(-3.0F, 17.0F, -3.0F, 6.0F, 6.0F, 6.0F, modelSize);
			this.slimeRightEye.addBox(-3.25F, 18.0F, -3.5F, 2.0F, 2.0F, 2.0F, modelSize);
			this.slimeLeftEye.addBox(1.25F, 18.0F, -3.5F, 2.0F, 2.0F, 2.0F, modelSize);
			this.slimeMouth.addBox(0.0F, 21.0F, -3.5F, 1.0F, 1.0F, 1.0F, modelSize);
			this.offset = (stack) -> {
				stack.translate(0.0d, 2.0625d, 0.0d);
			};
			this.armorOffset = 0.0625;
		} else {
			this.slimeBodies.addBox(-4.0F, 16.0F, -4.0F, 8.0F, 8.0F, 8.0F, modelSize);
			this.offset = (stack) -> {
				stack.translate(0.0d, 1.5d, 0.0d);
			};
			this.armorOffset = 0.125;
		}
	}

	@Override
	public void setModelSlotVisible(T entity, EquipmentSlotType slotType) {
		switch(slotType) {
		case HEAD:
			this.slimeBodies.showModel = true;
			this.slimeRightEye.showModel = true;
			this.slimeLeftEye.showModel = true;
			this.slimeMouth.showModel = true;
			break;
		case CHEST:
		case LEGS:
		case FEET:
		default:
			this.slimeBodies.showModel = false;
			this.slimeRightEye.showModel = false;
			this.slimeLeftEye.showModel = false;
			this.slimeMouth.showModel = false;
			break;
		}
	}

	@Override
	public Iterable<ModelRenderer> getParts() {
		return ImmutableList.of(this.slimeBodies, this.slimeRightEye, this.slimeLeftEye, this.slimeMouth);
	}

	@Override
	public void preRender(MatrixStack stack, boolean isChild) {
		offset.accept(stack);
	}

	@Override
	public void skullRender(MatrixStack stack, boolean isChild) {
		stack.translate(0.0d, -0.0625d, 0.0d);
	}

	@Override
	public void offset(LivingEntity entity, boolean wearingArmor, MatrixStack stack, boolean isChild) {
		stack.translate(0.0d, 1.0d, wearingArmor ? (this.armorOffset) : 0.125d);
	}

	@Override
	public ModelRenderer getModelHead() {
		return slimeBodies;
	}

	@Override
	public ModelRenderer getRandomModelRenderer(Random random) {
		return this.slimeBodies;
	}

	@Override
	public ModelRenderer getBody() {
		return slimeBodies;
	}
}
