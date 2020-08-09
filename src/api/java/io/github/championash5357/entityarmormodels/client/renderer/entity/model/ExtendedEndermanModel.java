package io.github.championash5357.entityarmormodels.client.renderer.entity.model;

import net.minecraft.client.renderer.entity.model.EndermanModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ExtendedEndermanModel<T extends LivingEntity> extends ExtendedBipedModel<T, EndermanModel<T>> {

	public ExtendedEndermanModel(float scale) {
		this(scale, 64, 32);
	}
	
	public ExtendedEndermanModel(float scale, int textureWidthIn, int textureHeightIn) {
		super(scale, -14.0F, textureWidthIn, textureHeightIn);
		this.bipedHeadwear = new ModelRenderer(this, 0, 16);
		this.bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, scale - 0.5F);
		this.bipedHeadwear.setRotationPoint(0.0F, -14.0F, 0.0F);
		this.bipedBody = new ModelRenderer(this, 32, 16);
		this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, scale);
		this.bipedBody.setRotationPoint(0.0F, -14.0F, 0.0F);
		this.bipedRightArm = new ModelRenderer(this, 56, textureHeightIn >= 64 ? 32 : 0);
		this.bipedRightArm.addBox(-1.0F, -2.0F, -1.0F, 2.0F, 30.0F, 2.0F, scale);
		this.bipedRightArm.setRotationPoint(-3.0F, -12.0F, 0.0F);
		this.bipedLeftArm = new ModelRenderer(this, 56, textureHeightIn >= 64 ? 32 : 0);
		this.bipedLeftArm.mirror = true;
		this.bipedLeftArm.addBox(-1.0F, -2.0F, -1.0F, 2.0F, 30.0F, 2.0F, scale);
		this.bipedLeftArm.setRotationPoint(5.0F, -12.0F, 0.0F);
		this.bipedRightLeg = new ModelRenderer(this, 56, 0);
		this.bipedRightLeg.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 30.0F, 2.0F, scale);
		this.bipedRightLeg.setRotationPoint(-2.0F, -2.0F, 0.0F);
		this.bipedLeftLeg = new ModelRenderer(this, 56, 0);
		this.bipedLeftLeg.mirror = true;
		this.bipedLeftLeg.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 30.0F, 2.0F, scale);
		this.bipedLeftLeg.setRotationPoint(2.0F, -2.0F, 0.0F);
	}
	
	@Override
	public String armorTextureExpansion() {
		return textureHeight >= 64 ? "_64" : super.armorTextureExpansion();
	}
}