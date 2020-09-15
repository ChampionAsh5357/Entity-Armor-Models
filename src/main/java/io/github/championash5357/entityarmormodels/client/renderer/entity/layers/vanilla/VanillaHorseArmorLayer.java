package io.github.championash5357.entityarmormodels.client.renderer.entity.layers.vanilla;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.vanilla.IVanillaArmorTextureAppension;
import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.vanilla.IVanillaModelAttributes;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.DyeableHorseArmorItem;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.ItemStack;

public class VanillaHorseArmorLayer<T extends LivingEntity, M extends EntityModel<T>, A extends EntityModel<T> & IVanillaModelAttributes<T, M> & IVanillaArmorTextureAppension> extends LayerRenderer<T, M> {

	private final A horseModel;
	
	public VanillaHorseArmorLayer(IEntityRenderer<T, M> livingrenderer, A horseModel) {
		super(livingrenderer);
		this.horseModel = horseModel;
	}

	@Override
	public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, T entitylivingbase, float limbSwing, float limbSwingAmount, float partialTicks, float ageTicks, float netHeadYaw, float headPitch) {
		ItemStack stack = entitylivingbase.getItemStackFromSlot(EquipmentSlotType.CHEST);
		if(stack.getItem() instanceof HorseArmorItem) {
			HorseArmorItem armor = (HorseArmorItem) stack.getItem();
			this.horseModel.copyAttributesOfModel(getEntityModel());
			this.horseModel.setLivingAnimations(entitylivingbase, limbSwing, limbSwingAmount, partialTicks);
			this.horseModel.setRotationAngles(entitylivingbase, limbSwing, limbSwingAmount, ageTicks, netHeadYaw, headPitch);
			float r, g, b;
			if(armor instanceof DyeableHorseArmorItem) {
				int color = ((DyeableHorseArmorItem) armor).getColor(stack);
				r = (float)(color >> 16 & 255) / 255.0f;
				g = (float)(color >> 8 & 255) / 255.0f;
				b = (float)(color & 255) / 255.0f;
			} else {
				r = g = b = 1.0f;
			}
			
			IVertexBuilder builder = buffer.getBuffer(RenderType.getEntityCutoutNoCull(horseModel.getHorseArmorLocation(entitylivingbase.getType(), armor)));
			this.horseModel.render(matrixStack, builder, packedLight, OverlayTexture.NO_OVERLAY, r, g, b, 1.0f);
		}
	}
}
