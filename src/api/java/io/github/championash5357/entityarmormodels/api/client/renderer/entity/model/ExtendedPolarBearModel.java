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

import com.mojang.blaze3d.matrix.MatrixStack;

import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.vanilla.ExtendedQuadrupedModel;
import net.minecraft.client.renderer.entity.model.PolarBearModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.PolarBearEntity;

/**
 * An extended version of {@link PolarBearModel}. 
 * Extend this model to apply custom entity armors 
 * to a Polar Bear.
 * */
public class ExtendedPolarBearModel<T extends PolarBearEntity> extends ExtendedQuadrupedModel<T, PolarBearModel<T>> {

	public ExtendedPolarBearModel(float modelSize) {
		this(modelSize, 128, 64);
	}

	public ExtendedPolarBearModel(float modelSize, int textureWidth, int textureHeight) {
		super(modelSize, textureWidth, textureHeight, 12.0f, true, 16.0F, 4.0F, 2.25F, 2.0F, 24.0f);
		this.headModel = new ModelRenderer(this, 0, 0);
		this.headModel.addBox(-3.5F, -3.0F, -3.0F, 7.0F, 7.0F, 7.0F, modelSize);
		this.headModel.setRotationPoint(0.0F, 10.0F, -16.0F);
		this.headModel.setTextureOffset(0, 44).addBox(-2.5F, 1.0F, -6.0F, 5.0F, 3.0F, 3.0F, modelSize);
		this.headModel.setTextureOffset(26, 0).addBox(-4.5F, -4.0F, -1.0F, 2.0F, 2.0F, 1.0F, modelSize);
		ModelRenderer modelrenderer = this.headModel.setTextureOffset(26, 0);
		modelrenderer.mirror = true;
		modelrenderer.addBox(2.5F, -4.0F, -1.0F, 2.0F, 2.0F, 1.0F, modelSize);
		this.body = new ModelRenderer(this);
		this.body.setTextureOffset(0, 19).addBox(-5.0F, -13.0F, -7.0F, 14.0F, 14.0F, 11.0F, modelSize);
		this.body.setTextureOffset(39, 0).addBox(-4.0F, -25.0F, -7.0F, 12.0F, 12.0F, 10.0F, modelSize);
		this.body.setRotationPoint(-2.0F, 9.0F, 12.0F);
		this.legBackRight = new ModelRenderer(this, 50, 22);
		this.legBackRight.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 10.0F, 8.0F, modelSize);
		this.legBackRight.setRotationPoint(-3.5F, 14.0F, 6.0F);
		this.legBackLeft = new ModelRenderer(this, 50, 22);
		this.legBackLeft.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 10.0F, 8.0F, modelSize);
		this.legBackLeft.setRotationPoint(3.5F, 14.0F, 6.0F);
		this.legFrontRight = new ModelRenderer(this, 50, 40);
		this.legFrontRight.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 10.0F, 6.0F, modelSize);
		this.legFrontRight.setRotationPoint(-2.5F, 14.0F, -7.0F);
		this.legFrontLeft = new ModelRenderer(this, 50, 40);
		this.legFrontLeft.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 10.0F, 6.0F, modelSize);
		this.legFrontLeft.setRotationPoint(2.5F, 14.0F, -7.0F);
		--this.legBackRight.rotationPointX;
		++this.legBackLeft.rotationPointX;
		this.legBackRight.rotationPointZ += 0.0F;
		this.legBackLeft.rotationPointZ += 0.0F;
		--this.legFrontRight.rotationPointX;
		++this.legFrontLeft.rotationPointX;
		--this.legFrontRight.rotationPointZ;
		--this.legFrontLeft.rotationPointZ;
	}

	@Override
	public void offset(LivingEntity entity, boolean wearingArmor, MatrixStack stack, boolean isChild) {
		stack.translate(0.125d, -1.5d, 0.0625d);
	}

	@Override
	public void preRender(MatrixStack stack, boolean isChild) {
		if (isChild) stack.translate(0.0d, 0.0625d, 0.125d);
		else stack.translate(0.0d, 0.25d, -0.125d);
	}

	@Override
	public void skullRender(MatrixStack stack, boolean isChild) {
		stack.translate(0.0d, -0.0625d, 0.0d);
	}

	@Override
	public void itemRender(MatrixStack stack, boolean isChild) {
		super.itemRender(stack, isChild);
	}
}
