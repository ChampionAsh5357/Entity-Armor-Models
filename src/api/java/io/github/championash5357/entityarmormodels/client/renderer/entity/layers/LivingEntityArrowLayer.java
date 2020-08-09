package io.github.championash5357.entityarmormodels.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;

import io.github.championash5357.entityarmormodels.client.renderer.entity.model.IRandomModelRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.util.math.MathHelper;

public class LivingEntityArrowLayer<T extends LivingEntity, M extends EntityModel<T> & IRandomModelRenderer> extends LivingEntityStuckInBodyLayer<T, M> {

	private final EntityRendererManager manager;
	private ArrowEntity allocatedArrow;

	public LivingEntityArrowLayer(LivingRenderer<T, M> entityRendererIn) {
		super(entityRendererIn);
		this.manager = entityRendererIn.getRenderManager();
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