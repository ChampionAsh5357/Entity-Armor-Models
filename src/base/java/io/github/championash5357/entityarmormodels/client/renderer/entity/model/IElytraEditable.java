package io.github.championash5357.entityarmormodels.client.renderer.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;

public interface IElytraEditable {

	default void translateElytra(MatrixStack stack, boolean isChild) {};	
}
