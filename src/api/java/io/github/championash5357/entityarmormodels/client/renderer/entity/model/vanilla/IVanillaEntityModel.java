package io.github.championash5357.entityarmormodels.client.renderer.entity.model.vanilla;

import io.github.championash5357.entityarmormodels.client.renderer.entity.model.IHasHeadEditable;
import io.github.championash5357.entityarmormodels.client.renderer.entity.model.IModelSlotVisible;
import io.github.championash5357.entityarmormodels.client.renderer.entity.model.IRandomModelRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;

public interface IVanillaEntityModel<T extends LivingEntity, A extends EntityModel<T>> extends IVanillaModelAttributes<T, A>, IModelSlotVisible<T>, IHasHeadEditable, IRandomModelRenderer, IVanillaArmorTextureAppension {}