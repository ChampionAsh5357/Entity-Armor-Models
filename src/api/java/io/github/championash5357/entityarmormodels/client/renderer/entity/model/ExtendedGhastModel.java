package io.github.championash5357.entityarmormodels.client.renderer.entity.model;

import java.util.Random;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.mojang.blaze3d.matrix.MatrixStack;

import io.github.championash5357.entityarmormodels.client.renderer.entity.model.vanilla.ExtendedSegmentedModel;
import net.minecraft.client.renderer.entity.model.GhastModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;

/**
 * An extended version of {@link GhastModel}. 
 * Extend this model to apply custom entity armors 
 * to a Ghast.
 * */
public class ExtendedGhastModel<T extends LivingEntity> extends ExtendedSegmentedModel<T, GhastModel<T>> {

	private final ModelRenderer[] tentacles = new ModelRenderer[9];
	protected final ModelRenderer head;
	private final ImmutableList<ModelRenderer> renderers;

	public ExtendedGhastModel(float modelSizeIn) {
		this(modelSizeIn, 64, 32);
	}

	public ExtendedGhastModel(float modelSizeIn, int textureWidthIn, int textureHeightIn) {
		this.textureWidth = textureWidthIn;
		this.textureHeight = textureHeightIn;
		Builder<ModelRenderer> builder = ImmutableList.builder();
		this.head = new ModelRenderer(this, 0, 0);
		this.head.addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, modelSizeIn);
		this.head.rotationPointY = 17.6F;
		builder.add(this.head);
		Random random = new Random(1660L);

		for(int i = 0; i < this.tentacles.length; ++i) {
			this.tentacles[i] = new ModelRenderer(this, 0, 0);
			float f = (((float)(i % 3) - (float)(i / 3 % 2) * 0.5F + 0.25F) / 2.0F * 2.0F - 1.0F) * 5.0F;
			float f1 = ((float)(i / 3) / 2.0F * 2.0F - 1.0F) * 5.0F;
			int j = random.nextInt(7) + 8;
			this.tentacles[i].addBox(-1.0F, 0.0F, -1.0F, 2.0F, (float)j, 2.0F, modelSizeIn);
			this.tentacles[i].rotationPointX = f;
			this.tentacles[i].rotationPointZ = f1;
			this.tentacles[i].rotationPointY = 24.6F;
			builder.add(this.tentacles[i]);
		}

		this.renderers = builder.build();
	}
	
	public ImmutableList<ModelRenderer> getTentacles() {
		return ImmutableList.copyOf(tentacles);
	}

	@Override
	public void setModelSlotVisible(T entityIn, EquipmentSlotType slotType) {
		switch(slotType) {
		case HEAD:
			for(ModelRenderer segment : tentacles) segment.showModel = false;
			this.head.showModel = true;
			break;
		case CHEST:
		default:
			for(ModelRenderer segment : tentacles) segment.showModel = false;
			this.head.showModel = false;
			break;
		case LEGS:
		case FEET:
			for(ModelRenderer segment : tentacles) segment.showModel = true;
			this.head.showModel = false;
			break;
		}
	}
	
	@Override
	public void preRender(MatrixStack stack, boolean isChild) {
		stack.scale(2.0f, 2.0f, 2.0f);
		stack.translate(0.0d, -0.25d, 0.0d);
	}

	@Override
	public ModelRenderer getModelHead() {
		return this.head;
	}

	@Override
	public Iterable<ModelRenderer> getParts() {
		return this.renderers;
	}
}