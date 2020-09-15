package io.github.championash5357.entityarmormodels.api.client.renderer.entity.model;

import java.util.Arrays;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;

import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.vanilla.ExtendedSegmentedModel;
import net.minecraft.client.renderer.entity.model.EndermiteModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.math.vector.Vector3f;

/**
 * An extended version of {@link EndermiteModel}. 
 * Extend this model to apply custom entity armors 
 * to an Endermite.
 * */
public class ExtendedEndermiteModel<T extends LivingEntity> extends ExtendedSegmentedModel<T, EndermiteModel<T>> {

	private static final int[][] BODY_SIZES = new int[][]{{4, 3, 2}, {6, 4, 5}, {3, 3, 1}, {1, 2, 1}};
	private static final int[][] BODY_TEXS = new int[][]{{0, 0}, {0, 5}, {0, 14}, {0, 18}};
	private static final int BODY_COUNT = BODY_SIZES.length;
	private final ModelRenderer[] bodyParts = new ModelRenderer[BODY_COUNT];

	public ExtendedEndermiteModel(float modelSize) {
		this(modelSize, 64, 32);
	}

	public ExtendedEndermiteModel(float modelSize, int textureWidth, int textureHeight) {
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;
		float f = -3.5F;

		for(int i = 0; i < this.bodyParts.length; ++i) {
			this.bodyParts[i] = new ModelRenderer(this, BODY_TEXS[i][0], BODY_TEXS[i][1]);
			this.bodyParts[i].addBox((float)BODY_SIZES[i][0] * -0.5F, 0.0F, (float)BODY_SIZES[i][2] * -0.5F, (float)BODY_SIZES[i][0], (float)BODY_SIZES[i][1], (float)BODY_SIZES[i][2], modelSize);
			this.bodyParts[i].setRotationPoint(0.0F, (float)(24 - BODY_SIZES[i][1]), f);
			if (i < this.bodyParts.length - 1) {
				f += (float)(BODY_SIZES[i][2] + BODY_SIZES[i + 1][2]) * 0.5F;
			}
		}
	}
	
	public ImmutableList<ModelRenderer> getBodyParts() {
		return ImmutableList.copyOf(bodyParts);
	}

	@Override
	public void setModelSlotVisible(T entity, EquipmentSlotType slotType) {
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
	public void offset(LivingEntity entity, boolean wearingArmor, MatrixStack stack, boolean isChild) {
		stack.scale(0.5f, 0.5f, 0.5f);
		stack.translate(0.0d, wearingArmor ? 0.0d : 0.125d, -0.25d);
		stack.rotate(Vector3f.XP.rotationDegrees(90.0f));
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

	@Override
	public ModelRenderer getBody() {
		return this.bodyParts[1];
	}
}
