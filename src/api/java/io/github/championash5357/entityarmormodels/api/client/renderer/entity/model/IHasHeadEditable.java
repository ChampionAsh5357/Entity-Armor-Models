package io.github.championash5357.entityarmormodels.api.client.renderer.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;

import io.github.championash5357.entityarmormodels.api.client.renderer.entity.layers.LivingEntityHeadLayer;
import net.minecraft.client.renderer.entity.model.IHasHead;

/**
 * Added to a model class to allow model to render skulls and items on 
 * their head. Used in {@link LivingEntityHeadLayer}. 
 * */
public interface IHasHeadEditable extends IHasHead {

	/**
	 * Before any rendering manipulation is done on the 
	 * stack, this method is called.
	 * 
	 * @param stack
	 * 			The {@link MatrixStack} instance.
	 * @param isChild
	 * 			If the entity is a child.
	 * */
	default void preRender(MatrixStack stack, boolean isChild) {}
	
	/**
	 * Called before a skull is rendered on the entity's 
	 * head.
	 * 
	 * @param stack
	 * 			The {@link MatrixStack} instance.
	 * @param isChild
	 * 			If the entity is a child.
	 * */
	default void skullRender(MatrixStack stack, boolean isChild) {}
	
	/**
	 * Called before an item is rendered on the entity's 
	 * head.
	 * 
	 * @param stack
	 * 			The {@link MatrixStack} instance.
	 * @param isChild
	 * 			If the entity is a child.
	 * */
	default void itemRender(MatrixStack stack, boolean isChild) {}
}
