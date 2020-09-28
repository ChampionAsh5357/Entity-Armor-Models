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

import java.util.Arrays;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.mojang.blaze3d.matrix.MatrixStack;

import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.vanilla.ExtendedSegmentedModel;
import net.minecraft.client.renderer.entity.model.BlazeModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;

/**
 * An extended version of {@link BlazeModel}. 
 * Extend this model to apply custom entity armors 
 * to a Blaze.
 * */
public class ExtendedBlazeModel<T extends LivingEntity> extends ExtendedSegmentedModel<T, BlazeModel<T>> {

	private final ModelRenderer[] blazeSticks;
	protected final ModelRenderer blazeHead;
	private final ImmutableList<ModelRenderer> renderers;

	public ExtendedBlazeModel(float modelSize) {
		this(modelSize, 64, 32);
	}

	public ExtendedBlazeModel(float modelSize, int textureWidth, int textureHeight) {
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;
		this.blazeHead = new ModelRenderer(this, 0, 0);
		this.blazeHead.addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, modelSize);
		this.blazeSticks = new ModelRenderer[12];

		for(int i = 0; i < this.blazeSticks.length; ++i) {
			this.blazeSticks[i] = new ModelRenderer(this, 0, 16);
			this.blazeSticks[i].addBox(0.0F, 0.0F, 0.0F, 2.0F, 8.0F, 2.0F, modelSize);
		}

		Builder<ModelRenderer> builder = ImmutableList.builder();
		builder.add(this.blazeHead);
		builder.addAll(Arrays.asList(this.blazeSticks));
		this.renderers = builder.build();
	}

	public ImmutableList<ModelRenderer> getBlazeSticks() {
		return ImmutableList.copyOf(blazeSticks);
	}

	@Override
	public void setModelSlotVisible(T entity, EquipmentSlotType slotType) {
		switch(slotType) {
		case HEAD:
			for(ModelRenderer segment : blazeSticks) segment.showModel = false;
			this.blazeHead.showModel = true;
			break;
		case CHEST:
		default:
			for(ModelRenderer segment : blazeSticks) segment.showModel = false;
			this.blazeHead.showModel = false;
			break;
		case LEGS:
		case FEET:
			for(ModelRenderer segment : blazeSticks) segment.showModel = true;
			this.blazeHead.showModel = false;
			break;
		}
	}

	@Override
	public void preRender(MatrixStack stack, boolean isChild) {
		stack.translate(0.0d, 0.25d, 0.0d);
	}

	@Override
	public void skullRender(MatrixStack stack, boolean isChild) {
		stack.translate(0.0d, -0.0625d, 0.0d);
	}

	@Override
	public void offset(LivingEntity entity, boolean wearingArmor, MatrixStack stack, boolean isChild) {
		stack.translate(0.0d, 0.0d, 0.125d);
	}

	@Override
	public ModelRenderer getModelHead() {
		return this.blazeHead;
	}

	@Override
	public Iterable<ModelRenderer> getParts() {
		return this.renderers;
	}

	@Override
	public ModelRenderer getBody() {
		return blazeHead;
	}
}
