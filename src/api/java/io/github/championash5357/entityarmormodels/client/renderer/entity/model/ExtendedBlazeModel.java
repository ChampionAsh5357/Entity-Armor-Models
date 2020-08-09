package io.github.championash5357.entityarmormodels.client.renderer.entity.model;

import java.util.Arrays;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.mojang.blaze3d.matrix.MatrixStack;

import io.github.championash5357.entityarmormodels.client.renderer.entity.model.vanilla.ExtendedSegmentedModel;
import net.minecraft.client.renderer.entity.model.BlazeModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;

public class ExtendedBlazeModel<T extends LivingEntity> extends ExtendedSegmentedModel<T, BlazeModel<T>> {

	protected final ModelRenderer[] blazeSticks;
	protected final ModelRenderer blazeHead;
	private final ImmutableList<ModelRenderer> renderers;

	public ExtendedBlazeModel(float modelSizeIn) {
		this(modelSizeIn, 64, 32);
	}

	public ExtendedBlazeModel(float modelSizeIn, int textureWidthIn, int textureHeightIn) {
		this.textureWidth = textureWidthIn;
		this.textureHeight = textureHeightIn;
		this.blazeHead = new ModelRenderer(this, 0, 0);
		this.blazeHead.addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, modelSizeIn);
		this.blazeSticks = new ModelRenderer[12];

		for(int i = 0; i < this.blazeSticks.length; ++i) {
			this.blazeSticks[i] = new ModelRenderer(this, 0, 16);
			this.blazeSticks[i].addBox(0.0F, 0.0F, 0.0F, 2.0F, 8.0F, 2.0F, modelSizeIn);
		}

		Builder<ModelRenderer> builder = ImmutableList.builder();
		builder.add(this.blazeHead);
		builder.addAll(Arrays.asList(this.blazeSticks));
		this.renderers = builder.build();
	}

	@Override
	public void setModelSlotVisible(T entityIn, EquipmentSlotType slotType) {
		switch(slotType) {
		case HEAD:
			for(ModelRenderer segment : blazeSticks) segment.showModel = false;
			this.blazeHead.showModel = true;
			break;
		case CHEST:
		default:
			for(ModelRenderer segment : blazeSticks) segment.showModel = false;
			this.blazeHead.showModel = false;
			break;
		case LEGS:
		case FEET:
			for(ModelRenderer segment : blazeSticks) segment.showModel = true;
			this.blazeHead.showModel = false;
			break;
		}
	}
	
	@Override
	public void preRender(MatrixStack stack, boolean isChild) {
		stack.translate(0.0d, 0.25d, 0.0d);
	}
	
	@Override
	public void skullRender(MatrixStack stack, boolean isChild) {
		stack.translate(0.0d, -0.0625d, 0.0d);
	}

	@Override
	public ModelRenderer getModelHead() {
		return this.blazeHead;
	}

	@Override
	public Iterable<ModelRenderer> getParts() {
		return this.renderers;
	}

}
