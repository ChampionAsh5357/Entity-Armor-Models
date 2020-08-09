package io.github.championash5357.entityarmormodels.client.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.IArmorMaterial;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * Registry used to attach custom armor models to 
 * entities. Vanilla armors are not supported since 
 * there would be many overlaps.
 * */
public class ArmorModelRegistry {
	
	private static final Map<EntityType<?>, ArmorModel<?, ?>> MODEL_MAPPINGS = new ConcurrentHashMap<>();
	
	/**
	 * Used to add custom armor models to vanilla and modded 
	 * entities. The correct entity model needs to be used 
	 * according to which entity it is applied to. Should be 
	 * safe to call during {@link FMLClientSetupEvent}.
	 * 
	 * @param typeIn
	 * 			The entity to apply the model to.
	 * @param materialIn
	 * 			The armor material of the model.
	 * @param modelIn
	 * 			The custom model.
	 * */
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
