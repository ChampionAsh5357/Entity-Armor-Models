package io.github.championash5357.entityarmormodels.client.renderer.entity.layers.vanilla;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.matrix.MatrixStack;

import io.github.championash5357.entityarmormodels.client.renderer.entity.model.IHasHeadEditable;
import io.github.championash5357.entityarmormodels.client.renderer.entity.model.vanilla.IVanillaModelAttributes;
import io.github.championash5357.entityarmormodels.client.util.ItemRendererExtension;
import net.minecraft.block.AbstractSkullBlock;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.SkullTileEntityRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.SkullTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3f;

public class VanillaHeadLayer<T extends LivingEntity, M extends EntityModel<T>, A extends EntityModel<T> & IVanillaModelAttributes<T, M> & IHasHeadEditable> extends LayerRenderer<T, M> {

	private final A entityModel;
	private final float xScale, yScale, zScale;
	
	public VanillaHeadLayer(IEntityRenderer<T, M> entityRendererIn, A entityModelIn, float xScaleIn, float yScaleIn, float zScaleIn) {
		super(entityRendererIn);
		this.entityModel = entityModelIn;
		this.xScale = xScaleIn;
		this.yScale = yScaleIn;
		this.zScale = zScaleIn;
	}

	@Override
	public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		ItemStack stack = entitylivingbaseIn.getItemStackFromSlot(EquipmentSlotType.HEAD);
		if(!stack.isEmpty()) {
			Item item = stack.getItem();
			if(!(item instanceof ArmorItem) || ((ArmorItem)item).getEquipmentSlot() != EquipmentSlotType.HEAD) {
				matrixStack.push();
				matrixStack.scale(this.xScale, this.yScale, this.zScale);
				boolean child = entitylivingbaseIn.isChild();
				entityModel.preRender(matrixStack, child);
				if (child && !(entitylivingbaseIn instanceof VillagerEntity)) {
					matrixStack.translate(0.0D, 0.03125D, 0.0D);
					matrixStack.scale(0.7F, 0.7F, 0.7F);
					matrixStack.translate(0.0D, 1.0D, 0.0D);
				}

				entityModel.copyAttributesOfModel(getEntityModel());
				entityModel.getModelHead().translateRotate(matrixStack);
				if (item instanceof BlockItem && ((BlockItem)item).getBlock() instanceof AbstractSkullBlock) {
					matrixStack.scale(1.1875F, -1.1875F, -1.1875F);
					entityModel.skullRender(matrixStack, child);

					GameProfile gameprofile = null;
					if (stack.hasTag()) {
						CompoundNBT compoundnbt = stack.getTag();
						if (compoundnbt.contains("SkullOwner", 10)) {
							gameprofile = NBTUtil.readGameProfile(compoundnbt.getCompound("SkullOwner"));
						} else if (compoundnbt.contains("SkullOwner", 8)) {
							String s = compoundnbt.getString("SkullOwner");
							if (!StringUtils.isBlank(s)) {
								gameprofile = SkullTileEntity.updateGameProfile(new GameProfile((UUID)null, s));
								compoundnbt.put("SkullOwner", NBTUtil.writeGameProfile(new CompoundNBT(), gameprofile));
							}
						}
					}

					matrixStack.translate(-0.5D, 0.0D, -0.5D);
					SkullTileEntityRenderer.render((Direction)null, 180.0F, ((AbstractSkullBlock)((BlockItem)item).getBlock()).getSkullType(), gameprofile, limbSwing, matrixStack, buffer, packedLightIn);
				} else  {
					matrixStack.translate(0.0D, -0.25D, 0.0D);
					matrixStack.rotate(Vector3f.YP.rotationDegrees(180.0F));
					matrixStack.scale(0.625F, -0.625F, -0.625F);
					entityModel.itemRender(matrixStack, child);

					ItemRendererExtension.renderItemNoCull(entitylivingbaseIn, stack, ItemCameraTransforms.TransformType.HEAD, false, matrixStack, buffer, packedLightIn);
				}
				matrixStack.pop();
			}
		}
	}


}
