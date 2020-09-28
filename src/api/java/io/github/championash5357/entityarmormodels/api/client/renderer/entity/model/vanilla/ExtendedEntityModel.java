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

package io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.vanilla;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public abstract class ExtendedEntityModel<T extends LivingEntity, M extends EntityModel<T>> extends EntityModel<T> implements IVanillaEntityModel<T, M> {
	
	private List<ModelRenderer> modelRenderers = Lists.newArrayList();
	
	@Override
	public void accept(ModelRenderer renderer) {
		super.accept(renderer);
		if(this.modelRenderers == null) {
			this.modelRenderers = Lists.newArrayList();
		}
		this.modelRenderers.add(renderer);
	}
	
	@Override
	public ModelRenderer getRandomModelRenderer(Random random) {
		return this.modelRenderers.get(random.nextInt(this.modelRenderers.size()));
	}
}
