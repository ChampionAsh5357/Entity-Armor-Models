package io.github.championash5357.entityarmormodels.client.renderer.entity.model;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.SkeletonModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

/**
 * An extended version of {@link SkeletonModel}. 
 * Custom armor models are already supported 
 * by Minecraft and Forge for this. You should 
 * extend {@link BipedModel} for custom armors 
 * relating to Skeleton, Stray, and Wither Skeleton.
 * */
public class ExtendedSkeletonModel<T extends LivingEntity, M extends BipedModel<T>> extends ExtendedBipedModel<T, M> {
	
	public ExtendedSkeletonModel(float modelSize) {
		this(modelSize, false);
	}
	
	public ExtendedSkeletonModel(float modelSize, boolean isArmor) {
		this(modelSize, 0.0f, 64, 32, isArmor);
	}

	public ExtendedSkeletonModel(float modelSize, float yOffset, int textureWidthIn, int textureHeightIn, boolean isArmor) {
		super(modelSize, yOffset, textureWidthIn, textureHeightIn);
		if(!isArmor) {
			this.bipedRightArm = new ModelRenderer(this, 40, 16);
			this.bipedRightArm.addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, modelSize);
			this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
			this.bipedLeftArm = new ModelRenderer(this, 40, 16);
			this.bipedLeftArm.mirror = true;
			this.bipedLeftArm.addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, modelSize);
			this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
			this.bipedRightLeg = new ModelRenderer(this, 0, 16);
			this.bipedRightLeg.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, modelSize);
			this.bipedRightLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
			this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
			this.bipedLeftLeg.mirror = true;
			this.bipedLeftLeg.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, modelSize);
			this.bipedLeftLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
		}
	}

}
