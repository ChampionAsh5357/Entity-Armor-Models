/*
 * Entity Armor Models
 * Copyright (C) 2020 ChampionAsh5357
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation version 3.0 of the License.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.github.championash5357.entityarmormodels.client.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Function;

import com.mojang.blaze3d.matrix.MatrixStack;

import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.vanilla.IVanillaEntityModel;
import io.github.championash5357.entityarmormodels.client.ClientConfigHolder;
import io.github.championash5357.entityarmormodels.client.renderer.entity.layers.vanilla.VanillaArmorLayer;
import io.github.championash5357.entityarmormodels.client.renderer.entity.layers.vanilla.VanillaArrowLayer;
import io.github.championash5357.entityarmormodels.client.renderer.entity.layers.vanilla.VanillaBackItemLayer;
import io.github.championash5357.entityarmormodels.client.renderer.entity.layers.vanilla.VanillaBeeStingerLayer;
import io.github.championash5357.entityarmormodels.client.renderer.entity.layers.vanilla.VanillaElytraLayer;
import io.github.championash5357.entityarmormodels.client.renderer.entity.layers.vanilla.VanillaHeadLayer;
import io.github.championash5357.entityarmormodels.client.renderer.entity.layers.vanilla.VanillaHorseArmorLayer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class RendererCast<T extends LivingEntity, M extends EntityModel<T>, A extends EntityModel<T> & IVanillaEntityModel<T, M>> {

	private static final Field LAYER_RENDERERS = ObfuscationReflectionHelper.findField(LivingRenderer.class, "field_177097_h");
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
	public static boolean backLayerPresent;

	public RendererCast(Function<Float, A> modelEntityFactory, BiConsumer<MatrixStack, Boolean> elytraOffset, boolean orderFirst) {
		this(modelEntityFactory, Constants.NO_HELD_ITEM_LAYERS, new float[]{0.0f, 0.5f, 1.0f}, elytraOffset, orderFirst);
	}

	public RendererCast(Function<Float, A> modelEntityFactory, byte castdex, BiConsumer<MatrixStack, Boolean> elytraOffset, boolean orderFirst) {
		this(modelEntityFactory, castdex, new float[]{0.0f, 0.5f, 1.0f}, elytraOffset, orderFirst);
	}

	public RendererCast(Function<Float, A> modelEntityFactory, byte castdex, float[] modelValues, BiConsumer<MatrixStack, Boolean> elytraOffset, boolean orderFirst) {
		this.modelEntityFactory = modelEntityFactory;
		this.elytraOffset = elytraOffset;
		this.orderFirst = orderFirst;
		this.modelValues = modelValues;
		enabledLayers = new boolean[] {(castdex & Constants.ARMOR_LAYER) != 0 && CONFIG_LAYERS[0],
				(castdex & Constants.HELD_ITEM_LAYER) != 0 && CONFIG_LAYERS[1],
				(castdex & Constants.ARROW_LAYER) != 0 && CONFIG_LAYERS[2],
				(castdex & Constants.HEAD_LAYER) != 0 && CONFIG_LAYERS[3],
				(castdex & Constants.ELYTRA_LAYER) != 0 && CONFIG_LAYERS[4],
				(castdex & Constants.BEE_LAYER) != 0 && CONFIG_LAYERS[5],
				(castdex & Constants.HORSE_ARMOR_LAYER) != 0 && CONFIG_LAYERS[0],
				backLayerPresent};
	}

	public RendererCast<T, M, A> setHeadLayer(float xScale, float yScale, float zScale) {
		headLayers[0] = xScale; headLayers[1] = yScale; headLayers[2] = zScale;
		return this;
	}

	@SuppressWarnings("unchecked")
	public void castAndApply(EntityRenderer<?> entityRenderer) {
		if(entityRenderer instanceof LivingRenderer<?, ?>) {
			LivingRenderer<T, M> livingrenderer = (LivingRenderer<T, M>) entityRenderer; //Assumes I'm not dumb and referenced my casts right

			A entityModel = modelEntityFactory.apply(modelValues[0]), halfArmorModel = modelEntityFactory.apply(modelValues[1]), armorModel = modelEntityFactory.apply(modelValues[2]);

			if(orderFirst) {
				ArrayList<LayerRenderer<?, ?>> layerRenderers = getLayerRenderers(livingrenderer);
				if(enabledLayers[7]) layerRenderers.add(0, new VanillaBackItemLayer<>(livingrenderer, entityModel));
				if(enabledLayers[5]) layerRenderers.add(0, new VanillaBeeStingerLayer<>(livingrenderer, entityModel));
				if(enabledLayers[4]) layerRenderers.add(0, new VanillaElytraLayer<>(livingrenderer, elytraOffset));
				if(enabledLayers[3]) layerRenderers.add(0, new VanillaHeadLayer<>(livingrenderer, entityModel, headLayers[0], headLayers[1], headLayers[2]));
				if(enabledLayers[2]) layerRenderers.add(0, new VanillaArrowLayer<>(livingrenderer, entityModel));
				if(enabledLayers[0]) layerRenderers.add(0, new VanillaArmorLayer<>(livingrenderer, halfArmorModel, armorModel));
				else if(enabledLayers[6]) {
					layerRenderers.add(0, new VanillaHorseArmorLayer<>(livingrenderer, modelEntityFactory.apply(0.1f)));
				}
			} else {
				if(enabledLayers[0]) livingrenderer.addLayer(new VanillaArmorLayer<>(livingrenderer, halfArmorModel, armorModel));
				else if(enabledLayers[6]) {
					livingrenderer.addLayer(new VanillaHorseArmorLayer<>(livingrenderer, modelEntityFactory.apply(0.1f)));
				}
				if(enabledLayers[2]) livingrenderer.addLayer(new VanillaArrowLayer<>(livingrenderer, entityModel));
				if(enabledLayers[3]) livingrenderer.addLayer(new VanillaHeadLayer<>(livingrenderer, entityModel, headLayers[0], headLayers[1], headLayers[2]));
				if(enabledLayers[4]) livingrenderer.addLayer(new VanillaElytraLayer<>(livingrenderer, elytraOffset));
				if(enabledLayers[5]) livingrenderer.addLayer(new VanillaBeeStingerLayer<>(livingrenderer, entityModel));
				if(enabledLayers[7]) livingrenderer.addLayer(new VanillaBackItemLayer<>(livingrenderer, entityModel));
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private static ArrayList<LayerRenderer<?, ?>> getLayerRenderers(LivingRenderer<?, ?> livingRenderer) {
		try {
			return (ArrayList<LayerRenderer<?, ?>>) LAYER_RENDERERS.get(livingRenderer);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public static class Constants {
		public static final byte ARMOR_LAYER = 0x20;
		public static final byte HELD_ITEM_LAYER = 0x10;
		public static final byte ARROW_LAYER = 0x8;
		public static final byte HEAD_LAYER = 0x4;
		public static final byte ELYTRA_LAYER = 0x2;
		public static final byte BEE_LAYER = 0x1;
		public static final byte HORSE_ARMOR_LAYER = 0x40;

		public static final byte CONTACT_LAYERS = ARROW_LAYER | BEE_LAYER;
		public static final byte NO_HELD_ITEM_LAYERS = ARMOR_LAYER | HEAD_LAYER | ELYTRA_LAYER | CONTACT_LAYERS;
		public static final byte NO_HELD_HEAD_LAYERS = NO_HELD_ITEM_LAYERS & ~HEAD_LAYER;
		public static final byte NO_HELD_ARMOR_LAYERS = NO_HELD_ITEM_LAYERS & ~ARMOR_LAYER;
		public static final byte HORSE_ARMOR_LAYERS = NO_HELD_ARMOR_LAYERS | HORSE_ARMOR_LAYER;
		public static final byte NO_HELD_ELYTRA_LAYERS = NO_HELD_ITEM_LAYERS & ~ELYTRA_LAYER;
		public static final byte ALL_LAYERS = ARMOR_LAYER | HELD_ITEM_LAYER | HEAD_LAYER | ELYTRA_LAYER | CONTACT_LAYERS;
	}
}