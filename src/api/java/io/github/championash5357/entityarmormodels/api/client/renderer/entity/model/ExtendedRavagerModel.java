package io.github.championash5357.entityarmormodels.api.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;

import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.vanilla.ExtendedSegmentedModel;
import net.minecraft.client.renderer.entity.model.RavagerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.RavagerEntity;
import net.minecraft.inventory.EquipmentSlotType;

/**
 * An extended version of {@link RavagerModel}. 
 * Extend this model to apply custom entity armors 
 * to a Ravager.
 * */
public class ExtendedRavagerModel extends ExtendedSegmentedModel<RavagerEntity, RavagerModel> {

	protected final ModelRenderer head;
	protected final ModelRenderer body;
	protected final ModelRenderer legBackRight;
	protected final ModelRenderer legBackLeft;
	protected final ModelRenderer legFrontRight;
	protected final ModelRenderer legFrontLeft;
	protected final ModelRenderer neck;

	public ExtendedRavagerModel(float modelSize) {
		this(modelSize, 128, 128);
	}

	public ExtendedRavagerModel(float modelSize, int textureWidth, int textureHeight) {
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;
		this.neck = new ModelRenderer(this);
		this.neck.setRotationPoint(0.0F, -7.0F, -1.5F);
		this.neck.setTextureOffset(68, 73).addBox(-5.0F, -1.0F, -18.0F, 10.0F, 10.0F, 18.0F, modelSize);
		this.head = new ModelRenderer(this);
		this.head.setRotationPoint(0.0F, 16.0F, -17.0F);
		this.head.setTextureOffset(0, 0).addBox(-8.0F, -20.0F, -14.0F, 16.0F, 20.0F, 16.0F, modelSize);
		this.head.setTextureOffset(0, 0).addBox(-2.0F, -6.0F, -18.0F, 4.0F, 8.0F, 4.0F, modelSize);
		ModelRenderer leftHorn = new ModelRenderer(this);
		leftHorn.setRotationPoint(-10.0F, -14.0F, -8.0F);
		leftHorn.setTextureOffset(74, 55).addBox(0.0F, -14.0F, -2.0F, 2.0F, 14.0F, 4.0F, modelSize);
		leftHorn.rotateAngleX = 1.0995574F;
		this.head.addChild(leftHorn);
		ModelRenderer rightHorn = new ModelRenderer(this);
		rightHorn.mirror = true;
		rightHorn.setRotationPoint(8.0F, -14.0F, -8.0F);
		rightHorn.setTextureOffset(74, 55).addBox(0.0F, -14.0F, -2.0F, 2.0F, 14.0F, 4.0F, modelSize);
		rightHorn.rotateAngleX = 1.0995574F;
		this.head.addChild(rightHorn);
		ModelRenderer jaw = new ModelRenderer(this);
		jaw.setRotationPoint(0.0F, -2.0F, 2.0F);
		jaw.setTextureOffset(0, 36).addBox(-8.0F, 0.0F, -16.0F, 16.0F, 3.0F, 16.0F, modelSize);
		this.head.addChild(jaw);
		this.neck.addChild(this.head);
		this.body = new ModelRenderer(this);
		this.body.setTextureOffset(0, 55).addBox(-7.0F, -10.0F, -7.0F, 14.0F, 16.0F, 20.0F, modelSize);
		this.body.setTextureOffset(0, 91).addBox(-6.0F, 6.0F, -7.0F, 12.0F, 13.0F, 18.0F, modelSize);
		this.body.setRotationPoint(0.0F, 1.0F, 2.0F);
		this.legBackRight = new ModelRenderer(this, 96, 0);
		this.legBackRight.addBox(-4.0F, 0.0F, -4.0F, 8.0F, 37.0F, 8.0F, modelSize);
		this.legBackRight.setRotationPoint(-8.0F, -13.0F, 18.0F);
		this.legBackLeft = new ModelRenderer(this, 96, 0);
		this.legBackLeft.mirror = true;
		this.legBackLeft.addBox(-4.0F, 0.0F, -4.0F, 8.0F, 37.0F, 8.0F, modelSize);
		this.legBackLeft.setRotationPoint(8.0F, -13.0F, 18.0F);
		this.legFrontRight = new ModelRenderer(this, 64, 0);
		this.legFrontRight.addBox(-4.0F, 0.0F, -4.0F, 8.0F, 37.0F, 8.0F, modelSize);
		this.legFrontRight.setRotationPoint(-8.0F, -13.0F, -5.0F);
		this.legFrontLeft = new ModelRenderer(this, 64, 0);
		this.legFrontLeft.mirror = true;
		this.legFrontLeft.addBox(-4.0F, 0.0F, -4.0F, 8.0F, 37.0F, 8.0F, modelSize);
		this.legFrontLeft.setRotationPoint(8.0F, -13.0F, -5.0F);
	}

	@Override
	public void copyAttributesOfModel(RavagerModel model) {
		super.copyAttributesOfModel(model);
		this.head.copyModelAngles(getChildModels(model.getParts().iterator().next()).get(0));
	}
	
	@Override
	public void setModelSlotVisible(RavagerEntity entity, EquipmentSlotType slotType) {
		switch(slotType) {
		case HEAD:
			this.neck.showModel = true;
			this.body.showModel = false;
			this.legBackLeft.showModel = false;
			this.legBackRight.showModel = false;
			this.legFrontLeft.showModel = false;
			this.legFrontRight.showModel = false;
			break;
		case CHEST:
			this.neck.showModel = false;
			this.body.showModel = true;
			this.legBackLeft.showModel = false;
			this.legBackRight.showModel = false;
			this.legFrontLeft.showModel = false;
			this.legFrontRight.showModel = false;
			break;
		case LEGS:
			this.neck.showModel = false;
			this.body.showModel = false;
			this.legBackLeft.showModel = true;
			this.legBackRight.showModel = true;
			this.legFrontLeft.showModel = true;
			this.legFrontRight.showModel = true;
			break;
		case FEET:
			this.neck.showModel = false;
			this.body.showModel = false;
			this.legBackLeft.showModel = true;
			this.legBackRight.showModel = true;
			this.legFrontLeft.showModel = true;
			this.legFrontRight.showModel = true;
			break;
		default:
			break;
		}
	}
	
	@Override
	public void offset(LivingEntity entity, boolean wearingArmor, MatrixStack stack, boolean isChild) {
		stack.translate(0.0d, -0.375d, 0.6875d);
	}

	@Override
	public void preRender(MatrixStack stack, boolean isChild) {
		stack.scale(2.0625f, 2.0625f, 2.0625f);
		stack.translate(0.0d, -0.8125d, 0.5d);
	}

	@Override
	public ModelRenderer getModelHead() {
		return this.head;
	}

	@Override
	public Iterable<ModelRenderer> getParts() {
		return ImmutableList.of(this.neck, this.body, this.legBackRight, this.legBackLeft, this.legFrontRight, this.legFrontLeft);
	}

	@Override
	public ModelRenderer getBody() {
		return body;
	}
}
