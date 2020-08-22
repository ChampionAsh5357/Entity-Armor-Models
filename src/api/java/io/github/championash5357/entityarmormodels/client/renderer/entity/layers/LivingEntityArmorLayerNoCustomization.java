package io.github.championash5357.entityarmormodels.client.renderer.entity.layers;

import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import io.github.championash5357.entityarmormodels.client.renderer.entity.model.IModelAttributes;
import io.github.championash5357.entityarmormodels.client.renderer.entity.model.IModelSlotVisible;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/**
 * Applied to a {@link LivingRenderer} to allow armor 
 * to display on your entity. Your entity must extend 
 * {@link LivingEntity} and have {@link IModelAttributes} 
 * and {@link IModelSlotVisible} implemented on the model.
 * <br>
 * The generic 'A' should either be the same or extends the 
 * generic 'M'.
 * <br>
 * This layer renderer specifically removes customization options 
 * for attaching custom models and texture locations. This should 
 * be used if you do not plan to have other mods attach custom models 
 * or texture locations to this mob.This will slightly improve performance 
 * for larger quantities of entities.
 * */
public class LivingEntityArmorLayerNoCustomization<T extends LivingEntity, M extends EntityModel<T> & IModelAttributes<T, A> & IModelSlotVisible<T>, A extends EntityModel<T>> extends LayerRenderer<T, M> {

	private static final Map<String, ResourceLocation> ARMOR_TEXTURE_RES_MAP = Maps.newHashMap();
	private final A modelArmorHalf, modelArmor;
	
	/**
	 * A constructor to apply the layer.
	 * 
	 * @param entityRendererIn
	 * 			The associated entity renderer.
	 * @param modelArmorHalf
	 * 			The armor model usually associated with the legs slot. Usually half of modelArmor.
	 * @param modelArmor
	 * 			The armor model for everything but the legs slot.
	 * */
	public LivingEntityArmorLayerNoCustomization(IEntityRenderer<T, M> entityRendererIn, A modelArmorHalf, A modelArmor) {
		super(entityRendererIn);
		this.modelArmorHalf = modelArmorHalf;
		this.modelArmor = modelArmor;
	}

	@Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		this.renderArmorSet(matrixStackIn, bufferIn, entitylivingbaseIn, EquipmentSlotType.HEAD, packedLightIn, this.getModel(EquipmentSlotType.HEAD));
		this.renderArmorSet(matrixStackIn, bufferIn, entitylivingbaseIn, EquipmentSlotType.CHEST, packedLightIn, this.getModel(EquipmentSlotType.CHEST));
		this.renderArmorSet(matrixStackIn, bufferIn, entitylivingbaseIn, EquipmentSlotType.LEGS, packedLightIn, this.getModel(EquipmentSlotType.LEGS));
		this.renderArmorSet(matrixStackIn, bufferIn, entitylivingbaseIn, EquipmentSlotType.FEET, packedLightIn, this.getModel(EquipmentSlotType.FEET));
	}

	private void renderArmorSet(MatrixStack matrixStack, IRenderTypeBuffer buffer, T entity, EquipmentSlotType slotType, int packedLight, A model) {
		ItemStack stack = entity.getItemStackFromSlot(slotType);
		if(stack.getItem() instanceof ArmorItem) {
			ArmorItem item = (ArmorItem) stack.getItem();
			if(item.getEquipmentSlot() == slotType) {
				this.getEntityModel().setModelAttributes(model);
				this.getEntityModel().setModelSlotVisible(entity, slotType);
				boolean effect = stack.hasEffect();
				if(item instanceof IDyeableArmorItem) {
					int base = ((IDyeableArmorItem) item).getColor(stack);
					float r = (float)(base >> 16 & 255) / 255.0f, g = (float)(base >> 8 & 255) / 255.0f, b = (float)(base & 255) / 255.0f;
					this.renderSlot(matrixStack, buffer, packedLight, effect, model, r, g, b, this.getArmorResource(entity, stack, slotType, null));
					this.renderSlot(matrixStack, buffer, packedLight, effect, model, 1.0f, 1.0f, 1.0f, this.getArmorResource(entity, stack, slotType, "overlay"));
				} else {
					this.renderSlot(matrixStack, buffer, packedLight, effect, model, 1.0f, 1.0f, 1.0f, this.getArmorResource(entity, stack, slotType, null));
				}
			}
		}
	}

	private void renderSlot(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, boolean effect, A model, float r, float g, float b, ResourceLocation armorTexture) {
		IVertexBuilder builder = ItemRenderer.getArmorVertexBuilder(buffer, RenderType.func_239263_a_(armorTexture), false, effect);
		model.render(matrixStack, builder, packedLight, OverlayTexture.NO_OVERLAY, r, g, b, 1.0f);
	}

	private A getModel(EquipmentSlotType slotType) {
		return (A)(this.isLegSlot(slotType) ? this.modelArmorHalf : this.modelArmor);
	}

	private boolean isLegSlot(EquipmentSlotType slotType) {
		return slotType == EquipmentSlotType.LEGS;
	}

	private ResourceLocation getArmorResource(T entity, ItemStack stack, EquipmentSlotType slot, @Nullable String type) {
		ArmorItem item = (ArmorItem)stack.getItem();
		String texture = item.getArmorMaterial().getName();
		String domain = "minecraft";
		int idx = texture.indexOf(':');
		if (idx != -1) {
			domain = texture.substring(0, idx);
			texture = texture.substring(idx + 1);
		}
		String s1 = String.format("%s:textures/models/armor/%s/%s_layer_%d%s.png", domain, entity.getType().getRegistryName().getPath(), texture, (isLegSlot(slot) ? 2 : 1), type == null ? "" : String.format("_%s", type));

		ResourceLocation resourcelocation = ARMOR_TEXTURE_RES_MAP.get(s1);

		if (resourcelocation == null) {
			resourcelocation = new ResourceLocation(s1);
			ARMOR_TEXTURE_RES_MAP.put(s1, resourcelocation);
		}

		return resourcelocation;
	}
}
