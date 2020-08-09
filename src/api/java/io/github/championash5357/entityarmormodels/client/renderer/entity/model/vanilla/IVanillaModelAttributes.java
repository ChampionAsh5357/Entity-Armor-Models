package io.github.championash5357.entityarmormodels.client.renderer.entity.model.vanilla;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;

public interface IVanillaModelAttributes<T extends LivingEntity, M extends EntityModel<T>> {

	void copyAttributesOfModel(M model);
}
