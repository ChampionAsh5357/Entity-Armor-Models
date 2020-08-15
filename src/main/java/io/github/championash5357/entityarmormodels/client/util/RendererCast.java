package io.github.championash5357.entityarmormodels.client.util;

import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Function;

import com.mojang.blaze3d.matrix.MatrixStack;

import io.github.championash5357.entityarmormodels.client.renderer.entity.layers.vanilla.VanillaArmorLayer;
import io.github.championash5357.entityarmormodels.client.renderer.entity.layers.vanilla.VanillaArrowLayer;
import io.github.championash5357.entityarmormodels.client.renderer.entity.layers.vanilla.VanillaBeeStingerLayer;
import io.github.championash5357.entityarmormodels.client.renderer.entity.layers.vanilla.VanillaElytraLayer;
import io.github.championash5357.entityarmormodels.client.renderer.entity.layers.vanilla.VanillaHeadLayer;
import io.github.championash5357.entityarmormodels.client.renderer.entity.model.vanilla.IVanillaEntityModel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class RendererCast<T extends LivingEntity, M extends EntityModel<T>, A extends EntityModel<T> & IVanillaEntityModel<T, M>> {

	private final Function<Float, A> modelEntityFactory;
	private final float[] modelValues;
	private final BiConsumer<MatrixStack, Boolean> elytraOffset;
	private final boolean orderFirst;
	private final boolean[] enabledLayers;
	private final float[] headLayers = new float[] {1.0f, 1.0f, 1.0f};
	private static final boolean[] CONFIG_LAYERS = new boolean[] {ClientConfigHolder.CLIENT.enableArmorLayer.get(),
			ClientConfigHolder.CLIENT.enableHeldItemLayer.get(),
			ClientConfigHolder.CLIENT.enableArrowLayer.get(),
			ClientConfigHolder.CLIENT.enableHeadLayer.get(),
			ClientConfigHolder.CLIENT.enableElytraLayer.get(),
			ClientConfigHolder.CLIENT.enableBeeStingLayer.get()};

	public RendererCast(Function<Float, A> modelEntityFactoryIn, BiConsumer<MatrixStack, Boolean> elytraOffsetIn, boolean orderFirst) {
		this(modelEntityFactoryIn, Constants.NO_HELD_ITEM_LAYERS, new float[]{0.0f, 0.5f, 1.0f}, elytraOffsetIn, orderFirst);
	}

	public RendererCast(Function<Float, A> modelEntityFactoryIn, byte castIndex, BiConsumer<MatrixStack, Boolean> elytraOffsetIn, boolean orderFirst) {
		this(modelEntityFactoryIn, castIndex, new float[]{0.0f, 0.5f, 1.0f}, elytraOffsetIn, orderFirst);
	}

	public RendererCast(Function<Float, A> modelEntityFactoryIn, byte castIndex, float[] modelValues, BiConsumer<MatrixStack, Boolean> elytraOffsetIn, boolean orderFirst) {
		this.modelEntityFactory = modelEntityFactoryIn;
		this.elytraOffset = elytraOffsetIn;
		this.orderFirst = orderFirst;
		this.modelValues = modelValues;
		enabledLayers = new boolean[] {(castIndex & Constants.ARMOR_LAYER) != 0 && CONFIG_LAYERS[0],
				(castIndex & Constants.HELD_ITEM_LAYER) != 0 && CONFIG_LAYERS[1],
				(castIndex & Constants.ARROW_LAYER) != 0 && CONFIG_LAYERS[2],
				(castIndex & Constants.HEAD_LAYER) != 0 && CONFIG_LAYERS[3],
				(castIndex & Constants.ELYTRA_LAYER) != 0 && CONFIG_LAYERS[4],
				(castIndex & Constants.BEE_LAYER) != 0 && CONFIG_LAYERS[5]};
	}

	public RendererCast<T, M, A> setHeadLayer(float xScaleIn, float yScaleIn, float zScaleIn) {
		headLayers[0] = xScaleIn; headLayers[1] = yScaleIn; headLayers[2] = zScaleIn;
		return this;
	}

	public void castAndApply(EntityRenderer<?> entityRenderer) {
		if(entityRenderer instanceof LivingRenderer<?, ?>) {
			@SuppressWarnings("unchecked")
			LivingRenderer<T, M> livingrenderer = (LivingRenderer<T, M>) entityRenderer; //Assumes I'm not dumb and referenced my casts right

			A entityModel = modelEntityFactory.apply(modelValues[0]), halfArmorModel = modelEntityFactory.apply(modelValues[1]), armorModel = modelEntityFactory.apply(modelValues[2]);

			if(orderFirst) {
				ArrayList<LayerRenderer<?, ?>> layerRenderers = ObfuscationReflectionHelper.getPrivateValue(LivingRenderer.class, livingrenderer, "field_177097_h");
				if(enabledLayers[5]) layerRenderers.add(0, new VanillaBeeStingerLayer<>(livingrenderer, entityModel));
				if(enabledLayers[4]) layerRenderers.add(0, new VanillaElytraLayer<>(livingrenderer, elytraOffset));
				if(enabledLayers[3]) layerRenderers.add(0, new VanillaHeadLayer<>(livingrenderer, entityModel, headLayers[0], headLayers[1], headLayers[2]));
				if(enabledLayers[2]) layerRenderers.add(0, new VanillaArrowLayer<>(livingrenderer, entityModel));
				if(enabledLayers[0]) layerRenderers.add(0, new VanillaArmorLayer<>(livingrenderer, halfArmorModel, armorModel));
			} else {
				if(enabledLayers[0]) livingrenderer.addLayer(new VanillaArmorLayer<>(livingrenderer, halfArmorModel, armorModel));
				if(enabledLayers[2]) livingrenderer.addLayer(new VanillaArrowLayer<>(livingrenderer, entityModel));
				if(enabledLayers[3]) livingrenderer.addLayer(new VanillaHeadLayer<>(livingrenderer, entityModel, headLayers[0], headLayers[1], headLayers[2]));
				if(enabledLayers[4]) livingrenderer.addLayer(new VanillaElytraLayer<>(livingrenderer, elytraOffset));
				if(enabledLayers[5]) livingrenderer.addLayer(new VanillaBeeStingerLayer<>(livingrenderer, entityModel));
			}
		}
	}

	public static class Constants {
		public static final byte ARMOR_LAYER = 0b100000;
		public static final byte HELD_ITEM_LAYER = 0b10000;
		public static final byte ARROW_LAYER = 0b1000;
		public static final byte HEAD_LAYER = 0b100;
		public static final byte ELYTRA_LAYER = 0b10;
		public static final byte BEE_LAYER = 0b1;

		public static final byte CONTACT_LAYERS = ARROW_LAYER | BEE_LAYER;
		public static final byte NO_HELD_ITEM_LAYERS = ARMOR_LAYER | HEAD_LAYER | ELYTRA_LAYER | CONTACT_LAYERS;
		public static final byte NO_HELD_HEAD_LAYERS = NO_HELD_ITEM_LAYERS & ~HEAD_LAYER;
		public static final byte NO_HELD_ARMOR_LAYERS = NO_HELD_ITEM_LAYERS & ~ARMOR_LAYER;
		public static final byte NO_HELD_ELYTRA_LAYERS = NO_HELD_ITEM_LAYERS & ~ELYTRA_LAYER;
		public static final byte ALL_LAYERS = ARMOR_LAYER | HELD_ITEM_LAYER | HEAD_LAYER | ELYTRA_LAYER | CONTACT_LAYERS;
	}
}