package io.github.championash5357.entityarmormodels.client.renderer.entity.layers;

import java.util.Random;

import com.mojang.blaze3d.matrix.MatrixStack;

import io.github.championash5357.entityarmormodels.client.renderer.entity.model.IRandomModelRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

/**
 * A class used to apply objects randomly to a model 
 * based on a current entity attribute. The entity must extend 
 * {@link LivingEntity} and have {@link IRandomModelRenderer} 
 * implemented on the model.
 * */
public abstract class LivingEntityStuckInBodyLayer<T extends LivingEntity, M extends EntityModel<T> & IRandomModelRenderer> extends LayerRenderer<T, M> {

	/**
	 * A constructor to apply the layer.
	 * 
	 * @param entityRendererIn
	 * 			The associated entity renderer.
	 * */
	public LivingEntityStuckInBodyLayer(LivingRenderer<T, M> entityRendererIn) {
		super(entityRendererIn);
	}

	/**
	 * Gets the number of objects to be displayed on the entity.
	 * 
	 * @param entity
	 * 			The current entity;
	 * @return A numerical value of a specific attribute.
	 * */
	protected abstract int getStuckEntityCount(T entity);
	
	/**
	 * Renders the object within the entity.
	 * 
	 * @param matrixStack
	 * 			The associated {@link MatrixStack} of the renderer.
	 * @param buffer
	 * 			The associated {@link IRenderTypeBuffer} of the renderer.
	 * @param packedLight
	 * 			The light value of the current location.
	 * @param entity
	 * 			The associated entity.
	 * @param randomX
	 * 			A random x position on a random {@link ModelRenderer}.
	 * @param randomY
	 * 			A random y position on a random {@link ModelRenderer}.
	 * @param randomZ
	 * 			A random z position on a random {@link ModelRenderer}.
	 * @param partialTicks
	 * 			The percentage of a tick that has passed.
	 * */
	protected abstract void renderStuckEntity(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, T entity, float randomX, float randomY, float randomZ, float partialTicks);
	
	@Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		int count = this.getStuckEntityCount(entitylivingbaseIn);
		Random random = new Random((long)entitylivingbaseIn.getEntityId());
		if(count > 0) {
			for(int i = 0; i < count; ++i) {
				matrixStackIn.push();
	            ModelRenderer modelrenderer = this.getEntityModel().getRandomModelRenderer(random);
	            ModelRenderer.ModelBox modelrenderer$modelbox = modelrenderer.getRandomCube(random);
	            modelrenderer.translateRotate(matrixStackIn);
	            float f = random.nextFloat();
	            float f1 = random.nextFloat();
	            float f2 = random.nextFloat();
	            float f3 = MathHelper.lerp(f, modelrenderer$modelbox.posX1, modelrenderer$modelbox.posX2) / 16.0F;
	            float f4 = MathHelper.lerp(f1, modelrenderer$modelbox.posY1, modelrenderer$modelbox.posY2) / 16.0F;
	            float f5 = MathHelper.lerp(f2, modelrenderer$modelbox.posZ1, modelrenderer$modelbox.posZ2) / 16.0F;
	            matrixStackIn.translate((double)f3, (double)f4, (double)f5);
	            f = -1.0F * (f * 2.0F - 1.0F);
	            f1 = -1.0F * (f1 * 2.0F - 1.0F);
	            f2 = -1.0F * (f2 * 2.0F - 1.0F);
	            this.renderStuckEntity(matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, f, f1, f2, partialTicks);
	            matrixStackIn.pop();
			}
		}
	}
}
