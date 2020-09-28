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

package io.github.championash5357.entityarmormodels.client.renderer.entity.layers.vanilla;

import com.mojang.blaze3d.matrix.MatrixStack;

import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.IRandomModelRenderer;
import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.vanilla.IVanillaModelAttributes;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.util.math.MathHelper;

public class VanillaArrowLayer<T extends LivingEntity, M extends EntityModel<T>, A extends EntityModel<T> & IVanillaModelAttributes<T, M> & IRandomModelRenderer> extends VanillaStuckInBodyLayer<T, M, A> {

	private final EntityRendererManager manager;
	private ArrowEntity allocatedArrow;

	public VanillaArrowLayer(LivingRenderer<T, M> entityRenderer, A entityModel) {
		super(entityRenderer, entityModel);
		this.manager = entityRenderer.getRenderManager();
	}

	@Override
	protected int getStuckEntityCount(T entity) {
		return entity.getArrowCountInEntity();
	}

	@Override
	protected void renderStuckEntity(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, T entity, float f, float f1, float f2, float partialTicks) {
		float rot = MathHelper.sqrt(f * f + f2 * f2);
		this.allocatedArrow = new ArrowEntity(entity.world, entity.getPosX(), entity.getPosY(), entity.getPosZ());
		this.allocatedArrow.rotationYaw = (float)(Math.atan2((double)f, (double)f2) * (double)(180F / (float)Math.PI));
		this.allocatedArrow.rotationPitch = (float)(Math.atan2((double)f1, (double)rot) * (double)(180F / (float)Math.PI));
		this.allocatedArrow.prevRotationYaw = this.allocatedArrow.rotationYaw;
		this.allocatedArrow.prevRotationPitch = this.allocatedArrow.rotationPitch;
		this.manager.renderEntityStatic(this.allocatedArrow, 0.0D, 0.0D, 0.0D, 0.0F, partialTicks, matrixStack, buffer, packedLight);
	}
}
