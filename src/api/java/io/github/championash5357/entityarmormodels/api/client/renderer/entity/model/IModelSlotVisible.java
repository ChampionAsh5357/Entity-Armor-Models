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

package io.github.championash5357.entityarmormodels.api.client.renderer.entity.model;

import io.github.championash5357.entityarmormodels.api.client.renderer.entity.layers.LivingEntityArmorLayer;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;

/**
 * Added to a model class to determine whether a given 
 * {@link ModelRenderer} should be visible. Used in 
 * {@link LivingEntityArmorLayer} to enable and disable 
 * armor parts.
 * */
public interface IModelSlotVisible<T extends LivingEntity> {

	/**
	 * Sets the model visibility for a certain slot.
	 * 
	 * @param entity
	 * 			The current entity to apply the model visibility for.
	 * @param slotType
	 * 			The current armor slot.
	 * */
	void setModelSlotVisible(T entity, EquipmentSlotType slotType);
}
