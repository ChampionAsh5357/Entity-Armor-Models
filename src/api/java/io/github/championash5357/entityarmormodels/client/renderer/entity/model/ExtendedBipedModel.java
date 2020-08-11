package io.github.championash5357.entityarmormodels.client.renderer.entity.model;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import io.github.championash5357.entityarmormodels.client.renderer.entity.model.vanilla.IVanillaEntityModel;
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
 * relating to Husk, Zombie, Piglin, and Zombified 
 * Piglin.
 * */
public class ExtendedBipedModel<T extends LivingEntity, M extends BipedModel<T>> extends BipedModel<T> implements IVanillaEntityModel<T, M> {
	private List<ModelRenderer> modelRenderers;

	public ExtendedBipedModel(float modelSize) {
		super(modelSize);
	}

	public ExtendedBipedModel(float modelSize, float yOffset, int textureWidthIn, int textureHeightIn) {
		super(RenderType::getEntityCutoutNoCull, modelSize, yOffset, textureWidthIn, textureHeightIn);
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
	public void setModelSlotVisible(T entityIn, EquipmentSlotType slotType) {
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
	public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}
	
	@Override
	public void accept(ModelRenderer renderer) {
		super.accept(renderer);
		if(this.modelRenderers == null) {
			this.modelRenderers = Lists.newArrayList();
		}

		this.modelRenderers.add(renderer);
	}

	@Override
	public ModelRenderer getRandomModelRenderer(Random randomIn) {
		return this.modelRenderers.get(randomIn.nextInt(this.modelRenderers.size()));
	}
}
