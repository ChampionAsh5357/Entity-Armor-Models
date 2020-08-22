package io.github.championash5357.entityarmormodels.client.renderer.entity.layers.vanilla;

import java.util.function.BiConsumer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.ElytraModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class VanillaElytraLayer<T extends LivingEntity, M extends EntityModel<T>> extends LayerRenderer<T, M> {

	private static final ResourceLocation TEXTURE_ELYTRA = new ResourceLocation("textures/entity/elytra.png");
	private final ElytraModel<T> modelElytra = new ElytraModel<>();
	private final BiConsumer<MatrixStack, Boolean> offset;
	
	public VanillaElytraLayer(IEntityRenderer<T, M> entityRendererIn, BiConsumer<MatrixStack, Boolean> offsetIn) {
		super(entityRendererIn);
		this.offset = offsetIn;
	}

	@Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		ItemStack stack = entitylivingbaseIn.getItemStackFromSlot(EquipmentSlotType.CHEST);
		if(stack.getItem() instanceof ElytraItem) {
			matrixStackIn.push();
			if (entitylivingbaseIn.isChild()) {
				matrixStackIn.translate(0.0D, 0.03125D, 0.0D);
				matrixStackIn.scale(0.7F, 0.7F, 0.7F);
			}
			offset.accept(matrixStackIn, entitylivingbaseIn.isChild());
			this.getEntityModel().copyModelAttributesTo(this.modelElytra);
			this.modelElytra.setRotationAngles(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			IVertexBuilder ivertexbuilder = ItemRenderer.getArmorVertexBuilder(bufferIn, this.modelElytra.getRenderType(TEXTURE_ELYTRA), false, stack.hasEffect());
			this.modelElytra.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
			matrixStackIn.pop();
		}
	}
}
