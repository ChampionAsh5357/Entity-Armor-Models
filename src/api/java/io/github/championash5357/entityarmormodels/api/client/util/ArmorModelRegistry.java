package io.github.championash5357.entityarmormodels.api.client.util;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * Registry used to attach custom armor models to 
 * entities. Vanilla armors are not supported since 
 * there would be many overlaps.
 * */
public class ArmorModelRegistry {
	
	private static final Map<EntityType<?>, ArmorModel<?, ?>> MODEL_MAPPINGS = new HashMap<>();
	
	/**
	 * Used to add custom armor models to vanilla and modded 
	 * entities. The correct entity model needs to be used 
	 * according to which entity it is applied to. Should be 
	 * safe to call during {@link FMLClientSetupEvent}.
	 * 
	 * @param type
	 * 			The entity to apply the model to.
	 * @param material
	 * 			The armor material of the model.
	 * @param model
	 * 			The model provider.
	 * */
	public static synchronized <T extends LivingEntity> void addArmorModel(EntityType<T> type, IArmorMaterial material, IModelProvider<T, EntityModel<T>> model) {
		getModelMappings(type).addArmorModel(material, model);
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends LivingEntity, A extends EntityModel<T>> ArmorModel<T, A> getModelMappings(EntityType<T> type) {
		return (ArmorModel<T, A>) MODEL_MAPPINGS.computeIfAbsent(type, t -> new ArmorModel<>());
	}
	
	public static class ArmorModel<T extends LivingEntity, A extends EntityModel<T>> {

		private final Map<IArmorMaterial, IModelProvider<T, A>> armorModelMap = new HashMap<>();
		
		private ArmorModel() {}
		
		private ArmorModel<T, A> addArmorModel(IArmorMaterial material, IModelProvider<T, A> model) {
			if(material instanceof ArmorMaterial) return this; // We don't want users adding in vanilla overrides
			this.armorModelMap.putIfAbsent(material, model);
			return this;
		}
		
		@Nullable
		public A getModel(IArmorMaterial material, T entity, ItemStack stack, EquipmentSlotType slot, A _default) {
			return this.armorModelMap.getOrDefault(material, (a, b, c, d) -> null).getModel(entity, stack, slot, _default);
		}
	}
	
	/**
	 * Interface used to get a custom armor 
	 * model from the current entity. All models 
	 * should be cached and stored for usage 
	 * with the parameters provided.
	 * */
	@FunctionalInterface
	public static interface IModelProvider<T extends LivingEntity, A extends EntityModel<T>> {
		
		/**
		 * Get the model associated with this provider.
		 * 
		 * @param entity
		 * 			The entity to apply the model to.
		 * @param stack
		 * 			The worn item.
		 * @param slot
		 * 			The current slot of the item.
		 * @param _default
		 * 			The default model being used.
		 * @return The custom model to apply.
		 * */
		@Nullable
		A getModel(T entity, ItemStack stack, EquipmentSlotType slot, A _default);
	}
}
