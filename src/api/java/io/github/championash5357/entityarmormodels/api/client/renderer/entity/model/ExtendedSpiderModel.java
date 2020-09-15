package io.github.championash5357.entityarmormodels.api.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;

import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.vanilla.ExtendedSegmentedModel;
import net.minecraft.client.renderer.entity.model.SpiderModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.math.vector.Vector3f;

/**
 * An extended version of {@link SpiderModel}. 
 * Extend this model to apply custom entity armors 
 * to a Spider and Cave Spider.
 * */
public class ExtendedSpiderModel<T extends LivingEntity> extends ExtendedSegmentedModel<T, SpiderModel<T>> {

	protected final ModelRenderer spiderHead;
	protected final ModelRenderer spiderNeck;
	protected final ModelRenderer spiderBody;
	protected final ModelRenderer spiderLeg1;
	protected final ModelRenderer spiderLeg2;
	protected final ModelRenderer spiderLeg3;
	protected final ModelRenderer spiderLeg4;
	protected final ModelRenderer spiderLeg5;
	protected final ModelRenderer spiderLeg6;
	protected final ModelRenderer spiderLeg7;
	protected final ModelRenderer spiderLeg8;

	public ExtendedSpiderModel(float modelSize) {
		this(modelSize, 64, 32);
	}

	public ExtendedSpiderModel(float modelSize, int textureWidth, int textureHeight) {
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;
		this.spiderHead = new ModelRenderer(this, 32, 4);
		this.spiderHead.addBox(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F, modelSize);
		this.spiderHead.setRotationPoint(0.0F, 15.0F, -3.0F);
		this.spiderNeck = new ModelRenderer(this, 0, 0);
		this.spiderNeck.addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, modelSize);
		this.spiderNeck.setRotationPoint(0.0F, 15.0F, 0.0F);
		this.spiderBody = new ModelRenderer(this, 0, 12);
		this.spiderBody.addBox(-5.0F, -4.0F, -6.0F, 10.0F, 8.0F, 12.0F, modelSize);
		this.spiderBody.setRotationPoint(0.0F, 15.0F, 9.0F);
		this.spiderLeg1 = new ModelRenderer(this, 18, 0);
		this.spiderLeg1.addBox(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, modelSize, true);
		this.spiderLeg1.setRotationPoint(-4.0F, 15.0F, 2.0F);
		this.spiderLeg2 = new ModelRenderer(this, 18, 0);
		this.spiderLeg2.addBox(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, modelSize);
		this.spiderLeg2.setRotationPoint(4.0F, 15.0F, 2.0F);
		this.spiderLeg3 = new ModelRenderer(this, 18, 0);
		this.spiderLeg3.addBox(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, modelSize, true);
		this.spiderLeg3.setRotationPoint(-4.0F, 15.0F, 1.0F);
		this.spiderLeg4 = new ModelRenderer(this, 18, 0);
		this.spiderLeg4.addBox(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, modelSize);
		this.spiderLeg4.setRotationPoint(4.0F, 15.0F, 1.0F);
		this.spiderLeg5 = new ModelRenderer(this, 18, 0);
		this.spiderLeg5.addBox(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, modelSize, true);
		this.spiderLeg5.setRotationPoint(-4.0F, 15.0F, 0.0F);
		this.spiderLeg6 = new ModelRenderer(this, 18, 0);
		this.spiderLeg6.addBox(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, modelSize);
		this.spiderLeg6.setRotationPoint(4.0F, 15.0F, 0.0F);
		this.spiderLeg7 = new ModelRenderer(this, 18, 0);
		this.spiderLeg7.addBox(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, modelSize, true);
		this.spiderLeg7.setRotationPoint(-4.0F, 15.0F, -1.0F);
		this.spiderLeg8 = new ModelRenderer(this, 18, 0);
		this.spiderLeg8.addBox(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, modelSize);
		this.spiderLeg8.setRotationPoint(4.0F, 15.0F, -1.0F);
	}

	@Override
	public void setModelSlotVisible(T entity, EquipmentSlotType slotType) {
		switch(slotType) {
		case HEAD:
			this.spiderHead.showModel = true;
			this.spiderNeck.showModel = false;
			this.spiderBody.showModel = false;
			this.spiderLeg1.showModel = false;
			this.spiderLeg2.showModel = false;
			this.spiderLeg3.showModel = false;
			this.spiderLeg4.showModel = false;
			this.spiderLeg5.showModel = false;
			this.spiderLeg6.showModel = false;
			this.spiderLeg7.showModel = false;
			this.spiderLeg8.showModel = false;
			break;
		case CHEST:
			this.spiderHead.showModel = false;
			this.spiderNeck.showModel = true;
			this.spiderBody.showModel = true;
			this.spiderLeg1.showModel = false;
			this.spiderLeg2.showModel = false;
			this.spiderLeg3.showModel = false;
			this.spiderLeg4.showModel = false;
			this.spiderLeg5.showModel = false;
			this.spiderLeg6.showModel = false;
			this.spiderLeg7.showModel = false;
			this.spiderLeg8.showModel = false;
			break;
		case LEGS:
			this.spiderHead.showModel = false;
			this.spiderNeck.showModel = false;
			this.spiderBody.showModel = true;
			this.spiderLeg1.showModel = true;
			this.spiderLeg2.showModel = true;
			this.spiderLeg3.showModel = true;
			this.spiderLeg4.showModel = true;
			this.spiderLeg5.showModel = true;
			this.spiderLeg6.showModel = true;
			this.spiderLeg7.showModel = true;
			this.spiderLeg8.showModel = true;
			break;
		case FEET:
			this.spiderHead.showModel = false;
			this.spiderNeck.showModel = false;
			this.spiderBody.showModel = false;
			this.spiderLeg1.showModel = true;
			this.spiderLeg2.showModel = true;
			this.spiderLeg3.showModel = true;
			this.spiderLeg4.showModel = true;
			this.spiderLeg5.showModel = true;
			this.spiderLeg6.showModel = true;
			this.spiderLeg7.showModel = true;
			this.spiderLeg8.showModel = true;
			break;
		default:
			break;
		}
	}
	
	@Override
	public void offset(LivingEntity entity, boolean wearingArmor, MatrixStack stack, boolean isChild) {
		stack.translate(0.0d, -0.125d, -0.5d);
		stack.rotate(Vector3f.XP.rotationDegrees(90.0f));
	}
	
	@Override
	public void preRender(MatrixStack stack, boolean isChild) {
		stack.translate(0.0d, 0.25d, -0.25d);
	}
	
	@Override
	public void skullRender(MatrixStack stack, boolean isChild) {
		stack.translate(0.0d, -0.0625d, 0.0d);
	}

	@Override
	public ModelRenderer getModelHead() {
		return this.spiderHead;
	}

	@Override
	public Iterable<ModelRenderer> getParts() {
		return ImmutableList.of(this.spiderHead, this.spiderNeck, this.spiderBody, this.spiderLeg1, this.spiderLeg2, this.spiderLeg3, this.spiderLeg4, this.spiderLeg5, this.spiderLeg6, this.spiderLeg7, this.spiderLeg8);
	}

	@Override
	public ModelRenderer getBody() {
		return spiderBody;
	}

}