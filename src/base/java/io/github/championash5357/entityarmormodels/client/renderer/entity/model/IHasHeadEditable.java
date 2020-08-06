package io.github.championash5357.entityarmormodels.client.renderer.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.entity.model.IHasHead;

public interface IHasHeadEditable extends IHasHead {

	default void preRender(MatrixStack stack, boolean isChild) {}
	
	default void skullRender(MatrixStack stack, boolean isChild) {}
	
	default void itemRender(MatrixStack stack, boolean isChild) {}
	
	default float getXScale() { return 1.0f; }
	default float getYScale() { return 1.0f; }
	default float getZScale() { return 1.0f; }
}
