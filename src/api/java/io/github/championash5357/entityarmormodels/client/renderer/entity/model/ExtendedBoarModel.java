package io.github.championash5357.entityarmormodels.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;

import io.github.championash5357.entityarmormodels.client.renderer.entity.model.vanilla.ExtendedAgeableModel;
import net.minecraft.client.renderer.entity.model.BoarModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.IFlinging;
import net.minecraft.inventory.EquipmentSlotType;

//TODO: Finish implementing
public class ExtendedBoarModel<T extends MobEntity & IFlinging> extends ExtendedAgeableModel<T, BoarModel<T>> {

	private final ModelRenderer head;
	private final ModelRenderer body;
	private final ModelRenderer leftFrontLeg;
	private final ModelRenderer rightFrontLeg;
	private final ModelRenderer leftBackLeg;
	private final ModelRenderer rightBackLeg;

	public ExtendedBoarModel(float modelSizeIn) {
		this(modelSizeIn, 128, 64);
	}

	public ExtendedBoarModel(float modelSizeIn, int textureWidthIn, int textureHeightIn) {
		super(BoarModel.class, "func_225602_a_", "func_225600_b_", true, 8.0F, 6.0F, 1.9F, 2.0F, 24.0F);
		this.textureWidth = textureWidthIn;
		this.textureHeight = textureHeightIn;
		this.body = new ModelRenderer(this);
		this.body.setRotationPoint(0.0F, 7.0F, 0.0F);
		this.body.setTextureOffset(1, 1).addBox(-8.0F, -7.0F, -13.0F, 16.0F, 14.0F, 26.0F);
		ModelRenderer hair = new ModelRenderer(this);
		hair.setRotationPoint(0.0F, -14.0F, -5.0F);
		hair.setTextureOffset(90, 33).addBox(0.0F, 0.0F, -9.0F, 0.0F, 10.0F, 19.0F, 0.001F);
		this.body.addChild(hair);
		this.head = new ModelRenderer(this);
		this.head.setRotationPoint(0.0F, 2.0F, -12.0F);
		this.head.setTextureOffset(61, 1).addBox(-7.0F, -3.0F, -19.0F, 14.0F, 6.0F, 19.0F);
		ModelRenderer leftTusk = new ModelRenderer(this);
		leftTusk.setRotationPoint(-6.0F, -2.0F, -3.0F);
		leftTusk.setTextureOffset(1, 1).addBox(-6.0F, -1.0F, -2.0F, 6.0F, 1.0F, 4.0F);
		leftTusk.rotateAngleZ = -0.6981317F;
		this.head.addChild(leftTusk);
		ModelRenderer rightTusk = new ModelRenderer(this);
		rightTusk.setRotationPoint(6.0F, -2.0F, -3.0F);
		rightTusk.setTextureOffset(1, 6).addBox(0.0F, -1.0F, -2.0F, 6.0F, 1.0F, 4.0F);
		rightTusk.rotateAngleZ = 0.6981317F;
		this.head.addChild(rightTusk);
		ModelRenderer modelrenderer = new ModelRenderer(this);
		modelrenderer.setRotationPoint(-7.0F, 2.0F, -12.0F);
		modelrenderer.setTextureOffset(10, 13).addBox(-1.0F, -11.0F, -1.0F, 2.0F, 11.0F, 2.0F);
		this.head.addChild(modelrenderer);
		ModelRenderer modelrenderer1 = new ModelRenderer(this);
		modelrenderer1.setRotationPoint(7.0F, 2.0F, -12.0F);
		modelrenderer1.setTextureOffset(1, 13).addBox(-1.0F, -11.0F, -1.0F, 2.0F, 11.0F, 2.0F);
		this.head.addChild(modelrenderer1);
		this.head.rotateAngleX = 0.87266463F;
		this.leftFrontLeg = new ModelRenderer(this);
		this.leftFrontLeg.setRotationPoint(-4.0F, 10.0F, -8.5F);
		this.leftFrontLeg.setTextureOffset(66, 42).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 14.0F, 6.0F);
		this.rightFrontLeg = new ModelRenderer(this);
		this.rightFrontLeg.setRotationPoint(4.0F, 10.0F, -8.5F);
		this.rightFrontLeg.setTextureOffset(41, 42).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 14.0F, 6.0F);
		this.leftBackLeg = new ModelRenderer(this);
		this.leftBackLeg.setRotationPoint(-5.0F, 13.0F, 10.0F);
		this.leftBackLeg.setTextureOffset(21, 45).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 11.0F, 5.0F);
		this.rightBackLeg = new ModelRenderer(this);
		this.rightBackLeg.setRotationPoint(5.0F, 13.0F, 10.0F);
		this.rightBackLeg.setTextureOffset(0, 45).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 11.0F, 5.0F);
	}

	@Override
	public void setModelSlotVisible(T entityIn, EquipmentSlotType slotType) {
		switch(slotType) {
		case HEAD:
			head.showModel = true;
			body.showModel = false;
			leftFrontLeg.showModel = false;
			rightFrontLeg.showModel = false;
			leftBackLeg.showModel = false;
			rightBackLeg.showModel = false;
			break;
		case CHEST:
			head.showModel = false;
			body.showModel = true;
			leftFrontLeg.showModel = false;
			rightFrontLeg.showModel = false;
			leftBackLeg.showModel = false;
			rightBackLeg.showModel = false;
			break;
		case LEGS:
			head.showModel = false;
			body.showModel = true;
			leftFrontLeg.showModel = true;
			rightFrontLeg.showModel = true;
			leftBackLeg.showModel = true;
			rightBackLeg.showModel = true;
			break;
		case FEET:
			head.showModel = false;
			body.showModel = false;
			leftFrontLeg.showModel = true;
			rightFrontLeg.showModel = true;
			leftBackLeg.showModel = true;
			rightBackLeg.showModel = true;
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
	protected Iterable<ModelRenderer> getHeadParts() {
		return ImmutableList.of(this.head);
	}

	@Override
	protected Iterable<ModelRenderer> getBodyParts() {
		return ImmutableList.of(this.body, this.leftFrontLeg, this.rightFrontLeg, this.leftBackLeg, this.rightBackLeg);
	}
}