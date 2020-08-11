package io.github.championash5357.entityarmormodels.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;

import io.github.championash5357.entityarmormodels.client.renderer.entity.model.vanilla.ExtendedSegmentedModel;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.entity.model.IllagerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.monster.AbstractIllagerEntity;
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

	public ExtendedIllagerModel(float modelSizeIn) {
		this(modelSizeIn, 0.0f, 64, 64);
	}

	public ExtendedIllagerModel(float modelSizeIn, float yRotationOffset, int textureWidthIn, int textureHeightIn) {
		this.head = (new ModelRenderer(this)).setTextureSize(textureWidthIn, textureHeightIn);
		this.head.setRotationPoint(0.0F, 0.0F + yRotationOffset, 0.0F);
		this.head.setTextureOffset(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, modelSizeIn);
		this.hat = (new ModelRenderer(this, 32, 0)).setTextureSize(textureWidthIn, textureHeightIn);
		this.hat.addBox(-4.0F, -10.0F, -4.0F, 8.0F, 12.0F, 8.0F, modelSizeIn + 0.45F);
		this.head.addChild(this.hat);
		this.hat.showModel = false;
		ModelRenderer modelrenderer = (new ModelRenderer(this)).setTextureSize(textureWidthIn, textureHeightIn);
		modelrenderer.setRotationPoint(0.0F, yRotationOffset - 2.0F, 0.0F);
		modelrenderer.setTextureOffset(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F, modelSizeIn);
		this.head.addChild(modelrenderer);
		this.body = (new ModelRenderer(this)).setTextureSize(textureWidthIn, textureHeightIn);
		this.body.setRotationPoint(0.0F, 0.0F + yRotationOffset, 0.0F);
		this.body.setTextureOffset(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F, modelSizeIn);
		this.body.setTextureOffset(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 18.0F, 6.0F, modelSizeIn + 0.5F);
		this.arms = (new ModelRenderer(this)).setTextureSize(textureWidthIn, textureHeightIn);
		this.arms.setRotationPoint(0.0F, 0.0F + yRotationOffset + 2.0F, 0.0F);
		this.arms.setTextureOffset(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, modelSizeIn);
		ModelRenderer modelrenderer1 = (new ModelRenderer(this, 44, 22)).setTextureSize(textureWidthIn, textureHeightIn);
		modelrenderer1.mirror = true;
		modelrenderer1.addBox(4.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, modelSizeIn);
		this.arms.addChild(modelrenderer1);
		this.arms.setTextureOffset(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F, modelSizeIn);
		this.rightLeg = (new ModelRenderer(this, 0, 22)).setTextureSize(textureWidthIn, textureHeightIn);
		this.rightLeg.setRotationPoint(-2.0F, 12.0F + yRotationOffset, 0.0F);
		this.rightLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSizeIn);
		this.leftLeg = (new ModelRenderer(this, 0, 22)).setTextureSize(textureWidthIn, textureHeightIn);
		this.leftLeg.mirror = true;
		this.leftLeg.setRotationPoint(2.0F, 12.0F + yRotationOffset, 0.0F);
		this.leftLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSizeIn);
		this.rightArm = (new ModelRenderer(this, 40, 46)).setTextureSize(textureWidthIn, textureHeightIn);
		this.rightArm.addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSizeIn);
		this.rightArm.setRotationPoint(-5.0F, 2.0F + yRotationOffset, 0.0F);
		this.leftArm = (new ModelRenderer(this, 40, 46)).setTextureSize(textureWidthIn, textureHeightIn);
		this.leftArm.mirror = true;
		this.leftArm.addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSizeIn);
		this.leftArm.setRotationPoint(5.0F, 2.0F + yRotationOffset, 0.0F);
	}

	@Override
	public void setModelSlotVisible(T entityIn, EquipmentSlotType slotType) {
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
			boolean flag = entityIn.getArmPose() == AbstractIllagerEntity.ArmPose.CROSSED;
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
	public void translateHand(HandSide sideIn, MatrixStack matrixStackIn) {
		this.getArm(sideIn).translateRotate(matrixStackIn);
	}

	@Override
	public Iterable<ModelRenderer> getParts() {
		return ImmutableList.of(this.head, this.body, this.rightLeg, this.leftLeg, this.arms, this.rightArm, this.leftArm);
	}
}
