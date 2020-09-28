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

package io.github.championash5357.entityarmormodels.api.client.util;

import javax.annotation.Nullable;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BreakableBlock;
import net.minecraft.block.StainedGlassPaneBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;

/**
 * A renderer extension that allows items to be rendered 
 * with no culling applied.
 * */
public abstract class ItemRendererExtension extends RenderType {
	
	public ItemRendererExtension(String name, VertexFormat format, int drawMode, int bufferSize, boolean useDelegate, boolean needsSorting, Runnable setupTask, Runnable clearTask) {
		super(name, format, drawMode, bufferSize, useDelegate, needsSorting, setupTask, clearTask);
	}

	private static final RenderType CUTOUT_NO_CULL_BLOCK_TYPE = RenderType.getEntityCutoutNoCull(PlayerContainer.LOCATION_BLOCKS_TEXTURE);
	private static final RenderType TRANSLUCENT_NO_CULL_BLOCK_TYPE = RenderType.getEntityTranslucent(PlayerContainer.LOCATION_BLOCKS_TEXTURE);
	private static final RenderType TRANSLUCENT_NO_CULL_ITEM_TYPE = getItemEntityTranslucentNoCull(PlayerContainer.LOCATION_BLOCKS_TEXTURE);
	private static ItemRenderer renderer;
	private static int combinedOverlay = OverlayTexture.NO_OVERLAY;
	
	/**
	 * Renders an item with no cull. See {@link ItemRenderer#renderItem(ItemStack, TransformType, boolean, MatrixStack, IRenderTypeBuffer, int, int, IBakedModel)} usage for basic implementation.
	 * 
	 * @param entity
	 * 			The entity holding the item. Can be null.
	 * @param stack
	 * 			The ItemStack instance.
	 * @param type
	 * 			The camera transform of the item.
	 * @param leftHand
	 * 			Whether or not the item is in the left hand.
	 * @param matrixStack
	 * 			The associated MatrixStack.
	 * @param buffer
	 * 			The render type buffer.
	 * @param combinedLight
	 * 			The combined light of the area.
	 * */
	public static void renderItemNoCull(@Nullable LivingEntity entity, ItemStack stack, TransformType type, boolean leftHand, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight) {
		if(renderer == null) renderer = Minecraft.getInstance().getItemRenderer();
		IBakedModel model = renderer.getItemModelWithOverrides(stack, entity.world, entity);
		matrixStack.push();
		boolean flag = type == ItemCameraTransforms.TransformType.GUI || type == ItemCameraTransforms.TransformType.GROUND || type == ItemCameraTransforms.TransformType.FIXED;
		if (stack.getItem() == Items.TRIDENT && flag) {
			model = renderer.getItemModelMesher().getModelManager().getModel(new ModelResourceLocation("minecraft:trident#inventory"));
		}

		model = net.minecraftforge.client.ForgeHooksClient.handleCameraTransforms(matrixStack, model, type, leftHand);
		matrixStack.translate(-0.5D, -0.5D, -0.5D);
		if (!model.isBuiltInRenderer() && (stack.getItem() != Items.TRIDENT || flag)) {
			boolean flag1;
			if (type != ItemCameraTransforms.TransformType.GUI && !type.isFirstPerson() && stack.getItem() instanceof BlockItem) {
				Block block = ((BlockItem)stack.getItem()).getBlock();
				flag1 = !(block instanceof BreakableBlock) && !(block instanceof StainedGlassPaneBlock);
			} else {
				flag1 = true;
			}
			if (model.isLayered()) { net.minecraftforge.client.ForgeHooksClient.drawItemLayered(renderer, model, stack, matrixStack, buffer, combinedLight, combinedOverlay, flag1); }
			else {
				RenderType rendertype = itemNoCull(stack, flag1);
				IVertexBuilder ivertexbuilder;
				if (stack.getItem() == Items.COMPASS && stack.hasEffect()) {
					matrixStack.push();
					MatrixStack.Entry matrixstack$entry = matrixStack.getLast();
					if (type == ItemCameraTransforms.TransformType.GUI) {
						matrixstack$entry.getMatrix().mul(0.5F);
					} else if (type.isFirstPerson()) {
						matrixstack$entry.getMatrix().mul(0.75F);
					}

					if (flag1) {
						ivertexbuilder = ItemRenderer.getDirectGlintVertexBuilder(buffer, rendertype, matrixstack$entry);
					} else {
						ivertexbuilder = ItemRenderer.getGlintVertexBuilder(buffer, rendertype, matrixstack$entry);
					}

					matrixStack.pop();
				} else if (flag1) {
					ivertexbuilder = ItemRenderer.getEntityGlintVertexBuilder(buffer, rendertype, true, stack.hasEffect());
				} else {
					ivertexbuilder = ItemRenderer.getBuffer(buffer, rendertype, true, stack.hasEffect());
				}

				renderer.renderModel(model, stack, combinedLight, combinedOverlay, matrixStack, ivertexbuilder);
			}
		} else {
			stack.getItem().getItemStackTileEntityRenderer().func_239207_a_(stack, type, matrixStack, buffer, combinedLight, combinedOverlay);
		}

		matrixStack.pop();
	}

