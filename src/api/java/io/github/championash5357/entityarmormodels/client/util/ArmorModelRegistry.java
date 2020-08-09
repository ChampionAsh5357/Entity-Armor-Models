package io.github.championash5357.entityarmormodels.client.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.IArmorMaterial;

public class ArmorModelRegistry {
	
	private static final Map<EntityType<?>, ArmorModel<?, ?>> MODEL_MAPPINGS = new ConcurrentHashMap<>();
	
	public static <T extends LivingEntity, A extends EntityModel<T>> void addArmorModel(EntityType<T> typeIn, IArmorMaterial materialIn, A modelIn) {
		getModelMappings(typeIn).addArmorModel(materialIn, modelIn);
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends LivingEntity, A extends EntityModel<T>> ArmorModel<T, A> getModelMappings(EntityType<T> typeIn) {
		return (ArmorModel<T, A>) MODEL_MAPPINGS.computeIfAbsent(typeIn, t -> new ArmorModel<>());
	}
	
	public static class ArmorModel<T extends LivingEntity, A extends EntityModel<T>> {

		private final Map<IArmorMaterial, A> armorModelMap = new HashMap<>();
		
		private ArmorModel() {}
		
		private ArmorModel<T, A> addArmorModel(IArmorMaterial material, A modelIn) {
			if(material instanceof ArmorMaterial) return this; // We don't want users adding in vanilla overrides
			this.armorModelMap.putIfAbsent(material, modelIn);
			return this;
		}
		
		public A getModel(IArmorMaterial material) {
			return this.armorModelMap.get(material);
		}
	}
}
