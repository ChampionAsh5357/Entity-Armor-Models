package io.github.championash5357.entityarmormodels.client.renderer.entity.model;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;

public interface IModelAttributes<T extends LivingEntity, A extends EntityModel<T>> {

	void setModelAttributes(A model);
}
