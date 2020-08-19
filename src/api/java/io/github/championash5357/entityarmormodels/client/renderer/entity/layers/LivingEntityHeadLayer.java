package io.github.championash5357.entityarmormodels.client.renderer.entity.layers;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.matrix.MatrixStack;

import io.github.championash5357.entityarmormodels.client.renderer.entity.model.IHasHeadEditable;
import io.github.championash5357.entityarmormodels.client.util.ItemRendererExtension;
import net.minecraft.block.AbstractSkullBlock;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
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

/**
 * Applied to a {@link LivingRenderer} to allow a 
 * head to display on your entity. Your entity must extend 
 * {@link LivingEntity} and have {@link IHasHeadEditable} 
 * implemented on the model.
 * */
public class LivingEntityHeadLayer<T extends LivingEntity, M extends EntityModel<T> & IHasHeadEditable> extends LayerRenderer<T, M> {

	private final float xScale, yScale, zScale;
	
	/**
	 * A constructor to apply the head layer with the default size of 1.0f.
	 * 
	 * @param entityRendererIn
	 * 			The associated entity renderer.
	 * */
	public LivingEntityHeadLayer(IEntityRenderer<T, M> entityRendererIn) {
		this(entityRendererIn, 1.0f, 1.0f, 1.0f);
	}
	
	/**
	 * A constructor to apply the head layer.
	 * 
	 * @param entityRendererIn
	 * 			The associated entity renderer.
	 * @param xScaleIn
	 * 			The x scale factor of the layer.
	 * @param yScaleIn
	 * 			The y scale factor of the layer.
	 * @param zScaleIn
	 * 			The z scale factor of the layer.
	 * */
	public LivingEntityHeadLayer(IEntityRenderer<T, M> entityRendererIn, float xScaleIn, float yScaleIn, float zScaleIn) {
		super(entityRendererIn);
		this.xScale = xScaleIn;
		this.yScale = yScaleIn;
		this.zScale = zScaleIn;
	}

	@Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn,float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		ItemStack stack = entitylivingbaseIn.getItemStackFromSlot(EquipmentSlotType.HEAD);
		if(!stack.isEmpty()) {
			Item item = stack.getItem();
			if(!(item instanceof ArmorItem) || ((ArmorItem)item).getEquipmentSlot() != EquipmentSlotType.HEAD) {
				matrixStackIn.push();
				matrixStackIn.scale(this.xScale, this.yScale, this.zScale);
				boolean child = entitylivingbaseIn.isChild();
				this.getEntityModel().preRender(matrixStackIn, child);
				if (child && !(entitylivingbaseIn instanceof VillagerEntity)) {
					matrixStackIn.translate(0.0D, 0.03125D, 0.0D);
					matrixStackIn.scale(0.7F, 0.7F, 0.7F);
					matrixStackIn.translate(0.0D, 1.0D, 0.0D);
				}

				this.getEntityModel().getModelHead().translateRotate(matrixStackIn);
				if (item instanceof BlockItem && ((BlockItem)item).getBlock() instanceof AbstractSkullBlock) {
					matrixStackIn.scale(1.1875F, -1.1875F, -1.1875F);
					this.getEntityModel().skullRender(matrixStackIn, child);

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

					matrixStackIn.translate(-0.5D, 0.0D, -0.5D);
					SkullTileEntityRenderer.render((Direction)null, 180.0F, ((AbstractSkullBlock)((BlockItem)item).getBlock()).getSkullType(), gameprofile, limbSwing, matrixStackIn, bufferIn, packedLightIn);
				} else  {
					matrixStackIn.translate(0.0D, -0.25D, 0.0D);
					matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180.0F));
					matrixStackIn.scale(0.625F, -0.625F, -0.625F);
					this.getEntityModel().itemRender(matrixStackIn, child);

					ItemRendererExtension.renderItemNoCull(entitylivingbaseIn, stack, ItemCameraTransforms.TransformType.HEAD, false, matrixStackIn, bufferIn, packedLightIn);
				}
				matrixStackIn.pop();
			}
		}
	}
}
