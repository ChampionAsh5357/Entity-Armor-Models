package io.github.championash5357.entityarmormodels.api.client.renderer.entity.model;

import io.github.championash5357.entityarmormodels.api.client.renderer.entity.layers.LivingEntityArmorLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

/**
 * Added to a model class to match the model overlay with 
 * with the model beneath. Used in {@link LivingEntityArmorLayer} 
 * to sync armor movements.
 * <br>
 * The generic 'A' should be the model you are implementing this 
 * interface on. A {@link ModelRenderer} on this should either be 
 * public or have an accessible list to synchronize the rotations.
 * */
public interface IModelAttributes<T extends LivingEntity, A extends EntityModel<T>> {

	/**
	 * Sets the attributes of the model being passed 
	 * in to this model. See {@link BipedModel#setModelAttributes(BipedModel)} 
	 * for implementation.
	 * 
	 * @param model
	 * 			The current entity model being set.
	 * */
	void setModelAttributes(A model);
}
