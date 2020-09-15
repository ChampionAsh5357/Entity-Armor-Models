package io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.vanilla;

import net.minecraft.entity.EntityType;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.util.ResourceLocation;

public interface IVanillaArmorTextureAppension {

	default String armorTextureExpansion() {
		return "";
	}
	
	default ResourceLocation getHorseArmorLocation(EntityType<?> type, HorseArmorItem item) {
		return item.getArmorTexture();
	}
}
