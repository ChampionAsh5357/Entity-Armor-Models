package io.github.championash5357.entityarmormodels.client.util;

import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

	private static final Logger LOGGER = LogManager.getLogger();
	
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
		this(modelEntityFactoryIn, 0, new float[]{0.0f, 0.5f, 1.0f}, elytraOffsetIn, orderFirst);
	}
	
	public RendererCast(Function<Float, A> modelEntityFactoryIn, int castIndex, BiConsumer<MatrixStack, Boolean> elytraOffsetIn, boolean orderFirst) {
		this(modelEntityFactoryIn, castIndex, new float[]{0.0f, 0.5f, 1.0f}, elytraOffsetIn, orderFirst);
	}
	
	public RendererCast(Function<Float, A> modelEntityFactoryIn, int castIndex, float[] modelValues, BiConsumer<MatrixStack, Boolean> elytraOffsetIn, boolean orderFirst) {
		this.modelEntityFactory = modelEntityFactoryIn;
		this.elytraOffset = elytraOffsetIn;
		this.orderFirst = orderFirst;
		this.modelValues = modelValues;
		switch(castIndex) {
		case 6:
			enabledLayers = new boolean[] {true, false, true, false, true, true};
			break;
		case 5:
			enabledLayers = new boolean[] {false, false, true, true, true, true};
			break;
		case 4:
			enabledLayers = new boolean[] {true, false, true, true, false, true};
			break;
		case 3:
			enabledLayers = new boolean[] {true, false, true, false, false, true};
			break;
		case 2:
			enabledLayers = new boolean[] {false, false, true, false, false, true};
			break;
		case 1:
			enabledLayers = new boolean[] {true, false, false, true, true, true};
			break;
		case 0:
		default:
			enabledLayers = new boolean[] {true, false, true, true, true, true};
			break;
		}
		for(int i = 0; i < 6; i++) enabledLayers[i] &= CONFIG_LAYERS[i];
	}
	
	public RendererCast<T, M, A> setHeadLayer(float xScaleIn, float yScaleIn, float zScaleIn) {
		headLayers[0] = xScaleIn; headLayers[1] = yScaleIn; headLayers[2] = zScaleIn;
		return this;
	}
	
	public void castAndApply(EntityRenderer<?> entityRenderer) {
		try {
			@SuppressWarnings("unchecked")
			LivingRenderer<T, M> livingrenderer = (LivingRenderer<T, M>) entityRenderer;
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
		} catch (ClassCastException e) {
			LOGGER.error("This entity renderer is incorrectly cast to a living renderer: {}", e);
		}
	}
}