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

import com.mojang.blaze3d.matrix.MatrixStack;

import io.github.championash5357.entityarmormodels.api.client.renderer.entity.layers.LivingEntityElytraLayer;

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
