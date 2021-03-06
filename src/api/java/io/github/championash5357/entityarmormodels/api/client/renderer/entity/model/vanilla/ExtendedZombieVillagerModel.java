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
import net.minecraft.client.renderer.entity.model.ZombieVillagerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

/**
 * An extended version of {@link ZombieVillagerModel}. 
 * Custom armor models are already supported 
 * by Minecraft and Forge for this. You should 
 * extend {@link BipedModel} for custom armors 
 * relating to Zombie Villager.
 * */
public class ExtendedZombieVillagerModel<T extends LivingEntity, M extends BipedModel<T>> extends ExtendedBipedModel<T, M> {

	public ExtendedZombieVillagerModel(float modelSize) {
		this(modelSize, false);
	}

	public ExtendedZombieVillagerModel(float modelSize, boolean isArmor) {
		this(modelSize, 0.0f, 64, isArmor ? 32 : 64, isArmor);
	}

	public ExtendedZombieVillagerModel(float modelSize, float yOffset, int textureWidth, int textureHeight, boolean isArmor) {
		super(modelSize, yOffset, textureWidth, textureHeight);
		if (isArmor) {
			this.bipedHead = new ModelRenderer(this, 0, 0);
			this.bipedHead.addBox(-4.0F, -10.0F, -4.0F, 8.0F, 8.0F, 8.0F, modelSize);
			this.bipedBody = new ModelRenderer(this, 16, 16);
			this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, modelSize + 0.1F);
			this.bipedRightLeg = new ModelRenderer(this, 0, 16);
			this.bipedRightLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
			this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSize + 0.1F);
			this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
			this.bipedLeftLeg.mirror = true;
			this.bipedLeftLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
			this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSize + 0.1F);
		} else {
			this.bipedHead = new ModelRenderer(this, 0, 0);
			this.bipedHead.setTextureOffset(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, modelSize);
			this.bipedHead.setTextureOffset(24, 0).addBox(-1.0F, -3.0F, -6.0F, 2.0F, 4.0F, 2.0F, modelSize);
			this.bipedHeadwear = new ModelRenderer(this, 32, 0);
			this.bipedHeadwear.addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, modelSize + 0.5F);
			ModelRenderer villagerHat = new ModelRenderer(this);
			villagerHat.setTextureOffset(30, 47).addBox(-8.0F, -8.0F, -6.0F, 16.0F, 16.0F, 1.0F, modelSize);
			villagerHat.rotateAngleX = (-(float)Math.PI / 2F);
			this.bipedHeadwear.addChild(villagerHat);
			this.bipedBody = new ModelRenderer(this, 16, 20);
			this.bipedBody.addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F, modelSize);
			this.bipedBody.setTextureOffset(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 18.0F, 6.0F, modelSize + 0.05F);
			this.bipedRightArm = new ModelRenderer(this, 44, 22);
			this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSize);
			this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
			this.bipedLeftArm = new ModelRenderer(this, 44, 22);
			this.bipedLeftArm.mirror = true;
			this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSize);
			this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
			this.bipedRightLeg = new ModelRenderer(this, 0, 22);
			this.bipedRightLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
			this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSize);
			this.bipedLeftLeg = new ModelRenderer(this, 0, 22);
			this.bipedLeftLeg.mirror = true;
			this.bipedLeftLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
			this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSize);
		}
	}

}
