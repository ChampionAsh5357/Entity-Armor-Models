package io.github.championash5357.entityarmormodels.client.renderer.entity.layers.vanilla;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.HorseModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.DyeableHorseArmorItem;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.ItemStack;

public class VanillaHorseArmorLayer<T extends AbstractHorseEntity> extends LayerRenderer<T, HorseModel<T>> {

	private final HorseModel<T> horseModel = new HorseModel<T>(0.1f);
	
	public VanillaHorseArmorLayer(IEntityRenderer<T, HorseModel<T>> livingrenderer) {
		super(livingrenderer);
	}

	@Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		ItemStack stack = entitylivingbaseIn.getItemStackFromSlot(EquipmentSlotType.CHEST);
		if(stack.getItem() instanceof HorseArmorItem) {
			HorseArmorItem armor = (HorseArmorItem) stack.getItem();
			this.getEntityModel().copyModelAttributesTo(horseModel);
			this.horseModel.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
			this.horseModel.setRotationAngles(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			float r, g, b;
			if(armor instanceof DyeableHorseArmorItem) {
				int color = ((DyeableHorseArmorItem) armor).getColor(stack);
				r = (float)(color >> 16 & 255) / 255.0f;
				g = (float)(color >> 8 & 255) / 255.0f;
				b = (float)(color & 255) / 255.0f;
			} else {
				r = g = b = 1.0f;
			}
			
			IVertexBuilder builder = bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(armor.getArmorTexture()));
			this.horseModel.render(matrixStackIn, builder, packedLightIn, OverlayTexture.NO_OVERLAY, r, g, b, 1.0f);
		}
	}
}
