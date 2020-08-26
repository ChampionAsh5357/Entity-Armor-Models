package io.github.championash5357.entityarmormodels.client.renderer.entity.model.vanilla;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.entity.model.HorseModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.inventory.EquipmentSlotType;

/**
 * An extended version of {@link HorseModel}. 
 * Custom armor models are already supported 
 * by Minecraft and Forge for this. You should 
 * extend {@link HorseModel} for custom armors 
 * relating to Horse, Zombie Horse, and Skeleton 
 * Horse.
 * */
public class ExtendedHorseModel<T extends AbstractHorseEntity> extends ExtendedAgeableModel<T, HorseModel<T>> {

	protected final ModelRenderer body;
	protected final ModelRenderer head;
	protected final ModelRenderer leg1;
	protected final ModelRenderer leg2;
	protected final ModelRenderer leg3;
	protected final ModelRenderer leg4;
	protected final ModelRenderer childLeg1;
	protected final ModelRenderer childLeg2;
	protected final ModelRenderer childLeg3;
	protected final ModelRenderer childLeg4;

	public ExtendedHorseModel(float modelSizeIn) {
		this(modelSizeIn, 64, 64);
	}
	
	public ExtendedHorseModel(float modelSizeIn, int textureWidthIn, int textureHeightIn) {
		super(true, 16.2F, 1.36F, 2.7272F, 2.0F, 20.0F);
		this.textureWidth = textureWidthIn;
		this.textureHeight = textureHeightIn;
		this.body = new ModelRenderer(this, 0, 32);
		this.body.addBox(-5.0F, -8.0F, -17.0F, 10.0F, 10.0F, 22.0F, 0.05F + modelSizeIn);
		this.body.setRotationPoint(0.0F, 11.0F, 5.0F);
		this.head = new ModelRenderer(this, 0, 35);
		this.head.addBox(-2.05F, -6.0F, -2.0F, 4.0F, 12.0F, 7.0F, modelSizeIn);
		this.head.rotateAngleX = ((float)Math.PI / 6F);
		ModelRenderer modelrenderer = new ModelRenderer(this, 0, 13);
		modelrenderer.addBox(-3.0F, -11.0F, -2.0F, 6.0F, 5.0F, 7.0F, modelSizeIn);
		ModelRenderer modelrenderer1 = new ModelRenderer(this, 56, 36);
		modelrenderer1.addBox(-1.0F, -11.0F, 5.01F, 2.0F, 16.0F, 2.0F, modelSizeIn);
		ModelRenderer modelrenderer2 = new ModelRenderer(this, 0, 25);
		modelrenderer2.addBox(-2.0F, -11.0F, -7.0F, 4.0F, 5.0F, 5.0F, modelSizeIn);
		this.head.addChild(modelrenderer);
		this.head.addChild(modelrenderer1);
		this.head.addChild(modelrenderer2);
		this.createEars(this.head);
		this.leg1 = new ModelRenderer(this, 48, 21);
		this.leg1.mirror = true;
		this.leg1.addBox(-3.0F, -1.01F, -1.0F, 4.0F, 11.0F, 4.0F, modelSizeIn);
		this.leg1.setRotationPoint(4.0F, 14.0F, 7.0F);
		this.leg2 = new ModelRenderer(this, 48, 21);
		this.leg2.addBox(-1.0F, -1.01F, -1.0F, 4.0F, 11.0F, 4.0F, modelSizeIn);
		this.leg2.setRotationPoint(-4.0F, 14.0F, 7.0F);
		this.leg3 = new ModelRenderer(this, 48, 21);
		this.leg3.mirror = true;
		this.leg3.addBox(-3.0F, -1.01F, -1.9F, 4.0F, 11.0F, 4.0F, modelSizeIn);
		this.leg3.setRotationPoint(4.0F, 6.0F, -12.0F);
		this.leg4 = new ModelRenderer(this, 48, 21);
		this.leg4.addBox(-1.0F, -1.01F, -1.9F, 4.0F, 11.0F, 4.0F, modelSizeIn);
		this.leg4.setRotationPoint(-4.0F, 6.0F, -12.0F);
		this.childLeg1 = new ModelRenderer(this, 48, 21);
		this.childLeg1.mirror = true;
		this.childLeg1.addBox(-3.0F, -1.01F, -1.0F, 4.0F, 11.0F, 4.0F, modelSizeIn, modelSizeIn + 5.5F, modelSizeIn);
		this.childLeg1.setRotationPoint(4.0F, 14.0F, 7.0F);
		this.childLeg2 = new ModelRenderer(this, 48, 21);
		this.childLeg2.addBox(-1.0F, -1.01F, -1.0F, 4.0F, 11.0F, 4.0F, modelSizeIn, modelSizeIn + 5.5F, modelSizeIn);
		this.childLeg2.setRotationPoint(-4.0F, 14.0F, 7.0F);
		this.childLeg3 = new ModelRenderer(this, 48, 21);
		this.childLeg3.mirror = true;
		this.childLeg3.addBox(-3.0F, -1.01F, -1.9F, 4.0F, 11.0F, 4.0F, modelSizeIn, modelSizeIn + 5.5F, modelSizeIn);
		this.childLeg3.setRotationPoint(4.0F, 6.0F, -12.0F);
		this.childLeg4 = new ModelRenderer(this, 48, 21);
		this.childLeg4.addBox(-1.0F, -1.01F, -1.9F, 4.0F, 11.0F, 4.0F, modelSizeIn, modelSizeIn + 5.5F, modelSizeIn);
		this.childLeg4.setRotationPoint(-4.0F, 6.0F, -12.0F);
		ModelRenderer tail = new ModelRenderer(this, 42, 36);
		tail.addBox(-1.5F, 0.0F, 0.0F, 3.0F, 14.0F, 4.0F, modelSizeIn);
		tail.setRotationPoint(0.0F, -5.0F, 2.0F);
		tail.rotateAngleX = ((float)Math.PI / 6F);
		this.body.addChild(tail);
	}

	protected void createEars(ModelRenderer parent) {
		ModelRenderer modelrenderer = new ModelRenderer(this, 19, 16);
		modelrenderer.addBox(0.55F, -13.0F, 4.0F, 2.0F, 3.0F, 1.0F, -0.001F);
		ModelRenderer modelrenderer1 = new ModelRenderer(this, 19, 16);
		modelrenderer1.addBox(-2.55F, -13.0F, 4.0F, 2.0F, 3.0F, 1.0F, -0.001F);
		parent.addChild(modelrenderer);
		parent.addChild(modelrenderer1);
	}

	@Override
	public void setModelSlotVisible(T entityIn, EquipmentSlotType slotType) {}

	@Override
	public ModelRenderer getModelHead() {
		return this.head;
	}
	
	@Override
	public void preRender(MatrixStack stack, boolean isChild) {
		stack.translate(0.0d, -0.25d, 0.0d);
		if(isChild) stack.translate(0.0d, -0.125d, 0.125d);
	}

	@Override
	protected Iterable<ModelRenderer> getHeadParts() {
		return ImmutableList.of(this.head);
	}

	@Override
	protected Iterable<ModelRenderer> getBodyParts() {
		return ImmutableList.of(this.body, this.leg1, this.leg2, this.leg3, this.leg4, this.childLeg1, this.childLeg2, this.childLeg3, this.childLeg4);
	}
}
