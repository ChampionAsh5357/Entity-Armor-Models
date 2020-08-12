package io.github.championash5357.entityarmormodels.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;

import io.github.championash5357.entityarmormodels.client.renderer.entity.model.vanilla.ExtendedSegmentedModel;
import net.minecraft.client.renderer.entity.model.RavagerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.monster.RavagerEntity;
import net.minecraft.inventory.EquipmentSlotType;

//TODO: Don't be lazy, copyModelAngles might need to be fixed (might be fine though), also need textures, head, and elytra
public class ExtendedRavagerModel extends ExtendedSegmentedModel<RavagerEntity, RavagerModel> {

	protected final ModelRenderer head;
	protected final ModelRenderer body;
	protected final ModelRenderer legBackRight;
	protected final ModelRenderer legBackLeft;
	protected final ModelRenderer legFrontRight;
	protected final ModelRenderer legFrontLeft;
	protected final ModelRenderer neck;

	public ExtendedRavagerModel(float modelSizeIn) {
		this(modelSizeIn, 128, 128);
	}

	public ExtendedRavagerModel(float modelSizeIn, int textureWidthIn, int textureHeightIn) {
		this.textureWidth = textureWidthIn;
		this.textureHeight = textureHeightIn;
		this.neck = new ModelRenderer(this);
		this.neck.setRotationPoint(0.0F, -7.0F, -1.5F);
		this.neck.setTextureOffset(68, 73).addBox(-5.0F, -1.0F, -18.0F, 10.0F, 10.0F, 18.0F, modelSizeIn);
		this.head = new ModelRenderer(this);
		this.head.setRotationPoint(0.0F, 16.0F, -17.0F);
		this.head.setTextureOffset(0, 0).addBox(-8.0F, -20.0F, -14.0F, 16.0F, 20.0F, 16.0F, modelSizeIn);
		this.head.setTextureOffset(0, 0).addBox(-2.0F, -6.0F, -18.0F, 4.0F, 8.0F, 4.0F, modelSizeIn);
		ModelRenderer modelrenderer = new ModelRenderer(this);
		modelrenderer.setRotationPoint(-10.0F, -14.0F, -8.0F);
		modelrenderer.setTextureOffset(74, 55).addBox(0.0F, -14.0F, -2.0F, 2.0F, 14.0F, 4.0F, modelSizeIn);
		modelrenderer.rotateAngleX = 1.0995574F;
		this.head.addChild(modelrenderer);
		ModelRenderer modelrenderer1 = new ModelRenderer(this);
		modelrenderer1.mirror = true;
		modelrenderer1.setRotationPoint(8.0F, -14.0F, -8.0F);
		modelrenderer1.setTextureOffset(74, 55).addBox(0.0F, -14.0F, -2.0F, 2.0F, 14.0F, 4.0F, modelSizeIn);
		modelrenderer1.rotateAngleX = 1.0995574F;
		this.head.addChild(modelrenderer1);
		ModelRenderer jaw = new ModelRenderer(this);
		jaw.setRotationPoint(0.0F, -2.0F, 2.0F);
		jaw.setTextureOffset(0, 36).addBox(-8.0F, 0.0F, -16.0F, 16.0F, 3.0F, 16.0F, modelSizeIn);
		this.head.addChild(jaw);
		this.neck.addChild(this.head);
		this.body = new ModelRenderer(this);
		this.body.setTextureOffset(0, 55).addBox(-7.0F, -10.0F, -7.0F, 14.0F, 16.0F, 20.0F, modelSizeIn);
		this.body.setTextureOffset(0, 91).addBox(-6.0F, 6.0F, -7.0F, 12.0F, 13.0F, 18.0F, modelSizeIn);
		this.body.setRotationPoint(0.0F, 1.0F, 2.0F);
		this.legBackRight = new ModelRenderer(this, 96, 0);
		this.legBackRight.addBox(-4.0F, 0.0F, -4.0F, 8.0F, 37.0F, 8.0F, modelSizeIn);
		this.legBackRight.setRotationPoint(-8.0F, -13.0F, 18.0F);
		this.legBackLeft = new ModelRenderer(this, 96, 0);
		this.legBackLeft.mirror = true;
		this.legBackLeft.addBox(-4.0F, 0.0F, -4.0F, 8.0F, 37.0F, 8.0F, modelSizeIn);
		this.legBackLeft.setRotationPoint(8.0F, -13.0F, 18.0F);
		this.legFrontRight = new ModelRenderer(this, 64, 0);
		this.legFrontRight.addBox(-4.0F, 0.0F, -4.0F, 8.0F, 37.0F, 8.0F, modelSizeIn);
		this.legFrontRight.setRotationPoint(-8.0F, -13.0F, -5.0F);
		this.legFrontLeft = new ModelRenderer(this, 64, 0);
		this.legFrontLeft.mirror = true;
		this.legFrontLeft.addBox(-4.0F, 0.0F, -4.0F, 8.0F, 37.0F, 8.0F, modelSizeIn);
		this.legFrontLeft.setRotationPoint(8.0F, -13.0F, -5.0F);
	}

	@Override
	public void setModelSlotVisible(RavagerEntity entityIn, EquipmentSlotType slotType) {
		switch(slotType) {
		case HEAD:
			this.neck.showModel = true;
			this.body.showModel = false;
			this.legBackLeft.showModel = false;
			this.legBackRight.showModel = false;
			this.legFrontLeft.showModel = false;
			this.legFrontRight.showModel = false;
		case CHEST:
			this.neck.showModel = false;
			this.body.showModel = true;
			this.legBackLeft.showModel = false;
			this.legBackRight.showModel = false;
			this.legFrontLeft.showModel = false;
			this.legFrontRight.showModel = false;
		case LEGS:
			this.neck.showModel = false;
			this.body.showModel = false;
			this.legBackLeft.showModel = true;
			this.legBackRight.showModel = true;
			this.legFrontLeft.showModel = true;
			this.legFrontRight.showModel = true;
		case FEET:
			this.neck.showModel = false;
			this.body.showModel = false;
			this.legBackLeft.showModel = true;
			this.legBackRight.showModel = true;
			this.legFrontLeft.showModel = true;
			this.legFrontRight.showModel = true;
		default:
			break;
		}
	}

	@Override
	public ModelRenderer getModelHead() {
		return this.head;
	}

	@Override
	public Iterable<ModelRenderer> getParts() {
		 return ImmutableList.of(this.neck, this.body, this.legBackRight, this.legBackLeft, this.legFrontRight, this.legFrontLeft);
	}
}
