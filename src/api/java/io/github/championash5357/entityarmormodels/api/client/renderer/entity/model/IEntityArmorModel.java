package io.github.championash5357.entityarmormodels.api.client.renderer.entity.model;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;

/**
 * A convenience interface to add the required interfaces 
 * needed to set up custom armor models on an entity.
 * */
public interface IEntityArmorModel<T extends LivingEntity, A extends EntityModel<T>> extends IModelAttributes<T, A>, IModelSlotVisible<T> {}