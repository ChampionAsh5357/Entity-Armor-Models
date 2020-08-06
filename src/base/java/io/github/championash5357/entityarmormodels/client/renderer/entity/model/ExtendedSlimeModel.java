package io.github.championash5357.entityarmormodels.client.renderer.entity.model;

import java.util.Random;
import java.util.function.Consumer;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;

import io.github.championash5357.entityarmormodels.client.renderer.entity.model.vanilla.ExtendedSegmentedModel;
import net.minecraft.client.renderer.entity.model.SlimeModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;

public class ExtendedSlimeModel<T extends LivingEntity> extends ExtendedSegmentedModel<T, SlimeModel<T>> {

	protected final ModelRenderer slimeBodies;
	protected final ModelRenderer slimeRightEye;
	protected final ModelRenderer slimeLeftEye;
	protected final ModelRenderer slimeMouth;
	private final float xScale, yScale, zScale;
	private final Consumer<MatrixStack> offset;

	public ExtendedSlimeModel(float modelSize) {
		this(modelSize, 0);
	}
	
	public ExtendedSlimeModel(float modelSize, int slimeBodyTexOffY) {
		this(modelSize, 64, 32, slimeBodyTexOffY);
	}

	public ExtendedSlimeModel(float modelSize, int textureWidthIn, int textureHeightIn, int slimeBodyTexOffY) {
		this.textureWidth = textureWidthIn;
		this.textureHeight = textureHeightIn;
		this.slimeBodies = new ModelRenderer(this, 0, slimeBodyTexOffY);
		this.slimeRightEye = new ModelRenderer(this, 32, 0);
		this.slimeLeftEye = new ModelRenderer(this, 32, 4);
		this.slimeMouth = new ModelRenderer(this, 32, 8);
		if (slimeBodyTexOffY > 0) {
			this.xScale = this.yScale = this.zScale = 0.6875f;
			this.slimeBodies.addBox(-3.0F, 17.0F, -3.0F, 6.0F, 6.0F, 6.0F, modelSize);
			this.slimeRightEye.addBox(-3.25F, 18.0F, -3.5F, 2.0F, 2.0F, 2.0F, modelSize);
			this.slimeLeftEye.addBox(1.25F, 18.0F, -3.5F, 2.0F, 2.0F, 2.0F, modelSize);
			this.slimeMouth.addBox(0.0F, 21.0F, -3.5F, 1.0F, 1.0F, 1.0F, modelSize);
			this.offset = (stack) -> {
				stack.translate(0.0d, 2.0625d, 0.0d);
			};
		} else {
			this.xScale = this.yScale = this.zScale = 1.0f;
			this.slimeBodies.addBox(-4.0F, 16.0F, -4.0F, 8.0F, 8.0F, 8.0F, modelSize);
			this.offset = (stack) -> {
				stack.translate(0.0d, 1.5d, 0.0d);
			};
		}
	}

	@Override
	public void setModelSlotVisible(EquipmentSlotType slotType) {
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
	public float getXScale() {
		return xScale;
	}
	
	@Override
	public float getYScale() {
		return yScale;
	}
	
	@Override
	public float getZScale() {
		return zScale;
	}
	
	@Override
	public ModelRenderer getModelHead() {
		return slimeBodies;
	}

	@Override
	public ModelRenderer getRandomModelRenderer(Random randomIn) {
		return this.slimeBodies;
	}
}
