package io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.vanilla;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.renderer.entity.model.QuadrupedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;

public abstract class ExtendedQuadrupedModel<T extends LivingEntity, M extends QuadrupedModel<T>> extends ExtendedAgeableModel<T, M> {

	protected ModelRenderer headModel;
	protected ModelRenderer body;
	protected ModelRenderer legBackRight;
	protected ModelRenderer legBackLeft;
	protected ModelRenderer legFrontRight;
	protected ModelRenderer legFrontLeft;

	protected ExtendedQuadrupedModel(float modelSize, int textureWidth, int textureHeight, float yOffset, boolean isChildHeadScaled, float childHeadOffsetY, float childHeadOffsetZ, float childHeadScale, float childBodyScale, float childBodyOffsetY) {
		super(isChildHeadScaled, childHeadOffsetY, childHeadOffsetZ, childHeadScale, childBodyScale, childBodyOffsetY);
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;
		this.headModel = new ModelRenderer(this, 0, 0);
		this.headModel.addBox(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F, modelSize);
		this.headModel.setRotationPoint(0.0F, 18 - yOffset, -6.0F);
		this.body = new ModelRenderer(this, 28, 8);
		this.body.addBox(-5.0F, -10.0F, -7.0F, 10.0F, 16.0F, 8.0F, modelSize);
		this.body.setRotationPoint(0.0F, 17 - yOffset, 2.0F);
		this.legBackRight = new ModelRenderer(this, 0, 16);
		this.legBackRight.addBox(-2.0F, 0.0F, -2.0F, 4.0F, yOffset, 4.0F, modelSize);
		this.legBackRight.setRotationPoint(-3.0F, 24 - yOffset, 7.0F);
		this.legBackLeft = new ModelRenderer(this, 0, 16);
		this.legBackLeft.addBox(-2.0F, 0.0F, -2.0F, 4.0F, yOffset, 4.0F, modelSize);
		this.legBackLeft.setRotationPoint(3.0F, 24 - yOffset, 7.0F);
		this.legFrontRight = new ModelRenderer(this, 0, 16);
		this.legFrontRight.addBox(-2.0F, 0.0F, -2.0F, 4.0F, yOffset, 4.0F, modelSize);
		this.legFrontRight.setRotationPoint(-3.0F, 24 - yOffset, -5.0F);
		this.legFrontLeft = new ModelRenderer(this, 0, 16);
		this.legFrontLeft.addBox(-2.0F, 0.0F, -2.0F, 4.0F, yOffset, 4.0F, modelSize);
		this.legFrontLeft.setRotationPoint(3.0F, 24 - yOffset, -5.0F);
	}

	@Override
	public void setModelSlotVisible(T entity, EquipmentSlotType slotType) {
		switch(slotType) {
		case HEAD:
			this.headModel.showModel = true;
			this.body.showModel = false;
			this.legBackLeft.showModel = false;
			this.legBackRight.showModel = false;
			this.legFrontLeft.showModel = false;
			this.legFrontRight.showModel = false;
			break;
		case CHEST:
			this.headModel.showModel = false;
			this.body.showModel = true;
			this.legBackLeft.showModel = false;
			this.legBackRight.showModel = false;
			this.legFrontLeft.showModel = false;
			this.legFrontRight.showModel = false;
			break;
		case LEGS:
			this.headModel.showModel = false;
			this.body.showModel = false;
			this.legBackLeft.showModel = true;
			this.legBackRight.showModel = true;
			this.legFrontLeft.showModel = true;
			this.legFrontRight.showModel = true;
			break;
		case FEET:
			this.headModel.showModel = false;
			this.body.showModel = false;
			this.legBackLeft.showModel = true;
			this.legBackRight.showModel = true;
			this.legFrontLeft.showModel = true;
			this.legFrontRight.showModel = true;
			break;
		default:
			break;
		}
	}

	@Override
	public ModelRenderer getModelHead() {
		return this.headModel;
	}

	@Override
	protected Iterable<ModelRenderer> getHeadParts() {
		return ImmutableList.of(this.headModel);
	}

	@Override
	protected Iterable<ModelRenderer> getBodyParts() {
		return ImmutableList.of(this.body, this.legBackRight, this.legBackLeft, this.legFrontRight, this.legFrontLeft);
	}
	
	@Override
	public ModelRenderer getBody() {
		return body;
	}
}
