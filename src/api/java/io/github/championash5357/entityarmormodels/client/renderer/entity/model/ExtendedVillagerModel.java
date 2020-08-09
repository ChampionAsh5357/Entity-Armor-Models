package io.github.championash5357.entityarmormodels.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;

import io.github.championash5357.entityarmormodels.client.renderer.entity.model.vanilla.ExtendedSegmentedModel;
import net.minecraft.client.renderer.entity.model.IHeadToggle;
import net.minecraft.client.renderer.entity.model.VillagerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;

public class ExtendedVillagerModel<T extends LivingEntity> extends ExtendedSegmentedModel<T, VillagerModel<T>> implements IHeadToggle {

	protected ModelRenderer hat;
	protected ModelRenderer villagerHead;
	protected final ModelRenderer villagerBody;
	protected final ModelRenderer villagerArms;
	protected final ModelRenderer rightVillagerLeg;
	protected final ModelRenderer leftVillagerLeg;
	protected final ModelRenderer villagerNose;

	public ExtendedVillagerModel(float modelSizeIn) {
		this(modelSizeIn, 64, 64);
	}

	public ExtendedVillagerModel(float modelSizeIn, int textureWidthIn, int textureHeightIn) {
		this.textureWidth = textureWidthIn;
		this.textureHeight = textureHeightIn;
		this.villagerHead = (new ModelRenderer(this)).setTextureSize(textureWidthIn, textureHeightIn);
		this.villagerHead.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.villagerHead.setTextureOffset(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, modelSizeIn);
		this.hat = (new ModelRenderer(this)).setTextureSize(textureWidthIn, textureHeightIn);
		this.hat.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.hat.setTextureOffset(32, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, modelSizeIn + 0.5F);
		this.villagerHead.addChild(this.hat);
		ModelRenderer hatBrim = (new ModelRenderer(this)).setTextureSize(textureWidthIn, textureHeightIn);
		hatBrim.setRotationPoint(0.0F, 0.0F, 0.0F);
		hatBrim.setTextureOffset(30, 47).addBox(-8.0F, -8.0F, -6.0F, 16.0F, 16.0F, 1.0F, modelSizeIn);
		hatBrim.rotateAngleX = (-(float)Math.PI / 2F);
		this.hat.addChild(hatBrim);
		this.villagerNose = (new ModelRenderer(this)).setTextureSize(textureWidthIn, textureHeightIn);
		this.villagerNose.setRotationPoint(0.0F, -2.0F, 0.0F);
		this.villagerNose.setTextureOffset(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F, modelSizeIn);
		this.villagerHead.addChild(this.villagerNose);
		this.villagerBody = (new ModelRenderer(this)).setTextureSize(textureWidthIn, textureHeightIn);
		this.villagerBody.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.villagerBody.setTextureOffset(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F, modelSizeIn);
		ModelRenderer clothing = (new ModelRenderer(this)).setTextureSize(textureWidthIn, textureHeightIn);
		clothing.setRotationPoint(0.0F, 0.0F, 0.0F);
		clothing.setTextureOffset(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 18.0F, 6.0F, modelSizeIn + 0.5F);
		this.villagerBody.addChild(clothing);
		this.villagerArms = (new ModelRenderer(this)).setTextureSize(textureWidthIn, textureHeightIn);
		this.villagerArms.setRotationPoint(0.0F, 2.0F, 0.0F);
		this.villagerArms.setTextureOffset(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, modelSizeIn);
		this.villagerArms.setTextureOffset(44, 22).addBox(4.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, modelSizeIn, true);
		this.villagerArms.setTextureOffset(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F, modelSizeIn);
		this.rightVillagerLeg = (new ModelRenderer(this, 0, 22)).setTextureSize(textureWidthIn, textureHeightIn);
		this.rightVillagerLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
		this.rightVillagerLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSizeIn);
		this.leftVillagerLeg = (new ModelRenderer(this, 0, 22)).setTextureSize(textureWidthIn, textureHeightIn);
		this.leftVillagerLeg.mirror = true;
		this.leftVillagerLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
		this.leftVillagerLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSizeIn);
	}

	@Override
	public void setModelSlotVisible(EquipmentSlotType slotType) {
		switch(slotType) {
		case HEAD:
			this.villagerHead.showModel = true;
			this.villagerBody.showModel = false;
			this.villagerArms.showModel = false;
			this.rightVillagerLeg.showModel = false;
			this.leftVillagerLeg.showModel = false;
			break;
		case CHEST:
			this.villagerHead.showModel = false;
			this.villagerBody.showModel = true;
			this.villagerArms.showModel = true;
			this.rightVillagerLeg.showModel = false;
			this.leftVillagerLeg.showModel = false;
			break;
		case LEGS:
			this.villagerHead.showModel = false;
			this.villagerBody.showModel = true;
			this.villagerArms.showModel = false;
			this.rightVillagerLeg.showModel = true;
			this.leftVillagerLeg.showModel = true;
			break;
		case FEET:
			this.villagerHead.showModel = false;
			this.villagerBody.showModel = false;
			this.villagerArms.showModel = false;
			this.rightVillagerLeg.showModel = true;
			this.leftVillagerLeg.showModel = true;
			break;
		default:
			break;
		}
	}

	@Override
	public ModelRenderer getModelHead() {
		return this.villagerHead;
	}

	@Override
	public void func_217146_a(boolean visible) {}

	@Override
	public Iterable<ModelRenderer> getParts() {
		return ImmutableList.of(this.villagerHead, this.villagerBody, this.rightVillagerLeg, this.leftVillagerLeg, this.villagerArms);
	}

}
