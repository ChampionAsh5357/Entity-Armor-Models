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

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;

/**
 * An extended version of {@link BipedModel}. 
 * Custom armor models are already supported 
 * by Minecraft and Forge for this. You should 
 * extend {@link BipedModel} for custom armors 
 * relating to Husk, Giant, Zombie, Piglin, 
 * Piglin Brute, and Zombified Piglin.
 * */
public class ExtendedBipedModel<T extends LivingEntity, M extends BipedModel<T>> extends BipedModel<T> implements IVanillaEntityModel<T, M> {
	private List<ModelRenderer> modelRenderers;

	public ExtendedBipedModel(float modelSize) {
		super(modelSize);
	}

	public ExtendedBipedModel(float modelSize, float yOffset, int textureWidth, int textureHeight) {
		super(RenderType::getEntityCutoutNoCull, modelSize, yOffset, textureWidth, textureHeight);
	}

	@Override
	public void copyAttributesOfModel(M model) {
		model.copyModelAttributesTo(this);
		this.leftArmPose = model.leftArmPose;
		this.rightArmPose = model.rightArmPose;
		this.isSneak = model.isSneak;
		this.bipedHead.copyModelAngles(model.bipedHead);
		this.bipedHeadwear.copyModelAngles(model.bipedHeadwear);
		this.bipedBody.copyModelAngles(model.bipedBody);
		this.bipedRightArm.copyModelAngles(model.bipedRightArm);
		this.bipedLeftArm.copyModelAngles(model.bipedLeftArm);
		this.bipedRightLeg.copyModelAngles(model.bipedRightLeg);
		this.bipedLeftLeg.copyModelAngles(model.bipedLeftLeg);
	}

	@Override
	public void setModelSlotVisible(T entity, EquipmentSlotType slotType) {
		setVisible(false);
		switch(slotType) {
		case HEAD:
			bipedHead.showModel = true;
			bipedHeadwear.showModel = true;
			break;
		case CHEST:
			bipedBody.showModel = true;
			bipedRightArm.showModel = true;
			bipedLeftArm.showModel = true;
			break;
		case LEGS:
			bipedBody.showModel = true;
			bipedRightLeg.showModel = true;
			bipedLeftLeg.showModel = true;
			break;
		case FEET:
			bipedRightLeg.showModel = true;
			bipedLeftLeg.showModel = true;
			break;
		default:
			break;
		}
	}

	@Override
	public void setRotationAngles(T entity, float limbSwing, float limbSwingAmount, float ageTicks, float netHeadYaw, float headPitch) {}
	
	@Override
	public void accept(ModelRenderer renderer) {
		super.accept(renderer);
		if(this.modelRenderers == null) {
			this.modelRenderers = Lists.newArrayList();
		}

		this.modelRenderers.add(renderer);
	}

	@Override
	public ModelRenderer getRandomModelRenderer(Random random) {
		return this.modelRenderers.get(random.nextInt(this.modelRenderers.size()));
	}

	@Override
	public ModelRenderer getBody() {
		return bipedBody;
	}
}
