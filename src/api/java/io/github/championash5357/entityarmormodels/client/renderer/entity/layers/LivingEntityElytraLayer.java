package io.github.championash5357.entityarmormodels.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import io.github.championash5357.entityarmormodels.client.renderer.entity.model.IElytraEditable;
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
	 * @param entityRendererIn
	 * 			The associated entity renderer.
	 * */
	public LivingEntityElytraLayer(IEntityRenderer<T, M> entityRendererIn) {
		super(entityRendererIn);
	}

	@Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		ItemStack stack = entitylivingbaseIn.getItemStackFromSlot(EquipmentSlotType.CHEST);
		if(stack.getItem() instanceof ElytraItem) {
			matrixStackIn.push();
			this.getEntityModel().translateElytra(matrixStackIn, entitylivingbaseIn.isChild());
			this.getEntityModel().copyModelAttributesTo(this.modelElytra);
			this.modelElytra.setRotationAngles(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			IVertexBuilder ivertexbuilder = ItemRenderer.getArmorVertexBuilder(bufferIn, this.modelElytra.getRenderType(TEXTURE_ELYTRA), false, stack.hasEffect());
			this.modelElytra.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
			matrixStackIn.pop();
		}
	}
}
