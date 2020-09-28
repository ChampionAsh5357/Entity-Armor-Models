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
import net.minecraft.client.renderer.entity.model.CreeperModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;

/**
 * An extended version of {@link CreeperModel}. 
 * Extend this model to apply custom entity armors 
 * to a Creeper.
 * */
public class ExtendedCreeperModel<T extends LivingEntity> extends ExtendedSegmentedModel<T, CreeperModel<T>> {

	protected final ModelRenderer head;
	protected final ModelRenderer body;
	protected final ModelRenderer leg1;
	protected final ModelRenderer leg2;
	protected final ModelRenderer leg3;
	protected final ModelRenderer leg4;

	public ExtendedCreeperModel(float modelSize) {
		this(modelSize, 64, 32);
	}

	public ExtendedCreeperModel(float modelSize, int textureWidth, int textureHeight) {
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;
		this.head = new ModelRenderer(this, 0, 0);
		this.head.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, modelSize);
		this.head.setRotationPoint(0.0F, 6.0F, 0.0F);
		this.body = new ModelRenderer(this, 16, 16);
		this.body.addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, modelSize);
		this.body.setRotationPoint(0.0F, 6.0F, 0.0F);
		this.leg1 = new ModelRenderer(this, 0, 16);
		this.leg1.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, modelSize);
		this.leg1.setRotationPoint(-2.0F, 18.0F, 4.0F);
		this.leg2 = new ModelRenderer(this, 0, 16);
		this.leg2.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, modelSize);
		this.leg2.setRotationPoint(2.0F, 18.0F, 4.0F);
		this.leg3 = new ModelRenderer(this, 0, 16);
		this.leg3.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, modelSize);
		this.leg3.setRotationPoint(-2.0F, 18.0F, -4.0F);
		this.leg4 = new ModelRenderer(this, 0, 16);
		this.leg4.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, modelSize);
		this.leg4.setRotationPoint(2.0F, 18.0F, -4.0F);
	}

	@Override
	public void setModelSlotVisible(T entity, EquipmentSlotType slotType) {
		switch(slotType) {
		case HEAD:
			this.head.showModel = true;
			this.body.showModel = false;
			this.leg1.showModel = false;
			this.leg2.showModel = false;
			this.leg3.showModel = false;
			this.leg4.showModel = false;
			break;
		case CHEST:
			this.head.showModel = false;
			this.body.showModel = true;
			this.leg1.showModel = false;
			this.leg2.showModel = false;
			this.leg3.showModel = false;
			this.leg4.showModel = false;
			break;
		case LEGS:
			this.head.showModel = false;
			this.body.showModel = true;
			this.leg1.showModel = true;
			this.leg2.showModel = true;
			this.leg3.showModel = true;
			this.leg4.showModel = true;
			break;
		case FEET:
			this.head.showModel = false;
			this.body.showModel = false;
			this.leg1.showModel = true;
			this.leg2.showModel = true;
			this.leg3.showModel = true;
			this.leg4.showModel = true;
			break;
		default:
			break;
		}
	}

	@Override
	public Iterable<ModelRenderer> getParts() {
		return ImmutableList.of(this.head, this.body, this.leg1, this.leg2, this.leg3, this.leg4);
	}

	@Override
	public void skullRender(MatrixStack stack, boolean isChild) {
		stack.translate(0.0d, -0.0625d, 0.0d);
	}

	@Override
	public ModelRenderer getModelHead() {
		return head;
	}

	@Override
	public ModelRenderer getBody() {
		return body;
	}
}
