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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import com.google.common.collect.Lists;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public abstract class ExtendedAgeableModel<T extends LivingEntity, M extends AgeableModel<T>> extends AgeableModel<T> implements IVanillaEntityModel<T, M>{

	private List<ModelRenderer> modelRenderers = Lists.newArrayList();
	private static final Method HEAD_PARTS = ObfuscationReflectionHelper.findMethod(AgeableModel.class, "func_225602_a_"), 
			BODY_PARTS = ObfuscationReflectionHelper.findMethod(AgeableModel.class, "func_225600_b_");
	
	protected ExtendedAgeableModel() {
		this(false, 5.0f, 2.0f);
	}
	
	protected ExtendedAgeableModel(boolean isChildHeadScaled, float childHeadOffsetY, float childHeadOffsetZ) {
		this(isChildHeadScaled, childHeadOffsetY, childHeadOffsetZ, 2.0F, 2.0F, 24.0F);
	}

	protected ExtendedAgeableModel(boolean isChildHeadScaled, float childHeadOffsetY, float childHeadOffsetZ, float childHeadScale, float childBodyScale, float childBodyOffsetY) {
		this(RenderType::getEntityCutoutNoCull, isChildHeadScaled, childHeadOffsetY, childHeadOffsetZ, childHeadScale, childBodyScale, childBodyOffsetY);
	}

	protected ExtendedAgeableModel(Function<ResourceLocation, RenderType> func, boolean isChildHeadScaled, float childHeadOffsetY, float childHeadOffsetZ, float childHeadScale, float childBodyScale, float childBodyOffsetY) {
		super(func, isChildHeadScaled, childHeadOffsetY, childHeadOffsetZ, childHeadScale, childBodyScale, childBodyOffsetY);
	}
	
	@Override
	public void copyAttributesOfModel(M model) {
		model.copyModelAttributesTo(this);
		try {
			Object resultHead = HEAD_PARTS.invoke(model), resultBody = BODY_PARTS.invoke(model);
			if(resultHead instanceof Iterable<?> && resultBody instanceof Iterable<?>) {
				Iterator<ModelRenderer> it = this.getHeadParts().iterator();
				for(@SuppressWarnings("unchecked")
				Iterator<ModelRenderer> itm = ((Iterable<ModelRenderer>) resultHead).iterator(); itm.hasNext();) {
					if(it.hasNext()) {
						it.next().copyModelAngles(itm.next());
					} else break;
				}
				
				it = this.getBodyParts().iterator();
				for(@SuppressWarnings("unchecked")
				Iterator<ModelRenderer> itm = ((Iterable<ModelRenderer>) resultBody).iterator(); itm.hasNext();) {
					if(it.hasNext()) {
						it.next().copyModelAngles(itm.next());
					} else break;
				}
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void setRotationAngles(T entity, float limbSwing, float limbSwingAmount, float ageTicks, float netHeadYaw, float headPitch) {}
	
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
