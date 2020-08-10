package io.github.championash5357.entityarmormodels.client.renderer.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;

import io.github.championash5357.entityarmormodels.client.renderer.entity.layers.LivingEntityHeadLayer;
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
	
	/**
	 * Defines the scale of the model in the x-direction.
	 * 
	 * @return A {@link Float}
	 * @deprecated Will be removed in 1.0 Release. Should 
	 * be passed in through head layer constructor.
	 * */
	@Deprecated
	default float getXScale() { return 1.0f; }
	
	/**
	 * Defines the scale of the model in the y-direction.
	 * 
	 * @return A {@link Float}
	 * @deprecated Will be removed in 1.0 Release. Should 
	 * be passed in through head layer constructor.
	 * */
	@Deprecated
	default float getYScale() { return 1.0f; }
	
	/**
	 * Defines the scale of the model in the z-direction.
	 * 
	 * @return A {@link Float}
	 * @deprecated Will be removed in 1.0 Release. Should 
	 * be passed in through head layer constructor.
	 * */
	@Deprecated
	default float getZScale() { return 1.0f; }
}
