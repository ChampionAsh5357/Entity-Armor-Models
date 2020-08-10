package io.github.championash5357.entityarmormodels.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;

import io.github.championash5357.entityarmormodels.client.renderer.entity.model.vanilla.ExtendedSegmentedModel;
import net.minecraft.client.renderer.entity.model.GuardianModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.monster.GuardianEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.math.MathHelper;

public class ExtendedGuardianModel extends ExtendedSegmentedModel<GuardianEntity, GuardianModel> {

	private static final float[] xAngles = new float[]{1.75F, 0.25F, 0.0F, 0.0F, 0.5F, 0.5F, 0.5F, 0.5F, 1.25F, 0.75F, 0.0F, 0.0F};
	private static final float[] yAngles = new float[]{0.0F, 0.0F, 0.0F, 0.0F, 0.25F, 1.75F, 1.25F, 0.75F, 0.0F, 0.0F, 0.0F, 0.0F};
	private static final float[] zAngles = new float[]{0.0F, 0.0F, 0.25F, 1.75F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.75F, 1.25F};
	private static final float[] xPoints = new float[]{0.0F, 0.0F, 8.0F, -8.0F, -8.0F, 8.0F, 8.0F, -8.0F, 0.0F, 0.0F, 8.0F, -8.0F};
	private static final float[] yPoints = new float[]{-8.0F, -8.0F, -8.0F, -8.0F, 0.0F, 0.0F, 0.0F, 0.0F, 8.0F, 8.0F, 8.0F, 8.0F};
	private static final float[] zPoints = new float[]{8.0F, -8.0F, 0.0F, 0.0F, -8.0F, -8.0F, 8.0F, 8.0F, 8.0F, -8.0F, 0.0F, 0.0F};
	protected final ModelRenderer guardianBody;
	protected final ModelRenderer guardianEye;
	protected final ModelRenderer[] guardianSpines;
	protected final ModelRenderer[] guardianTail;

	public ExtendedGuardianModel(float modelSizeIn) {
		this(modelSizeIn, 64, 64);
	}

	public ExtendedGuardianModel(float modelSizeIn, int textureWidthIn, int textureHeightIn) {
		this.textureWidth = textureWidthIn;
		this.textureHeight = textureHeightIn;
		this.guardianSpines = new ModelRenderer[12];
		this.guardianBody = new ModelRenderer(this);
		this.guardianBody.setTextureOffset(0, 0).addBox(-8.0F, 8.0F, -8.0F, 16.0F, 16.0F, 16.0F, modelSizeIn);

		for(int i = 0; i < this.guardianSpines.length; ++i) {
			this.guardianSpines[i] = new ModelRenderer(this, 0, 0);
			this.guardianSpines[i].addBox(-1.0F, -4.5F, -1.0F, 2.0F, 9.0F, 2.0F, modelSizeIn);
			this.guardianBody.addChild(this.guardianSpines[i]);
		}
		
		this.guardianEye = new ModelRenderer(this, 8, 0);
		this.guardianEye.addBox(-1.0F, 15.0F, 0.0F, 2.0F, 2.0F, 1.0F, modelSizeIn);
		this.guardianBody.addChild(this.guardianEye);
		this.guardianTail = new ModelRenderer[3];
		this.guardianTail[0] = new ModelRenderer(this, 0, 32);
		this.guardianTail[0].addBox(-2.0F, 14.0F, 7.0F, 4.0F, 4.0F, 8.0F, modelSizeIn);
		this.guardianTail[1] = new ModelRenderer(this, 0, 54);
		this.guardianTail[1].addBox(0.0F, 14.0F, 0.0F, 3.0F, 3.0F, 7.0F, modelSizeIn);
		this.guardianTail[2] = new ModelRenderer(this);
		this.guardianTail[2].setTextureOffset(41, 32).addBox(0.0F, 14.0F, 0.0F, 2.0F, 2.0F, 6.0F, modelSizeIn);
		this.guardianTail[2].setTextureOffset(25, 32).addBox(1.0F, 10.5F, 3.0F, 1.0F, 9.0F, 9.0F, modelSizeIn);
		this.guardianBody.addChild(this.guardianTail[0]);
		this.guardianTail[0].addChild(this.guardianTail[1]);
		this.guardianTail[1].addChild(this.guardianTail[2]);
		this.rotateSpines(0.0F, 0.0F);
	}

	@Override
	public void setModelSlotVisible(GuardianEntity entityIn, EquipmentSlotType slotType) {
		switch(slotType) {
		case HEAD:
			this.guardianBody.showModel = true;
			this.guardianEye.showModel = true;
			for(ModelRenderer renderer: guardianSpines) renderer.showModel = true;
			for(ModelRenderer renderer: guardianTail) renderer.showModel = false;
			break;
		case CHEST:
		default:
			this.guardianBody.showModel = false;
			this.guardianEye.showModel = false;
			for(ModelRenderer renderer: guardianSpines) renderer.showModel = false;
			for(ModelRenderer renderer: guardianTail) renderer.showModel = false;
			break;
		case LEGS:
			this.guardianBody.showModel = false;
			this.guardianEye.showModel = false;
			for(ModelRenderer renderer: guardianSpines) renderer.showModel = false;
			for(int i = 0; i < guardianTail.length; i++) {
				if(i < 2) guardianTail[i].showModel = true;
				else guardianTail[i].showModel = false;
			}
			break;
		case FEET:
			this.guardianBody.showModel = false;
			this.guardianEye.showModel = false;
			for(ModelRenderer renderer: guardianSpines) renderer.showModel = false;
			for(int i = 0; i < guardianTail.length; i++) {
				if(i == 2) guardianTail[i].showModel = true;
				else guardianTail[i].showModel = false;
			}
			break;
		}
	}

	@Override
	public ModelRenderer getModelHead() {
		return this.guardianBody;
	}
	
	@Override
	public void preRender(MatrixStack stack, boolean isChild) {
		stack.translate(0.0d, 1.4375d, 0.0d);
		stack.scale(1.6875f, 1.6875f, 1.6875f);
	}
	
	@Override
	public void skullRender(MatrixStack stack, boolean isChild) {
		stack.scale(1.0625f, 1.0625f, 1.0625f);
		stack.translate(0.0d, -0.03125d, 0.0d);
	}
	
	@Override
	public void itemRender(MatrixStack stack, boolean isChild) {
		super.itemRender(stack, isChild);
	}

	@Override
	public Iterable<ModelRenderer> getParts() {
		return ImmutableList.of(this.guardianBody);
	}

	private void rotateSpines(float periodOffset, float heightOffset) {
		for(int i = 0; i < 12; ++i) {
			this.guardianSpines[i].rotateAngleX = (float)Math.PI * xAngles[i];
			this.guardianSpines[i].rotateAngleY = (float)Math.PI * yAngles[i];
			this.guardianSpines[i].rotateAngleZ = (float)Math.PI * zAngles[i];
			this.guardianSpines[i].rotationPointX = xPoints[i] * (1.0F + MathHelper.cos(periodOffset * 1.5F + (float)i) * 0.01F - heightOffset);
			this.guardianSpines[i].rotationPointY = 16.0F + yPoints[i] * (1.0F + MathHelper.cos(periodOffset * 1.5F + (float)i) * 0.01F - heightOffset);
			this.guardianSpines[i].rotationPointZ = zPoints[i] * (1.0F + MathHelper.cos(periodOffset * 1.5F + (float)i) * 0.01F - heightOffset);
		}
	}
}
