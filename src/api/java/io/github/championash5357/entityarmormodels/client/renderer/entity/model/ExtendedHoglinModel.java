package io.github.championash5357.entityarmormodels.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;

import io.github.championash5357.entityarmormodels.client.renderer.entity.model.vanilla.ExtendedAgeableModel;
import net.minecraft.client.renderer.entity.model.BoarModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.IFlinging;
import net.minecraft.inventory.EquipmentSlotType;

/**
 * An extended version of {@link BoarModel}. 
 * Extend this model to apply custom entity armors 
 * to a Hoglin and Zoglin.
 * */
public class ExtendedHoglinModel<T extends MobEntity & IFlinging> extends ExtendedAgeableModel<T, BoarModel<T>> {

	protected final ModelRenderer head;
	protected final ModelRenderer body;
	protected final ModelRenderer leftFrontLeg;
	protected final ModelRenderer rightFrontLeg;
	protected final ModelRenderer leftBackLeg;
	protected final ModelRenderer rightBackLeg;

	public ExtendedHoglinModel(float modelSizeIn) {
		this(modelSizeIn, 128, 64);
	}

	public ExtendedHoglinModel(float modelSizeIn, int textureWidthIn, int textureHeightIn) {
		super(true, 8.0F, 6.0F, 1.9F, 2.0F, 24.0F);
		this.textureWidth = textureWidthIn;
		this.textureHeight = textureHeightIn;
		this.body = new ModelRenderer(this);
		this.body.setRotationPoint(0.0F, 7.0F, 0.0F);
		this.body.setTextureOffset(1, 1).addBox(-8.0F, -7.0F, -13.0F, 16.0F, 14.0F, 26.0F, modelSizeIn);
		this.head = new ModelRenderer(this);
		this.head.setRotationPoint(0.0F, 2.0F, -12.0F);
		this.head.setTextureOffset(61, 1).addBox(-7.0F, -3.0F, -19.0F, 14.0F, 6.0F, 19.0F, modelSizeIn);
		ModelRenderer leftEar = new ModelRenderer(this);
		leftEar.setRotationPoint(-6.0F, -2.0F, -3.0F);
		leftEar.setTextureOffset(1, 1).addBox(-6.0F, -1.0F, -2.0F, 6.0F, 1.0F, 4.0F, modelSizeIn);
		leftEar.rotateAngleZ = -0.6981317F;
		this.head.addChild(leftEar);
		ModelRenderer rightEar = new ModelRenderer(this);
		rightEar.setRotationPoint(6.0F, -2.0F, -3.0F);
		rightEar.setTextureOffset(1, 6).addBox(0.0F, -1.0F, -2.0F, 6.0F, 1.0F, 4.0F, modelSizeIn);
		rightEar.rotateAngleZ = 0.6981317F;
		this.head.addChild(rightEar);
		ModelRenderer leftTusk = new ModelRenderer(this);
		leftTusk.setRotationPoint(-7.0F, 2.0F, -12.0F);
		leftTusk.setTextureOffset(10, 13).addBox(-1.0F, -11.0F, -1.0F, 2.0F, 11.0F, 2.0F, modelSizeIn);
		this.head.addChild(leftTusk);
		ModelRenderer rightTusk = new ModelRenderer(this);
		rightTusk.setRotationPoint(7.0F, 2.0F, -12.0F);
		rightTusk.setTextureOffset(1, 13).addBox(-1.0F, -11.0F, -1.0F, 2.0F, 11.0F, 2.0F, modelSizeIn);
		this.head.addChild(rightTusk);
		this.head.rotateAngleX = 0.87266463F;
		this.leftFrontLeg = new ModelRenderer(this);
		this.leftFrontLeg.setRotationPoint(-4.0F, 10.0F, -8.5F);
		this.leftFrontLeg.setTextureOffset(66, 42).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 14.0F, 6.0F, modelSizeIn);
		this.rightFrontLeg = new ModelRenderer(this);
		this.rightFrontLeg.setRotationPoint(4.0F, 10.0F, -8.5F);
		this.rightFrontLeg.setTextureOffset(41, 42).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 14.0F, 6.0F, modelSizeIn);
		this.leftBackLeg = new ModelRenderer(this);
		this.leftBackLeg.setRotationPoint(-5.0F, 13.0F, 10.0F);
		this.leftBackLeg.setTextureOffset(21, 45).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 11.0F, 5.0F, modelSizeIn);
		this.rightBackLeg = new ModelRenderer(this);
		this.rightBackLeg.setRotationPoint(5.0F, 13.0F, 10.0F);
		this.rightBackLeg.setTextureOffset(0, 45).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 11.0F, 5.0F, modelSizeIn);
	}

	@Override
	public void setModelSlotVisible(T entityIn, EquipmentSlotType slotType) {
		switch(slotType) {
		case HEAD:
			head.showModel = true;
			body.showModel = false;
			leftFrontLeg.showModel = false;
			rightFrontLeg.showModel = false;
			leftBackLeg.showModel = false;
			rightBackLeg.showModel = false;
			break;
		case CHEST:
			head.showModel = false;
			body.showModel = true;
			leftFrontLeg.showModel = false;
			rightFrontLeg.showModel = false;
			leftBackLeg.showModel = false;
			rightBackLeg.showModel = false;
			break;
		case LEGS:
			head.showModel = false;
			body.showModel = true;
			leftFrontLeg.showModel = true;
			rightFrontLeg.showModel = true;
			leftBackLeg.showModel = true;
			rightBackLeg.showModel = true;
			break;
		case FEET:
			head.showModel = false;
			body.showModel = false;
			leftFrontLeg.showModel = true;
			rightFrontLeg.showModel = true;
			leftBackLeg.showModel = true;
			rightBackLeg.showModel = true;
			break;
		default:
			break;
		}
	}

	@Override
	public ModelRenderer getModelHead() {
		return this.head;
	}
	
	
	@Override
	public void preRender(MatrixStack stack, boolean isChild) {
		if(isChild) {
			stack.translate(0.0d, -0.625d, 0.625d);
			stack.scale(1.9375f, 1.9375f, 1.9375f);
		}
		else {
			stack.translate(0.0d, 0.5d, 0.5d);
			stack.scale(1.75f, 1.75f, 1.75f);
		}
	}

	@Override
	protected Iterable<ModelRenderer> getHeadParts() {
		return ImmutableList.of(this.head);
	}

	@Override
	protected Iterable<ModelRenderer> getBodyParts() {
		return ImmutableList.of(this.body, this.leftFrontLeg, this.rightFrontLeg, this.leftBackLeg, this.rightBackLeg);
	}
}