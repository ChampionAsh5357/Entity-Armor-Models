package io.github.championash5357.entityarmormodels.client.renderer.entity.layers.vanilla;

import java.util.Random;

import com.mojang.blaze3d.matrix.MatrixStack;

import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.IRandomModelRenderer;
import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.vanilla.IVanillaModelAttributes;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public abstract class VanillaStuckInBodyLayer<T extends LivingEntity, M extends EntityModel<T>, A extends EntityModel<T> & IVanillaModelAttributes<T, M> & IRandomModelRenderer> extends LayerRenderer<T, M> {

	private final A entityModel;
	
	public VanillaStuckInBodyLayer(LivingRenderer<T, M> entityRenderer, A entityModel) {
		super(entityRenderer);
		this.entityModel = entityModel;
	}

	protected abstract int getStuckEntityCount(T entity);
	
	protected abstract void renderStuckEntity(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, T entity, float f, float f1, float f2, float partialTicks);
	
	@Override
	public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, T entitylivingbase, float limbSwing, float limbSwingAmount, float partialTicks, float ageTicks, float netHeadYaw, float headPitch) {
		int count = this.getStuckEntityCount(entitylivingbase);
		Random random = new Random((long)entitylivingbase.getEntityId());
		if(count > 0) {
			for(int i = 0; i < count; ++i) {
				matrixStack.push();
				entityModel.copyAttributesOfModel(getEntityModel());
	            ModelRenderer modelrenderer = entityModel.getRandomModelRenderer(random);
	            ModelRenderer.ModelBox modelrenderer$modelbox = modelrenderer.getRandomCube(random);
	            modelrenderer.translateRotate(matrixStack);
	            float f = random.nextFloat();
	            float f1 = random.nextFloat();
	            float f2 = random.nextFloat();
	            float f3 = MathHelper.lerp(f, modelrenderer$modelbox.posX1, modelrenderer$modelbox.posX2) / 16.0F;
	            float f4 = MathHelper.lerp(f1, modelrenderer$modelbox.posY1, modelrenderer$modelbox.posY2) / 16.0F;
	            float f5 = MathHelper.lerp(f2, modelrenderer$modelbox.posZ1, modelrenderer$modelbox.posZ2) / 16.0F;
	            matrixStack.translate((double)f3, (double)f4, (double)f5);
	            f = -1.0F * (f * 2.0F - 1.0F);
	            f1 = -1.0F * (f1 * 2.0F - 1.0F);
	            f2 = -1.0F * (f2 * 2.0F - 1.0F);
	            this.renderStuckEntity(matrixStack, buffer, packedLight, entitylivingbase, f, f1, f2, partialTicks);
	            matrixStack.pop();
			}
		}
	}
}
