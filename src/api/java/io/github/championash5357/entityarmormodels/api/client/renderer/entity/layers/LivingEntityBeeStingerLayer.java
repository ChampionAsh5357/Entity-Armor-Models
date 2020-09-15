package io.github.championash5357.entityarmormodels.api.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.IRandomModelRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;

/**
 * Applied to a {@link LivingRenderer} to allow a 
 * hit bee sting to display on your entity. Your entity must extend 
 * {@link LivingEntity} and have {@link IRandomModelRenderer} 
 * implemented on the model.
 * */
public class LivingEntityBeeStingerLayer<T extends LivingEntity, M extends EntityModel<T> & IRandomModelRenderer> extends LivingEntityStuckInBodyLayer<T, M> {

	private static final ResourceLocation STINGER_TEXTURE = new ResourceLocation("textures/entity/bee/bee_stinger.png");

	/**
	 * A constructor to apply the layer.
	 * 
	 * @param entityRenderer
	 * 			The associated entity renderer.
	 * */
	public LivingEntityBeeStingerLayer(LivingRenderer<T, M> entityRenderer) {
		super(entityRenderer);
	}

	@Override
	protected int getStuckEntityCount(T entity) {
		return entity.getBeeStingCount();
	}

	@Override
	protected void renderStuckEntity(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, T entity, float x, float x1, float x2, float partialTicks) {
		float f = MathHelper.sqrt(x * x + x2 * x2);
		float f1 = (float)(Math.atan2((double)x, (double)x2) * (double)(180F / (float)Math.PI));
		float f2 = (float)(Math.atan2((double)x1, (double)f) * (double)(180F / (float)Math.PI));
		matrixStack.translate(0.0D, 0.0D, 0.0D);
		matrixStack.rotate(Vector3f.YP.rotationDegrees(f1 - 90.0F));
		matrixStack.rotate(Vector3f.ZP.rotationDegrees(f2));
		matrixStack.rotate(Vector3f.XP.rotationDegrees(45.0F));
		matrixStack.scale(0.03125F, 0.03125F, 0.03125F);
		matrixStack.translate(2.5D, 0.0D, 0.0D);
		IVertexBuilder ivertexbuilder = buffer.getBuffer(RenderType.getEntityCutoutNoCull(STINGER_TEXTURE));

		for(int i = 0; i < 4; ++i) {
			matrixStack.rotate(Vector3f.XP.rotationDegrees(90.0F));
			MatrixStack.Entry matrixstack$entry = matrixStack.getLast();
			Matrix4f matrix4f = matrixstack$entry.getMatrix();
			Matrix3f matrix3f = matrixstack$entry.getNormal();
			stingerSide(ivertexbuilder, matrix4f, matrix3f, -4.5F, -1, 0.0F, 0.0F, packedLight);
			stingerSide(ivertexbuilder, matrix4f, matrix3f, 4.5F, -1, 0.125F, 0.0F, packedLight);
			stingerSide(ivertexbuilder, matrix4f, matrix3f, 4.5F, 1, 0.125F, 0.0625F, packedLight);
			stingerSide(ivertexbuilder, matrix4f, matrix3f, -4.5F, 1, 0.0F, 0.0625F, packedLight);
		}
	}

	private static void stingerSide(IVertexBuilder builder, Matrix4f matrix4f, Matrix3f matrix3f, float x, int y, float xTexture, float yTexture, int packedLight) {
		builder.pos(matrix4f, x, (float)y, 0.0F).color(255, 255, 255, 255).tex(xTexture, yTexture).overlay(OverlayTexture.NO_OVERLAY).lightmap(packedLight).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
	}
}
