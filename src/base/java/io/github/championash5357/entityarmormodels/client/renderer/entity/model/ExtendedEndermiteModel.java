package io.github.championash5357.entityarmormodels.client.renderer.entity.model;

import java.util.Arrays;

import com.mojang.blaze3d.matrix.MatrixStack;

import io.github.championash5357.entityarmormodels.client.renderer.entity.model.vanilla.ExtendedSegmentedModel;
import net.minecraft.client.renderer.entity.model.EndermiteModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;

public class ExtendedEndermiteModel<T extends LivingEntity> extends ExtendedSegmentedModel<T, EndermiteModel<T>> {

	private static final int[][] BODY_SIZES = new int[][]{{4, 3, 2}, {6, 4, 5}, {3, 3, 1}, {1, 2, 1}};
	private static final int[][] BODY_TEXS = new int[][]{{0, 0}, {0, 5}, {0, 14}, {0, 18}};
	private static final int BODY_COUNT = BODY_SIZES.length;
	protected final ModelRenderer[] bodyParts = new ModelRenderer[BODY_COUNT];

	public ExtendedEndermiteModel(float modelSizeIn) {
		this(modelSizeIn, 64, 32);
	}

	public ExtendedEndermiteModel(float modelSizeIn, int textureWidthIn, int textureHeightIn) {
		this.textureWidth = textureWidthIn;
		this.textureHeight = textureHeightIn;
		float f = -3.5F;

		for(int i = 0; i < this.bodyParts.length; ++i) {
			this.bodyParts[i] = new ModelRenderer(this, BODY_TEXS[i][0], BODY_TEXS[i][1]);
			this.bodyParts[i].addBox((float)BODY_SIZES[i][0] * -0.5F, 0.0F, (float)BODY_SIZES[i][2] * -0.5F, (float)BODY_SIZES[i][0], (float)BODY_SIZES[i][1], (float)BODY_SIZES[i][2], modelSizeIn);
			this.bodyParts[i].setRotationPoint(0.0F, (float)(24 - BODY_SIZES[i][1]), f);
			if (i < this.bodyParts.length - 1) {
				f += (float)(BODY_SIZES[i][2] + BODY_SIZES[i + 1][2]) * 0.5F;
			}
		}
	}

	@Override
	public void setModelSlotVisible(EquipmentSlotType slotType) {
		switch(slotType) {
		case HEAD:
			for (int i = 0; i < bodyParts.length; i++) {
				if(i == 0) bodyParts[i].showModel = true;
				else bodyParts[i].showModel = false;
			}
			break;
		case CHEST:
			for (int i = 0; i < bodyParts.length; i++) {
				if(i == 1) bodyParts[i].showModel = true;
				else bodyParts[i].showModel = false;
			}
			break;
		case LEGS:
			for (int i = 0; i < bodyParts.length; i++) {
				if(i == 2) bodyParts[i].showModel = true;
				else bodyParts[i].showModel = false;
			}
			break;
		case FEET:
			for (int i = 0; i < bodyParts.length; i++) {
				if(i == 3) bodyParts[i].showModel = true;
				else bodyParts[i].showModel = false;
			}
			break;
		default:
			break;
		}
	}
	
	@Override
	public void preRender(MatrixStack stack, boolean isChild) {
		stack.translate(0.0d, 1.0d, 0.0d);
		stack.scale(0.5f, 0.5f, 0.5f);
		stack.translate(0.0d, -0.3125d, -0.4375d);
	}

	@Override
	public ModelRenderer getModelHead() {
		return this.bodyParts[0];
	}

	@Override
	public Iterable<ModelRenderer> getParts() {
		return Arrays.asList(this.bodyParts);
	}
}