	/**
	 * Gets the render type of an item with no cull. See {@link RenderTypeLookup#func_239219_a_(ItemStack, boolean)} usage for basic implementation.
	 * 
	 * @param stack
	 * 			The associated ItemStack.
	 * @param notBreakable
	 * 			If the associated item inside is not breakable.
	 * @return The {@link RenderType} of the item.
	 * */
	public static RenderType itemNoCull(ItemStack stack, boolean notBreakable) {
		Item item = stack.getItem();
		if(item instanceof BlockItem) {
			Block block = ((BlockItem) item).getBlock();
			return renderBlockNoCull(block.getDefaultState(), notBreakable);
		}
		return notBreakable ? TRANSLUCENT_NO_CULL_BLOCK_TYPE : TRANSLUCENT_NO_CULL_ITEM_TYPE;
	}

	/**
	 * Gets the render type of a block with no cull. See {@link RenderTypeLookup#func_239220_a_(BlockState, boolean)} usage for basic implementation.
	 * 
	 * @param state
	 * 			The associated BlockState.
	 * @param notBreakable
	 * 			If the associated item inside is not breakable.
	 * @return The {@link RenderType} of the block.
	 * */
	public static RenderType renderBlockNoCull(BlockState state, boolean notBreakable) {
		if(RenderTypeLookup.canRenderInLayer(state, RenderType.getTranslucent())) {
			if(!Minecraft.isFabulousGraphicsEnabled()) {
				return TRANSLUCENT_NO_CULL_BLOCK_TYPE;
			} else {
				return notBreakable ? TRANSLUCENT_NO_CULL_BLOCK_TYPE : TRANSLUCENT_NO_CULL_ITEM_TYPE;
			}
		} else {
			return CUTOUT_NO_CULL_BLOCK_TYPE;
		}
	}
	
	/**
	 * Gets a {@link RenderType} for an entity with translucent textures and no culling.
	 * 
	 * @param location
	 * 			The path of the texture.
	 * @return The associated {@link RenderType}.
	 * */
	public static RenderType getItemEntityTranslucentNoCull(ResourceLocation location) {
		RenderType.State state = RenderType.State.getBuilder().texture(new RenderState.TextureState(location, false, false)).transparency(TRANSLUCENT_TRANSPARENCY).target(field_241712_U_).diffuseLighting(DIFFUSE_LIGHTING_ENABLED).alpha(DEFAULT_ALPHA).cull(CULL_DISABLED).lightmap(LIGHTMAP_ENABLED).overlay(OVERLAY_ENABLED).writeMask(COLOR_DEPTH_WRITE).build(true);
		return makeType("item_entity_translucent_no_cull", DefaultVertexFormats.ENTITY, 7, 256, state);
	}
}
