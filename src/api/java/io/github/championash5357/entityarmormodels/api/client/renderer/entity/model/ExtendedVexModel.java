package io.github.championash5357.entityarmormodels.api.client.renderer.entity.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.vanilla.ExtendedBipedModel;
import net.minecraft.client.renderer.entity.model.VexModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.monster.VexEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

/**
 * An extended version of {@link VexModel}. 
 * Extend this model to apply custom entity armors 
 * to a Vex.
 * */
public class ExtendedVexModel extends ExtendedBipedModel<VexEntity, VexModel> {

	protected final ModelRenderer leftWing;
	protected final ModelRenderer rightWing;
	private static final Method GET_BODY_PARTS = ObfuscationReflectionHelper.findMethod(VexModel.class, "func_225600_b_");

	public ExtendedVexModel(float modelSize) {
		this(modelSize, 64, 64);
	}

	public ExtendedVexModel(float modelSize, int textureWidth, int textureHeight) {
		super(modelSize, 0.0f, textureWidth, textureHeight);
		this.bipedLeftLeg.showModel = false;
		this.bipedHeadwear.showModel = false;
		this.bipedRightLeg = new ModelRenderer(this, 32, 0);
		this.bipedRightLeg.addBox(-1.0F, -1.0F, -2.0F, 6.0F, 10.0F, 4.0F, modelSize);
		this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
		this.rightWing = new ModelRenderer(this, 0, 32);
		this.rightWing.addBox(-20.0F, 0.0F, 0.0F, 20.0F, 12.0F, 1.0F, modelSize);
		this.leftWing = new ModelRenderer(this, 0, 32);
		this.leftWing.mirror = true;
		this.leftWing.addBox(0.0F, 0.0F, 0.0F, 20.0F, 12.0F, 1.0F, modelSize);
	}

	public Iterable<ModelRenderer> getBodyParts() {
		return Iterables.concat(super.getBodyParts(), ImmutableList.of(this.rightWing, this.leftWing));
	}

	@Override
	public void copyAttributesOfModel(VexModel model) {
		model.copyModelAttributesTo(this);
		this.leftArmPose = model.leftArmPose;
		this.rightArmPose = model.rightArmPose;
		this.isSneak = model.isSneak;
		this.bipedHead.copyModelAngles(model.bipedHead);
		this.bipedHeadwear.copyModelAngles(model.bipedHeadwear);
		Iterator<ModelRenderer> it = this.getBodyParts().iterator();
		try {
			Object result = GET_BODY_PARTS.invoke(model);
			if(result instanceof Iterable<?>) {
				for(@SuppressWarnings("unchecked")
				Iterator<ModelRenderer> itm = ((Iterable<ModelRenderer>) result).iterator(); itm.hasNext();) {
					if(it.hasNext()) {
						it.next().copyModelAngles(itm.next());
					} else break;
				}
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		this.leftWing.showModel = visible;
		this.rightWing.showModel = visible;
	}

	@Override
	public void setModelSlotVisible(VexEntity entity, EquipmentSlotType slotType) {
		setVisible(false);
		switch(slotType) {
		case HEAD:
			bipedHead.showModel = true;
			break;
		case CHEST:
			bipedBody.showModel = true;
			bipedRightArm.showModel = true;
			bipedLeftArm.showModel = true;
			leftWing.showModel = true;
			rightWing.showModel = true;
			break;
		case LEGS:
			bipedBody.showModel = true;
			bipedRightLeg.showModel = true;
			break;
		case FEET:
			bipedRightLeg.showModel = true;
			break;
		default:
			break;
		}
	}
}
