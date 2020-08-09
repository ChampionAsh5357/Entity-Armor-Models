package io.github.championash5357.entityarmormodels.client.renderer.entity.model;

import io.github.championash5357.entityarmormodels.client.renderer.entity.layers.LivingEntityArmorLayer;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.inventory.EquipmentSlotType;

/**
 * Added to a model class to determine whether a given 
 * {@link ModelRenderer} should be visible. Used in 
 * {@link LivingEntityArmorLayer} to enable and disable 
 * armor parts.
 * */
public interface IModelSlotVisible {

	/**
	 * Sets the model visibility for a certain slot.
	 * 
	 * @param slotType
	 * 			The current armor slot.
	 * */
	void setModelSlotVisible(EquipmentSlotType slotType);
}
