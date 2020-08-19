package io.github.championash5357.entityarmormodels.client.util;

import java.util.Random;

import javax.annotation.Nullable;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraftforge.client.model.data.EmptyModelData;

/**
 * A renderer extension that allows items to be rendered 
 * with no culling applied.
 * */
public abstract class ItemRendererExtension extends RenderType {

	public ItemRendererExtension(String nameIn, VertexFormat formatIn, int drawModeIn, int bufferSizeIn, boolean useDelegateIn, boolean needsSortingIn, Runnable setupTaskIn, Runnable clearTaskIn) {
		super(nameIn, formatIn, drawModeIn, bufferSizeIn, useDelegateIn, needsSortingIn, setupTaskIn, clearTaskIn);
	}

	private static final RenderType CUTOUT_NO_CULL_BLOCK_TYPE = RenderType.getEntityCutoutNoCull(PlayerContainer.LOCATION_BLOCKS_TEXTURE);
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
		boolean flag = type == ItemCameraTransforms.TransformType.GUI;
		boolean flag1 = flag || type == ItemCameraTransforms.TransformType.GROUND || type == ItemCameraTransforms.TransformType.FIXED;
		if (stack.getItem() == Items.TRIDENT && flag1) {
			model = renderer.getItemModelMesher().getModelManager().getModel(new ModelResourceLocation("minecraft:trident#inventory"));
		}

		model = net.minecraftforge.client.ForgeHooksClient.handleCameraTransforms(matrixStack, model, type, leftHand);
		matrixStack.translate(-0.5D, -0.5D, -0.5D);
		if (!model.isBuiltInRenderer() && (stack.getItem() != Items.TRIDENT || flag1)) {
			RenderType rendertype = getRenderType(stack);
			
			IVertexBuilder ivertexbuilder = ItemRenderer.getBuffer(buffer, rendertype, true, stack.hasEffect());
			renderModel(model, stack, combinedLight, combinedOverlay, matrixStack, ivertexbuilder);
		} else {
			stack.getItem().getItemStackTileEntityRenderer().render(stack, matrixStack, buffer, combinedLight, combinedOverlay);
		}

		matrixStack.pop();
	}
	
	/**
	 * Identical method of {@link ItemRenderer#renderModel}.
	 * 
	 * @param modelIn
	 * 			The model being rendered.
	 * @param stack
	 * 			The ItemStack instance.
	 * @param combinedLightIn
	 * 			The combined light of the area.
	 * @param combinedOverlayIn
	 * 			The combined overlay of the area.
	 * @param matrixStackIn
	 * 			The associated MatrixStack.
	 * @param bufferIn
	 * 			The render type buffer.
	 * */
	private static void renderModel(IBakedModel modelIn, ItemStack stack, int combinedLightIn, int combinedOverlayIn, MatrixStack matrixStackIn, IVertexBuilder bufferIn) {
	      Random random = new Random();

	      for(Direction direction : Direction.values()) {
	         random.setSeed(42L);
	         renderer.renderQuads(matrixStackIn, bufferIn, modelIn.getQuads((BlockState)null, direction, random, EmptyModelData.INSTANCE), stack, combinedLightIn, combinedOverlayIn);
	      }

	      random.setSeed(42L);
	      renderer.renderQuads(matrixStackIn, bufferIn, modelIn.getQuads((BlockState)null, (Direction)null, random, EmptyModelData.INSTANCE), stack, combinedLightIn, combinedOverlayIn);
	   }

	/**
	 * Gets the render type of a block with no cull. See {@link RenderTypeLookup#getRenderType(BlockState)} usage for basic implementation.
	 * 
	 * @param state
	 * 			The associated BlockState.
	 * @return The {@link RenderType} of the block.
	 * */
	public static RenderType getRenderType(BlockState blockStateIn) {
		return RenderTypeLookup.canRenderInLayer(blockStateIn, RenderType.getTranslucent()) ? Atlases.getTranslucentBlockType() : CUTOUT_NO_CULL_BLOCK_TYPE;
	}

	/**
	 * Gets the render type of an item with no cull. See {@link RenderTypeLookup#getRenderType(ItemStack)} usage for basic implementation.
	 * 
	 * @param stack
	 * 			The associated ItemStack.
	 * @return The {@link RenderType} of the item.
	 * */
	public static RenderType getRenderType(ItemStack itemStackIn) {
		Item item = itemStackIn.getItem();
		if (item instanceof BlockItem) {
			Block block = ((BlockItem)item).getBlock();
			return getRenderType(block.getDefaultState());
		} else {
			return Atlases.getTranslucentBlockType();
		}
	}
}
