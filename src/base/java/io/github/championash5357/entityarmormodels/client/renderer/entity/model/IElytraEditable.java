package io.github.championash5357.entityarmormodels.client.renderer.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;

import io.github.championash5357.entityarmormodels.client.renderer.entity.layers.LivingEntityElytraLayer;

/**
 * Added to a model class to allow an elytra to be translated 
 * and scaled on an entity. Used in {@link LivingEntityElytraLayer}.
 * */
public interface IElytraEditable {

	/**
	 * Called before the elytra is rendered onto the screen.
	 * 
	 * @param stack
	 * 			The {@link MatrixStack} instance.
	 * @param isChild
	 * 			If the entity is a child.
	 * */
	default void translateElytra(MatrixStack stack, boolean isChild) {};	
}
