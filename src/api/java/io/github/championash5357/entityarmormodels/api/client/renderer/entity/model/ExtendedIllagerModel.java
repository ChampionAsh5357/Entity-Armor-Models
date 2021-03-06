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

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;

import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.vanilla.ExtendedSegmentedModel;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.entity.model.IllagerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.PillagerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.HandSide;

/**
 * An extended version of {@link IllagerModel}. 
 * Extend this model to apply custom entity armors 
 * to an Evoker, Illusioner, Pillager, or Vindicator.
 * */
public class ExtendedIllagerModel<T extends AbstractIllagerEntity> extends ExtendedSegmentedModel<T, IllagerModel<T>> implements IHasArm {

	protected final ModelRenderer head;
	protected final ModelRenderer hat;
	protected final ModelRenderer body;
	protected final ModelRenderer arms;
	protected final ModelRenderer rightLeg;
	protected final ModelRenderer leftLeg;
	protected final ModelRenderer rightArm;
	protected final ModelRenderer leftArm;

	public ExtendedIllagerModel(float modelSize) {
		this(modelSize, 0.0f, 64, 64);
	}

	public ExtendedIllagerModel(float modelSize, float yRotationOffset, int textureWidth, int textureHeight) {
		this.head = (new ModelRenderer(this)).setTextureSize(textureWidth, textureHeight);
		this.head.setRotationPoint(0.0F, 0.0F + yRotationOffset, 0.0F);
		this.head.setTextureOffset(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, modelSize);
		this.hat = (new ModelRenderer(this, 32, 0)).setTextureSize(textureWidth, textureHeight);
		this.hat.addBox(-4.0F, -10.0F, -4.0F, 8.0F, 12.0F, 8.0F, modelSize + 0.45F);
		this.head.addChild(this.hat);
		this.hat.showModel = false;
		ModelRenderer modelrenderer = (new ModelRenderer(this)).setTextureSize(textureWidth, textureHeight);
		modelrenderer.setRotationPoint(0.0F, yRotationOffset - 2.0F, 0.0F);
		modelrenderer.setTextureOffset(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F, modelSize);
		this.head.addChild(modelrenderer);
		this.body = (new ModelRenderer(this)).setTextureSize(textureWidth, textureHeight);
		this.body.setRotationPoint(0.0F, 0.0F + yRotationOffset, 0.0F);
		this.body.setTextureOffset(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F, modelSize);
		this.body.setTextureOffset(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 18.0F, 6.0F, modelSize + 0.5F);
		this.arms = (new ModelRenderer(this)).setTextureSize(textureWidth, textureHeight);
		this.arms.setRotationPoint(0.0F, 0.0F + yRotationOffset + 2.0F, 0.0F);
		this.arms.setTextureOffset(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, modelSize);
		ModelRenderer modelrenderer1 = (new ModelRenderer(this, 44, 22)).setTextureSize(textureWidth, textureHeight);
		modelrenderer1.mirror = true;
		modelrenderer1.addBox(4.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, modelSize);
		this.arms.addChild(modelrenderer1);
		this.arms.setTextureOffset(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F, modelSize);
		this.rightLeg = (new ModelRenderer(this, 0, 22)).setTextureSize(textureWidth, textureHeight);
		this.rightLeg.setRotationPoint(-2.0F, 12.0F + yRotationOffset, 0.0F);
		this.rightLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSize);
		this.leftLeg = (new ModelRenderer(this, 0, 22)).setTextureSize(textureWidth, textureHeight);
		this.leftLeg.mirror = true;
		this.leftLeg.setRotationPoint(2.0F, 12.0F + yRotationOffset, 0.0F);
		this.leftLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSize);
		this.rightArm = (new ModelRenderer(this, 40, 46)).setTextureSize(textureWidth, textureHeight);
		this.rightArm.addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSize);
		this.rightArm.setRotationPoint(-5.0F, 2.0F + yRotationOffset, 0.0F);
		this.leftArm = (new ModelRenderer(this, 40, 46)).setTextureSize(textureWidth, textureHeight);
		this.leftArm.mirror = true;
		this.leftArm.addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSize);
		this.leftArm.setRotationPoint(5.0F, 2.0F + yRotationOffset, 0.0F);
	}

	@Override
	public void setModelSlotVisible(T entity, EquipmentSlotType slotType) {
		switch(slotType) {
		case HEAD:
			this.head.showModel = true;
			this.body.showModel = false;
			this.arms.showModel = false;
			this.rightArm.showModel = false;
			this.leftArm.showModel = false;
			this.rightLeg.showModel = false;
			this.leftLeg.showModel = false;
			break;
		case CHEST:
			boolean flag = entity.getArmPose() == AbstractIllagerEntity.ArmPose.CROSSED;
			this.head.showModel = false;
			this.body.showModel = true;
			this.arms.showModel = flag;
			this.rightArm.showModel = !flag;
			this.leftArm.showModel = !flag;
			this.rightLeg.showModel = false;
			this.leftLeg.showModel = false;
			break;
		case LEGS:
			this.head.showModel = false;
			this.body.showModel = true;
			this.arms.showModel = false;
			this.rightArm.showModel = false;
			this.leftArm.showModel = false;
			this.rightLeg.showModel = true;
			this.leftLeg.showModel = true;
			break;
		case FEET:
			this.head.showModel = false;
			this.body.showModel = false;
			this.arms.showModel = false;
			this.rightArm.showModel = false;
			this.leftArm.showModel = false;
			this.rightLeg.showModel = true;
			this.leftLeg.showModel = true;
			break;
		default:
			break;
		}
	}

	@Override
	public ModelRenderer getModelHead() {
		return this.head;
	}

	private ModelRenderer getArm(HandSide side) {
		return side == HandSide.LEFT ? this.leftArm : this.rightArm;
	}

	@Override
	public void translateHand(HandSide side, MatrixStack matrixStack) {
		this.getArm(side).translateRotate(matrixStack);
	}

	@Override
	public void offset(LivingEntity entity, boolean wearingArmor, MatrixStack stack, boolean isChild) {
		double zOffset = entity instanceof PillagerEntity ? 0.0625 : 0.09375d;
		stack.translate(0.0d, 0.0d, wearingArmor ? 0.0625 : zOffset);
	}
	
	@Override
	public Iterable<ModelRenderer> getParts() {
		return ImmutableList.of(this.head, this.body, this.rightLeg, this.leftLeg, this.arms, this.rightArm, this.leftArm);
	}

	@Override
	public ModelRenderer getBody() {
		return body;
	}
}
