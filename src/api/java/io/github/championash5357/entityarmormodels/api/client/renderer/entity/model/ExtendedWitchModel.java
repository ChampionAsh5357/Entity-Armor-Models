package io.github.championash5357.entityarmormodels.api.client.renderer.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.entity.model.WitchModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

/**
 * An extended version of {@link WitchModel}. 
 * Extend this model to apply custom entity armors 
 * to a Witch.
 * */
public class ExtendedWitchModel<T extends LivingEntity> extends ExtendedVillagerModel<T> {
	
	public ExtendedWitchModel(float modelSize) {
		this(modelSize, 64, 128);
	}

	public ExtendedWitchModel(float modelSize, int textureWidth, int textureHeight) {
		super(modelSize, textureWidth, textureHeight);
		ModelRenderer mole = (new ModelRenderer(this)).setTextureSize(textureWidth, textureHeight);
		mole.setRotationPoint(0.0F, -2.0F, 0.0F);
		mole.setTextureOffset(0, 0).addBox(0.0F, 3.0F, -6.75F, 1.0F, 1.0F, 1.0F, -0.25F + modelSize);
		this.villagerNose.addChild(mole);
		this.villagerHead = (new ModelRenderer(this)).setTextureSize(textureWidth, textureHeight);
		this.villagerHead.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.villagerHead.setTextureOffset(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, modelSize);
		this.hat = (new ModelRenderer(this)).setTextureSize(textureWidth, textureHeight);
		this.hat.setRotationPoint(-5.0F, -10.03125F, -5.0F);
		this.hat.setTextureOffset(0, 64).addBox(0.0F, 0.0F, 0.0F, 10.0F, 2.0F, 10.0F, modelSize);
		this.villagerHead.addChild(this.hat);
		this.villagerHead.addChild(this.villagerNose);
		ModelRenderer modelrenderer = (new ModelRenderer(this)).setTextureSize(textureWidth, textureHeight);
		modelrenderer.setRotationPoint(1.75F, -4.0F, 2.0F);
		modelrenderer.setTextureOffset(0, 76).addBox(0.0F, 0.0F, 0.0F, 7.0F, 4.0F, 7.0F, modelSize);
		modelrenderer.rotateAngleX = -0.05235988F;
		modelrenderer.rotateAngleZ = 0.02617994F;
		this.hat.addChild(modelrenderer);
		ModelRenderer modelrenderer1 = (new ModelRenderer(this)).setTextureSize(textureWidth, textureHeight);
		modelrenderer1.setRotationPoint(1.75F, -4.0F, 2.0F);
		modelrenderer1.setTextureOffset(0, 87).addBox(0.0F, 0.0F, 0.0F, 4.0F, 4.0F, 4.0F, modelSize);
		modelrenderer1.rotateAngleX = -0.10471976F;
		modelrenderer1.rotateAngleZ = 0.05235988F;
		modelrenderer.addChild(modelrenderer1);
		ModelRenderer modelrenderer2 = (new ModelRenderer(this)).setTextureSize(textureWidth, textureHeight);
		modelrenderer2.setRotationPoint(1.75F, -2.0F, 2.0F);
		modelrenderer2.setTextureOffset(0, 95).addBox(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.25F + modelSize);
		modelrenderer2.rotateAngleX = -0.20943952F;
		modelrenderer2.rotateAngleZ = 0.10471976F;
		modelrenderer1.addChild(modelrenderer2);
	}
	
	@Override
	public void skullRender(MatrixStack stack, boolean isChild) {
		stack.translate(0.0D, -0.0625D, 0.0D);
	}
	
	@Override
	public void itemRender(MatrixStack stack, boolean isChild) {
		stack.scale(0.95f, 0.95f, 0.95f);
		stack.translate(0.0D, -0.0625D, 0.0D);
	}
}
