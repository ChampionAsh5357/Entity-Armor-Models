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

package io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.vanilla;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.DrownedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

/**
 * An extended version of {@link DrownedModel}. 
 * Custom armor models are already supported 
 * by Minecraft and Forge for this. You should 
 * extend {@link BipedModel} for custom armors 
 * relating to Drowned.
 * */
public class ExtendedDrownedModel<T extends LivingEntity, M extends BipedModel<T>> extends ExtendedBipedModel<T, M> {

	public ExtendedDrownedModel(float modelSize) {
		this(modelSize, false);
	}
	
	public ExtendedDrownedModel(float modelSize, boolean isArmor) {
		this(modelSize, 0.0f, 64, isArmor ? 32 : 64);
	}
	
	public ExtendedDrownedModel(float modelSize, float yOffset, int textureWidth, int textureHeight) {
		super(modelSize, yOffset, textureWidth, textureHeight);
		this.bipedRightArm = new ModelRenderer(this, 32, 48);
		this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSize);
		this.bipedRightArm.setRotationPoint(-5.0F, 2.0F + modelSize, 0.0F);
		this.bipedRightLeg = new ModelRenderer(this, 16, 48);
		this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSize);
		this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F + modelSize, 0.0F);
	}

}
