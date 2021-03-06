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
import java.util.Random;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.mojang.blaze3d.matrix.MatrixStack;

import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.vanilla.ExtendedSegmentedModel;
import net.minecraft.client.renderer.entity.model.MagmaCubeModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.inventory.EquipmentSlotType;

/**
 * An extended version of {@link MagmaCubeModel}. 
 * Extend this model to apply custom entity armors 
 * to a Magma Cube.
 * */
public class ExtendedMagmaCubeModel<T extends SlimeEntity> extends ExtendedSegmentedModel<T, MagmaCubeModel<T>> {

	private final ModelRenderer[] segments;
	protected final ModelRenderer core;
	private final ImmutableList<ModelRenderer> renderers;

	public ExtendedMagmaCubeModel(float modelSize) {
		this(modelSize, true);
	}

	public ExtendedMagmaCubeModel(float modelSize, boolean isArmor) {
		this(modelSize, 64, 32, isArmor);
	}

	public ExtendedMagmaCubeModel(float modelSize, int textureWidth, int textureHeight, boolean isArmor) {
		this.textureHeight = textureHeight;
		this.textureWidth = textureWidth;
		if(isArmor) {
			this.segments = new ModelRenderer[1];
			this.segments[0] = new ModelRenderer(this, 0, 0);
			this.segments[0].addBox(-4.0f, 16.0f, -4.0f, 8.0f, 8.0f, 8.0f, modelSize);
		} else {
			this.segments = new ModelRenderer[8];
			for(int i = 0; i < this.segments.length; ++i) {
				int j = i % 2 == 0 ? 0 : 32;
				int k = (i > 3 ? i - 2 : i) / 2 * 27;
				if (i == 2) {
					j = 24;
					k = 9;
				} else if (i == 3) {
					j = 24;
					k = 18;
				}

				this.segments[i] = new ModelRenderer(this, j, k);
				this.segments[i].addBox(-4.0F, (float)(16 + i), -4.0F, 8.0F, 1.0F, 8.0F, modelSize, 0.0f, modelSize);
			}
		}
		this.core = new ModelRenderer(this, 0, 16);
		this.core.addBox(-2.0F, 18.0F, -2.0F, 4.0F, 4.0F, 4.0F, modelSize);
		Builder<ModelRenderer> builder = ImmutableList.builder();
		builder.add(this.core);
		builder.addAll(Arrays.asList(this.segments));
		this.renderers = builder.build();
	}

	@Override
	public Iterable<ModelRenderer> getParts() {
		return this.renderers;
	}
	
	public ImmutableList<ModelRenderer> getSegments() {
		return ImmutableList.copyOf(segments);
	}
	
	@Override
	public void offset(LivingEntity entity, boolean wearingArmor, MatrixStack stack, boolean isChild) {
		stack.translate(0.0d, 1.0d, 0.125d);
	}

	@Override
	public void setModelSlotVisible(T entity, EquipmentSlotType slotType) {
		switch(slotType) {
		case HEAD:
			for(ModelRenderer segment : segments) segment.showModel = true;
			this.core.showModel = false;
			break;
		case CHEST:
			for(ModelRenderer segment : segments) segment.showModel = false;
			this.core.showModel = true;
			break;
		case LEGS:
		case FEET:
		default:
			for(ModelRenderer segment : segments) segment.showModel = false;
			this.core.showModel = false;
			break;
		}
	}

	@Override
	public void preRender(MatrixStack stack, boolean isChild) {
		stack.translate(0.0d, 1.5d, 0.0d);
	}
	
	@Override
	public void skullRender(MatrixStack stack, boolean isChild) {
		stack.translate(0.0d, -0.0625d, 0.0d);
	}
	
	@Override
	public ModelRenderer getModelHead() {
		return this.segments[0];
	}

	@Override
	public ModelRenderer getRandomModelRenderer(Random random) {
		return this.segments[random.nextInt(this.segments.length)];
	}
	
	@Override
	public String armorTextureExpansion() {
		return this.segments.length > 1 ? "_split" : super.armorTextureExpansion();
	}

	@Override
	public ModelRenderer getBody() {
		return this.segments[0];
	}
}
