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

import java.util.Random;

import io.github.championash5357.entityarmormodels.api.client.renderer.entity.layers.LivingEntityArmorLayer;
import io.github.championash5357.entityarmormodels.api.client.renderer.entity.layers.LivingEntityBeeStingerLayer;
import net.minecraft.client.renderer.model.ModelRenderer;

/**
 * Added to a model class to allow a random {@link ModelRenderer}
 * to return when called. Used in {@link LivingEntityArmorLayer} and
 * {@link LivingEntityBeeStingerLayer} to attach their respective values 
 * when hit.
 * */
public interface IRandomModelRenderer {

	/**
	 * Gets a random {@link ModelRenderer} from the model.
	 * 
	 * @param random
	 * 			An instance of random.
	 * @return A {@link ModelRenderer} in the model.
	 * */
	ModelRenderer getRandomModelRenderer(Random random);
}
