package io.github.championash5357.entityarmormodels.api.client.renderer.entity.model;

import java.util.Arrays;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.mojang.blaze3d.matrix.MatrixStack;

import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.vanilla.ExtendedSegmentedModel;
import net.minecraft.client.renderer.entity.model.SilverfishModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.math.vector.Vector3f;

/**
 * An extended version of {@link SilverfishModel}. 
 * Extend this model to apply custom entity armors 
 * to a Silverfish.
 * */
public class ExtendedSilverfishModel<T extends LivingEntity> extends ExtendedSegmentedModel<T, SilverfishModel<T>> {

	private final ModelRenderer[] silverfishBodyParts;
	private final ModelRenderer[] silverfishWings;
	private final ImmutableList<ModelRenderer> renderers;
	private final float[] zPlacement = new float[7];
	private static final int[][] SILVERFISH_BOX_LENGTH = new int[][]{{3, 2, 2}, {4, 3, 2}, {6, 4, 3}, {3, 3, 3}, {2, 2, 3}, {2, 1, 2}, {1, 1, 2}};
	private static final int[][] SILVERFISH_TEXTURE_POSITIONS = new int[][]{{0, 0}, {0, 4}, {0, 9}, {0, 16}, {0, 22}, {11, 0}, {13, 4}};

	public ExtendedSilverfishModel(float modelSize) {
		this(modelSize, 64, 32);
	}

	public ExtendedSilverfishModel(float modelSize, int textureWidth, int textureHeight) {
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;
		this.silverfishBodyParts = new ModelRenderer[7];
		float f = -3.5F;

		for(int i = 0; i < this.silverfishBodyParts.length; ++i) {
			this.silverfishBodyParts[i] = new ModelRenderer(this, SILVERFISH_TEXTURE_POSITIONS[i][0], SILVERFISH_TEXTURE_POSITIONS[i][1]);
			this.silverfishBodyParts[i].addBox((float)SILVERFISH_BOX_LENGTH[i][0] * -0.5F, 0.0F, (float)SILVERFISH_BOX_LENGTH[i][2] * -0.5F, (float)SILVERFISH_BOX_LENGTH[i][0], (float)SILVERFISH_BOX_LENGTH[i][1], (float)SILVERFISH_BOX_LENGTH[i][2], modelSize);
			this.silverfishBodyParts[i].setRotationPoint(0.0F, (float)(24 - SILVERFISH_BOX_LENGTH[i][1]), f);
			this.zPlacement[i] = f;
			if (i < this.silverfishBodyParts.length - 1) {
				f += (float)(SILVERFISH_BOX_LENGTH[i][2] + SILVERFISH_BOX_LENGTH[i + 1][2]) * 0.5F;
			}
		}

		this.silverfishWings = new ModelRenderer[3];
		this.silverfishWings[0] = new ModelRenderer(this, 20, 0);
		this.silverfishWings[0].addBox(-5.0F, 0.0F, (float)SILVERFISH_BOX_LENGTH[2][2] * -0.5F, 10.0F, 8.0F, (float)SILVERFISH_BOX_LENGTH[2][2], modelSize);
		this.silverfishWings[0].setRotationPoint(0.0F, 16.0F, this.zPlacement[2]);
		this.silverfishWings[1] = new ModelRenderer(this, 20, 11);
		this.silverfishWings[1].addBox(-3.0F, 0.0F, (float)SILVERFISH_BOX_LENGTH[4][2] * -0.5F, 6.0F, 4.0F, (float)SILVERFISH_BOX_LENGTH[4][2], modelSize);
		this.silverfishWings[1].setRotationPoint(0.0F, 20.0F, this.zPlacement[4]);
		this.silverfishWings[2] = new ModelRenderer(this, 20, 18);
		this.silverfishWings[2].addBox(-3.0F, 0.0F, (float)SILVERFISH_BOX_LENGTH[4][2] * -0.5F, 6.0F, 5.0F, (float)SILVERFISH_BOX_LENGTH[1][2], modelSize);
		this.silverfishWings[2].setRotationPoint(0.0F, 19.0F, this.zPlacement[1]);
		Builder<ModelRenderer> builder = ImmutableList.builder();
		builder.addAll(Arrays.asList(this.silverfishBodyParts));
		builder.addAll(Arrays.asList(this.silverfishWings));
		this.renderers = builder.build();
	}
	
	public ImmutableList<ModelRenderer> getSilverfishBodyParts() {
		return ImmutableList.copyOf(silverfishBodyParts);
	}
	
	public ImmutableList<ModelRenderer> getSilverfishWings() {
		return ImmutableList.copyOf(silverfishWings);
	}

	@Override
	public void setModelSlotVisible(T entity, EquipmentSlotType slotType) {
		switch(slotType) {
		case HEAD:
			for(int i = 0; i < this.silverfishBodyParts.length; i++) {
				if(i == 0) this.silverfishBodyParts[i].showModel = true;
				else this.silverfishBodyParts[i].showModel = false;
			}
			for(ModelRenderer renderer : silverfishWings) renderer.showModel = false;
			break;
		case CHEST:
			for(int i = 0; i < this.silverfishBodyParts.length; i++) {
				if(i >= 1 && i <= 3) this.silverfishBodyParts[i].showModel = true;
				else this.silverfishBodyParts[i].showModel = false;
			}
			for(int i = 0; i < this.silverfishWings.length; i++) {
				if(i >= 0 && i <= 1) this.silverfishWings[i].showModel = true;
				else this.silverfishWings[i].showModel = false;
			}
			break;
		case LEGS:
			for(int i = 0; i < this.silverfishBodyParts.length; i++) {
				if(i >= 3 && i <= 5) this.silverfishBodyParts[i].showModel = true;
				else this.silverfishBodyParts[i].showModel = false;
			}
			for(int i = 0; i < this.silverfishWings.length; i++) {
				if(i == 2) this.silverfishWings[i].showModel = true;
				else this.silverfishWings[i].showModel = false;
			}
			break;
		case FEET:
			for(int i = 0; i < this.silverfishBodyParts.length; i++) {
				if(i >= 5 && i <= 6) this.silverfishBodyParts[i].showModel = true;
				else this.silverfishBodyParts[i].showModel = false;
			}
			for(ModelRenderer renderer : silverfishWings) renderer.showModel = false;
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
		return this.silverfishBodyParts[0];
	}

	@Override
	public Iterable<ModelRenderer> getParts() {
		return this.renderers;
	}

	@Override
	public ModelRenderer getBody() {
		return this.silverfishBodyParts[2];
	}
}
