package io.github.championash5357.entityarmormodels.client.renderer.entity.model;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ExtendedDrownedModel<T extends LivingEntity, M extends BipedModel<T>> extends ExtendedBipedModel<T, M> {

	public ExtendedDrownedModel(float modelSize) {
		this(modelSize, false);
	}
	
	public ExtendedDrownedModel(float modelSize, boolean isArmor) {
		this(modelSize, 0.0f, 64, isArmor ? 32 : 64);
	}
	
	public ExtendedDrownedModel(float modelSize, float yOffset, int textureWidthIn, int textureHeightIn) {
		super(modelSize, yOffset, textureWidthIn, textureHeightIn);
		this.bipedRightArm = new ModelRenderer(this, 32, 48);
		this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSize);
		this.bipedRightArm.setRotationPoint(-5.0F, 2.0F + modelSize, 0.0F);
		this.bipedRightLeg = new ModelRenderer(this, 16, 48);
		this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSize);
		this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F + modelSize, 0.0F);
	}

}
