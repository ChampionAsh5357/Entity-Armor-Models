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

package io.github.championash5357.entityarmormodels.api.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.IElytraEditable;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.ElytraModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/**
 * Applied to a {@link LivingRenderer} to allow an 
 * elytra to display on your entity. Your entity must extend 
 * {@link LivingEntity} and have {@link IElytraEditable} 
 * implemented on the model.
 * */
public class LivingEntityElytraLayer<T extends LivingEntity, M extends EntityModel<T> & IElytraEditable> extends LayerRenderer<T, M> {

	private static final ResourceLocation TEXTURE_ELYTRA = new ResourceLocation("textures/entity/elytra.png");
	private final ElytraModel<T> modelElytra = new ElytraModel<>();
	
	/**
	 * A constructor to apply the layer.
	 * 
	 * @param entityRenderer
	 * 			The associated entity renderer.
	 * */
	public LivingEntityElytraLayer(IEntityRenderer<T, M> entityRenderer) {
		super(entityRenderer);
	}

	@Override
	public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, T entitylivingbase, float limbSwing, float limbSwingAmount, float partialTicks, float ageTicks, float netHeadYaw, float headPitch) {
		ItemStack stack = entitylivingbase.getItemStackFromSlot(EquipmentSlotType.CHEST);
		if(stack.getItem() instanceof ElytraItem) {
			matrixStack.push();
			this.getEntityModel().translateElytra(matrixStack, entitylivingbase.isChild());
			this.getEntityModel().copyModelAttributesTo(this.modelElytra);
			this.modelElytra.setRotationAngles(entitylivingbase, limbSwing, limbSwingAmount, ageTicks, netHeadYaw, headPitch);
			IVertexBuilder ivertexbuilder = ItemRenderer.getArmorVertexBuilder(buffer, this.modelElytra.getRenderType(TEXTURE_ELYTRA), false, stack.hasEffect());
			this.modelElytra.render(matrixStack, ivertexbuilder, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
			matrixStack.pop();
		}
	}
}
