package io.github.championash5357.entityarmormodels.api.client.renderer.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

/**
 * Added to a model class to allow model to render back items. 
 * This is only used if the Back Slot mod is present. 
 * */
public interface IHasBackItem {

	/**
	 * Gets the body of the model to attach to.
	 * 
	 * @return The {@link ModelRenderer} body.
	 * */
	ModelRenderer getBody();
	
	/**
	 * Occurs after child and body offsets are set.
	 * 
	 * @param entity
	 * 			The associated entity.
	 * @param wearingArmor
	 * 			If the entity is wearing armor.
	 * @param stack
	 * 			The {@link MatrixStack} being modified
	 * @param isChild
	 * 			If the entity is a child or not.
	 * */
	default void offset(LivingEntity entity, boolean wearingArmor, MatrixStack stack, boolean isChild) {}
}
