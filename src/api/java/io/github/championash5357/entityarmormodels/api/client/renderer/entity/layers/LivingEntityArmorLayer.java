/*
 * Entity Armor Models
 * Copyright (C) 2020 ChampionAsh5357
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation version 3.0 of the License.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.github.championash5357.entityarmormodels.api.client.renderer.entity.layers;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.IModelAttributes;
import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.IModelSlotVisible;
import io.github.championash5357.entityarmormodels.api.client.util.ArmorModelRegistry;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
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
 * */
public class LivingEntityArmorLayer<T extends LivingEntity, M extends EntityModel<T> & IModelAttributes<T, A> & IModelSlotVisible<T>, A extends EntityModel<T>> extends LayerRenderer<T, M> {

	private static final Map<String, ResourceLocation> ARMOR_TEXTURE_RES_MAP = new HashMap<>();
	private final A modelArmorHalf, modelArmor;
	
	/**
	 * A constructor to apply the layer.
	 * 
	 * @param entityRenderer
	 * 			The associated entity renderer.
	 * @param modelArmorHalf
	 * 			The armor model usually associated with the legs slot. Usually half of modelArmor.
	 * @param modelArmor
	 * 			The armor model for everything but the legs slot.
	 * */
	public LivingEntityArmorLayer(IEntityRenderer<T, M> entityRenderer, A modelArmorHalf, A modelArmor) {
		super(entityRenderer);
		this.modelArmorHalf = modelArmorHalf;
		this.modelArmor = modelArmor;
	}

	@Override
	public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, T entitylivingbase, float limbSwing, float limbSwingAmount, float partialTicks, float ageTicks, float netHeadYaw, float headPitch) {
		this.renderArmorSet(matrixStack, buffer, entitylivingbase, EquipmentSlotType.HEAD, packedLight, this.getModel(EquipmentSlotType.HEAD));
		this.renderArmorSet(matrixStack, buffer, entitylivingbase, EquipmentSlotType.CHEST, packedLight, this.getModel(EquipmentSlotType.CHEST));
		this.renderArmorSet(matrixStack, buffer, entitylivingbase, EquipmentSlotType.LEGS, packedLight, this.getModel(EquipmentSlotType.LEGS));
		this.renderArmorSet(matrixStack, buffer, entitylivingbase, EquipmentSlotType.FEET, packedLight, this.getModel(EquipmentSlotType.FEET));
	}

	private void renderArmorSet(MatrixStack matrixStack, IRenderTypeBuffer buffer, T entity, EquipmentSlotType slotType, int packedLight, A model) {
		ItemStack stack = entity.getItemStackFromSlot(slotType);
		if(stack.getItem() instanceof ArmorItem) {
			ArmorItem item = (ArmorItem) stack.getItem();
			if(item.getEquipmentSlot() == slotType) {
				model = getArmorModel(item.getArmorMaterial(), entity, stack, slotType, model);
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
		IVertexBuilder builder = ItemRenderer.getArmorVertexBuilder(buffer, RenderType.getArmorCutoutNoCull(armorTexture), false, effect);
		model.render(matrixStack, builder, packedLight, OverlayTexture.NO_OVERLAY, r, g, b, 1.0f);
	}

	private A getModel(EquipmentSlotType slotType) {
		return (A)(this.isLegSlot(slotType) ? this.modelArmorHalf : this.modelArmor);
	}

	private boolean isLegSlot(EquipmentSlotType slotType) {
		return slotType == EquipmentSlotType.LEGS;
	}
	
	@SuppressWarnings("unchecked")
	private A getArmorModel(IArmorMaterial material, T entity, ItemStack stack, EquipmentSlotType slot, A _default) {
		@Nullable Object model = ArmorModelRegistry.getModelMappings((EntityType<T>) entity.getType()).getModel(material, entity, stack, slot, _default);
		return model != null && _default.getClass().isInstance(model) ? (A) model : _default;
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
		String s1 = String.format("%s:textures/models/armor/%s/%s_layer_%d%s.png", domain, entity.getType().getRegistryName().getNamespace() + "_" + entity.getType().getRegistryName().getPath(), texture, (isLegSlot(slot) ? 2 : 1), type == null ? "" : String.format("_%s", type));

		s1 = net.minecraftforge.client.ForgeHooksClient.getArmorTexture(entity, stack, s1, slot, type);
		ResourceLocation resourcelocation = ARMOR_TEXTURE_RES_MAP.get(s1);

		if (resourcelocation == null) {
			resourcelocation = new ResourceLocation(s1);
			ARMOR_TEXTURE_RES_MAP.put(s1, resourcelocation);
		}

		return resourcelocation;
	}
}
