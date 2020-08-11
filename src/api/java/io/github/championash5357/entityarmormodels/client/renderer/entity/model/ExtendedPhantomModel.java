package io.github.championash5357.entityarmormodels.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;

import io.github.championash5357.entityarmormodels.client.renderer.entity.model.vanilla.ExtendedSegmentedModel;
import net.minecraft.client.renderer.entity.model.PhantomModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.math.MathHelper;

//TODO: Same subclass issue
public class ExtendedPhantomModel<T extends LivingEntity> extends ExtendedSegmentedModel<T, PhantomModel<T>> {

	protected final ModelRenderer head;
	protected final ModelRenderer body;
	protected final ModelRenderer leftWingBody;
	protected final ModelRenderer leftWing;
	protected final ModelRenderer rightWingBody;
	protected final ModelRenderer rightWing;
	protected final ModelRenderer tail1;
	protected final ModelRenderer tail2;

	public ExtendedPhantomModel(float modelSizeIn) {
		this(modelSizeIn, 64, 64);
	}

	public ExtendedPhantomModel(float modelSizeIn, int textureWidthIn, int textureHeightIn) {
		this.textureWidth = textureWidthIn;
		this.textureHeight = textureHeightIn;
		this.body = new ModelRenderer(this, 0, 8);
		this.body.addBox(-3.0F, -2.0F, -8.0F, 5.0F, 3.0F, 9.0F);
		this.tail1 = new ModelRenderer(this, 3, 20);
		this.tail1.addBox(-2.0F, 0.0F, 0.0F, 3.0F, 2.0F, 6.0F);
		this.tail1.setRotationPoint(0.0F, -2.0F, 1.0F);
		this.body.addChild(this.tail1);
		this.tail2 = new ModelRenderer(this, 4, 29);
		this.tail2.addBox(-1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 6.0F);
		this.tail2.setRotationPoint(0.0F, 0.5F, 6.0F);
		this.tail1.addChild(this.tail2);
		this.leftWingBody = new ModelRenderer(this, 23, 12);
		this.leftWingBody.addBox(0.0F, 0.0F, 0.0F, 6.0F, 2.0F, 9.0F);
		this.leftWingBody.setRotationPoint(2.0F, -2.0F, -8.0F);
		this.leftWing = new ModelRenderer(this, 16, 24);
		this.leftWing.addBox(0.0F, 0.0F, 0.0F, 13.0F, 1.0F, 9.0F);
		this.leftWing.setRotationPoint(6.0F, 0.0F, 0.0F);
		this.leftWingBody.addChild(this.leftWing);
		this.rightWingBody = new ModelRenderer(this, 23, 12);
		this.rightWingBody.mirror = true;
		this.rightWingBody.addBox(-6.0F, 0.0F, 0.0F, 6.0F, 2.0F, 9.0F);
		this.rightWingBody.setRotationPoint(-3.0F, -2.0F, -8.0F);
		this.rightWing = new ModelRenderer(this, 16, 24);
		this.rightWing.mirror = true;
		this.rightWing.addBox(-13.0F, 0.0F, 0.0F, 13.0F, 1.0F, 9.0F);
		this.rightWing.setRotationPoint(-6.0F, 0.0F, 0.0F);
		this.rightWingBody.addChild(this.rightWing);
		this.leftWingBody.rotateAngleZ = 0.1F;
		this.leftWing.rotateAngleZ = 0.1F;
		this.rightWingBody.rotateAngleZ = -0.1F;
		this.rightWing.rotateAngleZ = -0.1F;
		this.body.rotateAngleX = -0.1F;
		this.head = new ModelRenderer(this, 0, 0);
		this.head.addBox(-4.0F, -2.0F, -5.0F, 7.0F, 3.0F, 5.0F);
		this.head.setRotationPoint(0.0F, 1.0F, -7.0F);
		this.head.rotateAngleX = 0.2F;
		this.body.addChild(this.head);
		this.body.addChild(this.leftWingBody);
		this.body.addChild(this.rightWingBody);
	}

	/**Temporary solution to sync values. Should be removed when actual fix is implemented*/
	@Override
	public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		float f = ((float)(entityIn.getEntityId() * 3) + ageInTicks) * 0.13F;
		this.leftWingBody.rotateAngleZ = MathHelper.cos(f) * 16.0F * ((float)Math.PI / 180F);
		this.leftWing.rotateAngleZ = MathHelper.cos(f) * 16.0F * ((float)Math.PI / 180F);
		this.rightWingBody.rotateAngleZ = -this.leftWingBody.rotateAngleZ;
		this.rightWing.rotateAngleZ = -this.leftWing.rotateAngleZ;
		this.tail1.rotateAngleX = -(5.0F + MathHelper.cos(f * 2.0F) * 5.0F) * ((float)Math.PI / 180F);
		this.tail2.rotateAngleX = -(5.0F + MathHelper.cos(f * 2.0F) * 5.0F) * ((float)Math.PI / 180F);
	}

	@Override
	public void setModelSlotVisible(T entityIn, EquipmentSlotType slotType) {
		switch(slotType) {
		case HEAD:
			this.head.showModel = true;
			this.body.showModel = false;
			this.leftWingBody.showModel = false;
			this.leftWing.showModel = false;
			this.rightWingBody.showModel = false;
			this.rightWing.showModel = false;
			this.tail1.showModel = false;
			this.tail2.showModel = false;
			break;
		case CHEST:
			this.head.showModel = false;
			this.body.showModel = true;
			this.leftWingBody.showModel = true;
			this.leftWing.showModel = true;
			this.rightWingBody.showModel = true;
			this.rightWing.showModel = true;
			this.tail1.showModel = false;
			this.tail2.showModel = false;
			break;
		case LEGS:
			this.head.showModel = false;
			this.body.showModel = false;
			this.leftWingBody.showModel = false;
			this.leftWing.showModel = false;
			this.rightWingBody.showModel = false;
			this.rightWing.showModel = false;
			this.tail1.showModel = true;
			this.tail2.showModel = true;
			break;
		case FEET:
			this.head.showModel = false;
			this.body.showModel = false;
			this.leftWingBody.showModel = false;
			this.leftWing.showModel = false;
			this.rightWingBody.showModel = false;
			this.rightWing.showModel = false;
			this.tail1.showModel = false;
			this.tail2.showModel = true;
			break;
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
		return ImmutableList.of(this.body);
	}

	@Override
	public void preRender(MatrixStack stack, boolean isChild) {
		stack.translate(-0.03125d, 0.125d, -0.25d);
		stack.scale(0.8125f, 0.8125f, 0.8125f);
	}
}
