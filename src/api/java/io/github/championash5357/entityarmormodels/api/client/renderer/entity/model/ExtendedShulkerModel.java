/*
 * Entity Armor Models
 * Copyright (C) 2020 ChampionAsh5357
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation version 3.0 of the License.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.github.championash5357.entityarmormodels.api.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;

import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.vanilla.ExtendedSegmentedModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.ShulkerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.ShulkerEntity;
import net.minecraft.inventory.EquipmentSlotType;

/**
 * An extended version of {@link ShulkerModel}. 
 * Extend this model to apply custom entity armors 
 * to a Shulker.
 * */
public class ExtendedShulkerModel<T extends ShulkerEntity> extends ExtendedSegmentedModel<T, ShulkerModel<T>> {

	protected final ModelRenderer base;
	protected final ModelRenderer lid;
	protected final ModelRenderer head;

	public ExtendedShulkerModel(float modelSize) {
		this(modelSize, 64, 64);
	}

	public ExtendedShulkerModel(float modelSize, int textureWidth, int textureHeight) {
		super(RenderType::getEntityCutoutNoCullZOffset);
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;
		this.base = new ModelRenderer(this, 0, 28);
		this.lid = new ModelRenderer(this, 0, 0);
		this.head = new ModelRenderer(this, 0, 52);
		this.lid.addBox(-8.0F, -16.0F, -8.0F, 16.0F, 12.0F, 16.0F, modelSize);
		this.lid.setRotationPoint(0.0F, 24.0F, 0.0F);
		this.base.addBox(-8.0F, -8.0F, -8.0F, 16.0F, 8.0F, 16.0F, modelSize);
		this.base.setRotationPoint(0.0F, 24.0F, 0.0F);
		this.head.addBox(-3.0F, 0.0F, -3.0F, 6.0F, 6.0F, 6.0F, modelSize);
		this.head.setRotationPoint(0.0F, 12.0F, 0.0F);
	}

	@Override
	public void copyAttributesOfModel(ShulkerModel<T> model) {
		model.copyModelAttributesTo(this);
		this.base.copyModelAngles(model.getBase());
		this.lid.copyModelAngles(model.getLid());
		this.head.copyModelAngles(model.getHead());
	}

	@Override
	public void setModelSlotVisible(T entity, EquipmentSlotType slotType) {
		switch(slotType) {
		case HEAD:
			this.base.showModel = false;
			this.lid.showModel = false;
			this.head.showModel = true;
			break;
		case CHEST:
			this.base.showModel = true;
			this.lid.showModel = true;
			this.head.showModel = false;
			break;
		case LEGS:
		case FEET:
		default:
			this.base.showModel = false;
			this.lid.showModel = false;
			this.head.showModel = false;
			break;
		}
	}

	@Override
	public void preRender(MatrixStack stack, boolean isChild) {
		stack.translate(0.0d, 0.5d, 0.0d);
	}

	@Override
	public ModelRenderer getModelHead() {
		return this.head;
	}

	@Override
	public void offset(LivingEntity entity, boolean wearingArmor, MatrixStack stack, boolean isChild) {
		stack.translate(0.0d, 0.0d, 0.0625d);
	}

	@Override
	public Iterable<ModelRenderer> getParts() {
		return ImmutableList.of(this.base, this.lid, this.head);
	}

	@Override
	public ModelRenderer getBody() {
		return head;
	}
}
