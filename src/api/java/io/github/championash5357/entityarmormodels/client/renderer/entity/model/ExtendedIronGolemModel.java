package io.github.championash5357.entityarmormodels.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;

import io.github.championash5357.entityarmormodels.client.renderer.entity.model.vanilla.ExtendedSegmentedModel;
import net.minecraft.client.renderer.entity.model.IronGolemModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.inventory.EquipmentSlotType;

/**
 * An extended version of {@link IronGolemModel}. 
 * Extend this model to apply custom entity armors 
 * to a Iron Golem.
 * */
public class ExtendedIronGolemModel<T extends IronGolemEntity> extends ExtendedSegmentedModel<T, IronGolemModel<T>> {

	protected final ModelRenderer ironGolemHead;
	protected final ModelRenderer ironGolemBody;
	protected final ModelRenderer ironGolemRightArm;
	protected final ModelRenderer ironGolemLeftArm;
	protected final ModelRenderer ironGolemLeftLeg;
	protected final ModelRenderer ironGolemRightLeg;

	public ExtendedIronGolemModel(float modelSizeIn) {
		this(modelSizeIn, 128, 128);
	}

	public ExtendedIronGolemModel(float modelSizeIn, int textureWidthIn, int textureHeightIn) {
		this.textureWidth = textureWidthIn;
		this.textureHeight = textureHeightIn;
		this.ironGolemHead = new ModelRenderer(this);
		this.ironGolemHead.setRotationPoint(0.0F, -7.0F, -2.0F);
		this.ironGolemHead.setTextureOffset(0, 0).addBox(-4.0F, -12.0F, -5.5F, 8.0F, 10.0F, 8.0F, modelSizeIn);
		this.ironGolemHead.setTextureOffset(24, 0).addBox(-1.0F, -5.0F, -7.5F, 2.0F, 4.0F, 2.0F, modelSizeIn);
		this.ironGolemBody = new ModelRenderer(this);
		this.ironGolemBody.setRotationPoint(0.0F, -7.0F, 0.0F);
		this.ironGolemBody.setTextureOffset(0, 40).addBox(-9.0F, -2.0F, -6.0F, 18.0F, 12.0F, 11.0F, modelSizeIn);
		this.ironGolemBody.setTextureOffset(0, 70).addBox(-4.5F, 10.0F, -3.0F, 9.0F, 5.0F, 6.0F, modelSizeIn + 0.5F);
		this.ironGolemRightArm = new ModelRenderer(this);
		this.ironGolemRightArm.setRotationPoint(0.0F, -7.0F, 0.0F);
		this.ironGolemRightArm.setTextureOffset(60, 21).addBox(-13.0F, -2.5F, -3.0F, 4.0F, 30.0F, 6.0F, modelSizeIn);
		this.ironGolemLeftArm = new ModelRenderer(this);
		this.ironGolemLeftArm.mirror = true;
		this.ironGolemLeftArm.setRotationPoint(0.0F, -7.0F, 0.0F);
		this.ironGolemLeftArm.setTextureOffset(60, 58).addBox(9.0F, -2.5F, -3.0F, 4.0F, 30.0F, 6.0F, modelSizeIn);
		this.ironGolemLeftLeg = new ModelRenderer(this, 0, 22);
		this.ironGolemLeftLeg.setRotationPoint(-4.0F, 11.0F, 0.0F);
		this.ironGolemLeftLeg.setTextureOffset(37, 0).addBox(-3.5F, -3.0F, -3.0F, 6.0F, 16.0F, 5.0F, modelSizeIn);
		this.ironGolemRightLeg = new ModelRenderer(this, 0, 22);
		this.ironGolemRightLeg.mirror = true;
		this.ironGolemRightLeg.setTextureOffset(60, 0).setRotationPoint(5.0F, 11.0F, 0.0F);
		this.ironGolemRightLeg.addBox(-3.5F, -3.0F, -3.0F, 6.0F, 16.0F, 5.0F, modelSizeIn);
	}

	@Override
	public void setModelSlotVisible(T entityIn, EquipmentSlotType slotType) {
		switch(slotType) {
		case HEAD:
			this.ironGolemHead.showModel = true;
			this.ironGolemBody.showModel = false;
			this.ironGolemRightArm.showModel = false;
			this.ironGolemLeftArm.showModel = false;
			this.ironGolemLeftLeg.showModel = false;
			this.ironGolemRightLeg.showModel = false;
			break;
		case CHEST:
			this.ironGolemHead.showModel = false;
			this.ironGolemBody.showModel = true;
			this.ironGolemRightArm.showModel = true;
			this.ironGolemLeftArm.showModel = true;
			this.ironGolemLeftLeg.showModel = false;
			this.ironGolemRightLeg.showModel = false;
			break;
		case LEGS:
			this.ironGolemHead.showModel = false;
			this.ironGolemBody.showModel = true;
			this.ironGolemRightArm.showModel = false;
			this.ironGolemLeftArm.showModel = false;
			this.ironGolemLeftLeg.showModel = true;
			this.ironGolemRightLeg.showModel = true;
			break;
		case FEET:
			this.ironGolemHead.showModel = false;
			this.ironGolemBody.showModel = false;
			this.ironGolemRightArm.showModel = false;
			this.ironGolemLeftArm.showModel = false;
			this.ironGolemLeftLeg.showModel = true;
			this.ironGolemRightLeg.showModel = true;
			break;
		default:
			break;
		}
	}

	@Override
	public void preRender(MatrixStack stack, boolean isChild) {
		stack.scale(1.125f, 1.125f, 1.125f);
		stack.translate(0.0d, -0.0625d, 0.0d);
	}
	
	@Override
	public void skullRender(MatrixStack stack, boolean isChild) {
		stack.scale(1.0625f, 1.0625f, 1.0625f);
	}
	
	@Override
	public ModelRenderer getModelHead() {
		return this.ironGolemHead;
	}

	@Override
	public Iterable<ModelRenderer> getParts() {
		return ImmutableList.of(this.ironGolemHead, this.ironGolemBody, this.ironGolemLeftLeg, this.ironGolemRightLeg, this.ironGolemRightArm, this.ironGolemLeftArm);
	}

}
