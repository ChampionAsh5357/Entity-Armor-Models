package io.github.championash5357.entityarmormodels.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;

import io.github.championash5357.entityarmormodels.client.renderer.entity.model.vanilla.ExtendedSegmentedModel;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.client.renderer.entity.model.PhantomModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;

/**
 * An extended version of {@link PhantomModel}. 
 * Extend this model to apply custom entity armors 
 * to a Phantom.
 * */
public class ExtendedPhantomModel<T extends LivingEntity> extends ExtendedSegmentedModel<T, PhantomModel<T>> {

	protected final IndependentModelRenderer head;
	protected final IndependentModelRenderer body;
	protected final IndependentModelRenderer leftWingBody;
	protected final IndependentModelRenderer leftWing;
	protected final IndependentModelRenderer rightWingBody;
	protected final IndependentModelRenderer rightWing;
	protected final IndependentModelRenderer tail1;
	protected final IndependentModelRenderer tail2;

	public ExtendedPhantomModel(float modelSizeIn) {
		this(modelSizeIn, 64, 64);
	}

	public ExtendedPhantomModel(float modelSizeIn, int textureWidthIn, int textureHeightIn) {
		this.textureWidth = textureWidthIn;
		this.textureHeight = textureHeightIn;
		this.body = new IndependentModelRenderer(this, 0, 8);
		this.body.addBox(-3.0F, -2.0F, -8.0F, 5.0F, 3.0F, 9.0F, modelSizeIn);
		this.tail1 = new IndependentModelRenderer(this, 3, 20);
		this.tail1.addBox(-2.0F, 0.0F, 0.0F, 3.0F, 2.0F, 6.0F, modelSizeIn);
		this.tail1.setRotationPoint(0.0F, -2.0F, 1.0F);
		this.body.addChild(this.tail1);
		this.tail2 = new IndependentModelRenderer(this, 4, 29);
		this.tail2.addBox(-1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 6.0F, modelSizeIn);
		this.tail2.setRotationPoint(0.0F, 0.5F, 6.0F);
		this.tail1.addChild(this.tail2);
		this.leftWingBody = new IndependentModelRenderer(this, 23, 12);
		this.leftWingBody.addBox(0.0F, 0.0F, 0.0F, 6.0F, 2.0F, 9.0F, modelSizeIn);
		this.leftWingBody.setRotationPoint(2.0F, -2.0F, -8.0F);
		this.leftWing = new IndependentModelRenderer(this, 16, 24);
		this.leftWing.addBox(0.0F, 0.0F, 0.0F, 13.0F, 1.0F, 9.0F, modelSizeIn);
		this.leftWing.setRotationPoint(6.0F, 0.0F, 0.0F);
		this.leftWingBody.addChild(this.leftWing);
		this.rightWingBody = new IndependentModelRenderer(this, 23, 12);
		this.rightWingBody.mirror = true;
		this.rightWingBody.addBox(-6.0F, 0.0F, 0.0F, 6.0F, 2.0F, 9.0F, modelSizeIn);
		this.rightWingBody.setRotationPoint(-3.0F, -2.0F, -8.0F);
		this.rightWing = new IndependentModelRenderer(this, 16, 24);
		this.rightWing.mirror = true;
		this.rightWing.addBox(-13.0F, 0.0F, 0.0F, 13.0F, 1.0F, 9.0F, modelSizeIn);
		this.rightWing.setRotationPoint(-6.0F, 0.0F, 0.0F);
		this.rightWingBody.addChild(this.rightWing);
		this.leftWingBody.rotateAngleZ = 0.1F;
		this.leftWing.rotateAngleZ = 0.1F;
		this.rightWingBody.rotateAngleZ = -0.1F;
		this.rightWing.rotateAngleZ = -0.1F;
		this.body.rotateAngleX = -0.1F;
		this.head = new IndependentModelRenderer(this, 0, 0);
		this.head.addBox(-4.0F, -2.0F, -5.0F, 7.0F, 3.0F, 5.0F, modelSizeIn);
		this.head.setRotationPoint(0.0F, 1.0F, -7.0F);
		this.head.rotateAngleX = 0.2F;
		this.body.addChild(this.head);
		this.body.addChild(this.leftWingBody);
		this.body.addChild(this.rightWingBody);
	}

	@Override
	public void copyAttributesOfModel(PhantomModel<T> model) {
		model.copyModelAttributesTo(this);
		model.getParts().forEach(body -> {
			this.body.copyModelAngles(body);
			ObjectList<ModelRenderer> bodyChilds = getChildModels(body);
			this.tail1.copyModelAngles(bodyChilds.get(0));
			this.tail2.copyModelAngles(getChildModels(bodyChilds.get(0)).get(0));
			this.head.copyModelAngles(bodyChilds.get(1));
			this.leftWingBody.copyModelAngles(bodyChilds.get(2));
			this.leftWing.copyModelAngles(getChildModels(bodyChilds.get(2)).get(0));
			this.rightWingBody.copyModelAngles(bodyChilds.get(3));
			this.rightWing.copyModelAngles(getChildModels(bodyChilds.get(3)).get(0));
		});
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
