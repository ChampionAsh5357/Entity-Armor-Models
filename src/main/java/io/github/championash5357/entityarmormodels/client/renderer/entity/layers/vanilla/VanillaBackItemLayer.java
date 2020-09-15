package io.github.championash5357.entityarmormodels.client.renderer.entity.layers.vanilla;

import com.mojang.blaze3d.matrix.MatrixStack;

import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.IHasBackItem;
import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.vanilla.IVanillaModelAttributes;
import net.minecraft.block.AbstractSkullBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;

public class VanillaBackItemLayer<T extends LivingEntity, M extends EntityModel<T>, A extends EntityModel<T> & IHasBackItem & IVanillaModelAttributes<T, M>> extends LayerRenderer<T, M> {

	private final A entityModel;

	public VanillaBackItemLayer(IEntityRenderer<T, M> entityRenderer, A entityModel) {
		super(entityRenderer);
		this.entityModel = entityModel;
	}

	@Override
	public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, T entitylivingbase, float limbSwing, float limbSwingAmount, float partialTicks, float ageTicks, float netHeadYaw, float headPitch) {
		entitylivingbase.getCapability(io.github.championash5357.backslot.api.common.capability.CapabilityInstances.BACK_SLOT_CAPABILITY).ifPresent(backSlot -> {
			ItemStack stack = backSlot.getBackStack();
			if(!stack.isEmpty()) {
				Item item = stack.getItem();
				io.github.championash5357.backslot.api.client.renderer.IBackRender render = io.github.championash5357.backslot.api.client.ClientManagers.getTransformationManager().getRender(item);
				boolean isChild = entitylivingbase.isChild();
				matrixStack.push();
				if(isChild) {
					matrixStack.translate(0.0D, 0.75D, 0.0D);
					matrixStack.scale(0.5F, 0.5F, 0.5F);
				}

				matrixStack.push();
				entityModel.copyAttributesOfModel(getEntityModel());
				entityModel.getBody().translateRotate(matrixStack);
				ItemStack chest = entitylivingbase.getItemStackFromSlot(EquipmentSlotType.CHEST);
				boolean armor = !chest.isEmpty() && chest.getItem() instanceof ArmorItem;
				if(armor) matrixStack.translate(0.0, 0.0, 0.0625);
				entityModel.offset(entitylivingbase, armor, matrixStack, isChild);
				render.offset(matrixStack);
				if(item instanceof BlockItem) {
					matrixStack.rotate(Vector3f.XP.rotationDegrees(180.0F));
					matrixStack.rotate(Vector3f.YP.rotationDegrees(180.0F));
					if(((BlockItem)item).getBlock() instanceof AbstractSkullBlock) {
						matrixStack.translate(0.0d, 0.0d, 0.375d);
					} else {
						matrixStack.scale(0.5f, 0.5f, 0.5f);
						matrixStack.translate(0.0d, -0.5d, 0.75d);
					}
				} else {
					matrixStack.translate(0.0d, 0.25d, 0.15625d);
				}
				Minecraft.getInstance().getFirstPersonRenderer().renderItemSide(entitylivingbase, stack, TransformType.NONE, false, matrixStack, buffer, packedLight);
				matrixStack.pop();
				matrixStack.pop();
			}
		});
	}
}
