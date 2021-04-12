/*
 * Entity Armor Models
 * Copyright (c) 2021-2021 ChampionAsh5357.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.ashwork.entityarmormodels.client.renderer.entity.layers;

import java.util.Random;
import java.util.function.Function;

import javax.annotation.Nullable;

import org.codehaus.plexus.util.StringUtils;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.ashwork.entityarmormodels.client.renderer.RendererInformation;
import net.ashwork.entityarmormodels.client.renderer.entity.HeadTransformationMatrix;
import net.ashwork.entityarmormodels.client.renderer.entity.model.EntityModelData;
import net.ashwork.entityarmormodels.client.util.ClientCache;
import net.minecraft.block.AbstractSkullBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.SkullTileEntityRenderer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.SkullTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.common.util.Constants;

public class EntityArmorLayer<T extends LivingEntity, M extends EntityModel<T>, A extends EntityModel<T>> extends LayerRenderer<T, M> {

    private static final ResourceLocation BEE_STINGER_LOCATION = new ResourceLocation("textures/entity/bee/bee_stinger.png");
    private static final ResourceLocation WINGS_LOCATION = new ResourceLocation("textures/entity/elytra.png");
    private static final ClientCache CACHE = new ClientCache();
    private final EntityRendererManager manager;
    private final LayerRendererData<T, M> layerData;
    private final EntityModelData<T, A> modelData;
    private final boolean armor, head, mainhand, offhand, elytra, arrows, beeStings;
    private RendererInformation renderInfo;
    private boolean shouldLayerFirst;

    public EntityArmorLayer(LivingRenderer<T, M> renderer, LayerRendererData<T, M> layerData, EntityModelData<T, A> modelData, byte disabledLayers) {
        super(renderer);
        this.manager = renderer.getDispatcher();
        this.layerData = layerData;
        this.modelData = modelData;
        this.renderInfo = RendererInformation.EMPTY;
        this.shouldLayerFirst = this.renderInfo.shouldLayerFirst();
        this.armor = (disabledLayers & 0b1) == 0;
        this.head = (disabledLayers & 0b10) == 0;
        this.mainhand = (disabledLayers & 0b100) == 0;
        this.offhand = (disabledLayers & 0b1000) == 0;
        this.elytra = (disabledLayers & 0b10000) == 0;
        this.arrows = (disabledLayers & 0b100000) == 0;
        this.beeStings = (disabledLayers & 0b1000000) == 0;
    }

    public void setRenderInfo(EntityType<?> type, RendererInformation info) {
        this.renderInfo = info;
        if (shouldLayerFirst != info.shouldLayerFirst()) {
            this.shouldLayerFirst = info.shouldLayerFirst();
            this.layerData.updateLayerLocation(shouldLayerFirst);
        }
    }

    public boolean shouldLayerFirst() {
        return shouldLayerFirst;
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, T entity, float animationPosition, float animationSpeed, float partialTick, float bob, float yRotation, float xRotation) {
        this.modelData.prepare(getParentModel(), entity, animationPosition, animationSpeed, partialTick, bob, yRotation, xRotation);
        if (armor && renderInfo.showArmor()) this.renderArmor(matrixStack, buffer, packedLight, entity, animationPosition, animationSpeed, partialTick, bob, yRotation, xRotation);
        if (mainhand && renderInfo.showMainhandItem()) this.renderMainhandItem();
        if (offhand && renderInfo.showOffhandItem()) this.renderOffhandItem();
        if (arrows && renderInfo.showArrows()) this.renderArrows(matrixStack, buffer, packedLight, entity, animationPosition, animationSpeed, partialTick, bob, yRotation, xRotation);
        if (head && renderInfo.showHead()) this.renderHead(matrixStack, buffer, packedLight, entity, animationPosition, animationSpeed, partialTick, bob, yRotation, xRotation);
        if (elytra && renderInfo.showElytra()) this.renderElytra(matrixStack, buffer, packedLight, entity, animationPosition, animationSpeed, partialTick, bob, yRotation, xRotation);
        if (beeStings && renderInfo.showBeeStings()) this.renderBeeStings(matrixStack, buffer, packedLight, entity, animationPosition, animationSpeed, partialTick, bob, yRotation, xRotation);
    }

    protected void renderArmor(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, T entity, float animationPosition, float animationSpeed, float partialTick, float bob, float yRotation, float xRotation) {
        this.modelData.prepareArmor(getParentModel(), entity, animationPosition, animationSpeed, partialTick, bob, yRotation, xRotation);
        this.renderArmorPiece(matrixStack, buffer, entity, EquipmentSlotType.HEAD, packedLight);
        this.renderArmorPiece(matrixStack, buffer, entity, EquipmentSlotType.CHEST, packedLight);
        this.renderArmorPiece(matrixStack, buffer, entity, EquipmentSlotType.LEGS, packedLight);
        this.renderArmorPiece(matrixStack, buffer, entity, EquipmentSlotType.FEET, packedLight);
    }

    // TODO: Render Mainhand Item
    protected void renderMainhandItem() {}

    // TODO: Render Offhand Item
    protected void renderOffhandItem() {}

    protected void renderArrows(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, T entity, float animationPosition, float animationSpeed, float partialTick, float bob, float yRotation, float xRotation) {
        this.renderStuckInBody(matrixStack, buffer, packedLight, entity, animationPosition, animationSpeed, partialTick, bob, yRotation, xRotation, T::getArrowCount,
                (matrixStackIn, bufferIn, packedLightIn, entityIn, xRandomIn, yRandomIn, zRandomIn, partialTickIn) -> {
                    ArrowEntity arrow = new ArrowEntity(entityIn.level, entityIn.getX(), entityIn.getY(), entityIn.getZ());
                    arrow.yRotO = arrow.yRot = (float) (Math.atan2(xRandomIn, zRandomIn) * 180f / Math.PI);
                    arrow.xRotO = arrow.xRot = (float) (Math.atan2(yRandomIn, MathHelper.sqrt(xRandomIn * xRandomIn + zRandomIn * zRandomIn)) * 180f / Math.PI);
                    this.manager.render(arrow, 0d, 0d, 0d, 0f, partialTickIn, matrixStackIn, bufferIn, packedLightIn);
                });
    }

    protected void renderHead(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, T entity, float animationPosition, float animationSpeed, float partialTick, float bob, float yRotation, float xRotation) {
        ItemStack stack = entity.getItemBySlot(EquipmentSlotType.HEAD);
        if (!stack.isEmpty()) {
            Item item = stack.getItem();
            HeadTransformationMatrix transforms = this.renderInfo.getHeadTransforms();
            boolean baby = entity.isBaby();
            matrixStack.pushPose();
            this.modelData.translateAndRotateHead(matrixStack, baby);
            transforms.applyTransforms(matrixStack, baby);
            if (item instanceof BlockItem && ((BlockItem) item).getBlock() instanceof AbstractSkullBlock) {
                matrixStack.scale(1.1875f, -1.1875f, -1.1875f);
                matrixStack.translate(-0.5d, 0d, -0.5d);
                transforms.applySkullTransforms(matrixStack, baby);
                GameProfile profile = null;
                if (stack.hasTag()) {
                    CompoundNBT nbt = stack.getTag();
                    if (nbt.contains("SkullOwner", Constants.NBT.TAG_COMPOUND))
                        profile = NBTUtil.readGameProfile(nbt.getCompound("SkullOwner"));
                    else if (nbt.contains("SkullOwner", Constants.NBT.TAG_STRING)) {
                        String ownerString = nbt.getString("SkullOwner");
                        if (!StringUtils.isBlank(ownerString)) {
                            profile = SkullTileEntity.updateGameprofile(new GameProfile(null, ownerString));
                            nbt.put("SkullOwner", NBTUtil.writeGameProfile(new CompoundNBT(), profile));
                        }
                    }
                }
                SkullTileEntityRenderer.renderSkull(null, 180f, ((AbstractSkullBlock) ((BlockItem) item).getBlock()).getType(), profile, animationPosition, matrixStack, buffer, packedLight);
            } else if (!(item instanceof ArmorItem) || ((ArmorItem) item).getSlot() != EquipmentSlotType.HEAD) {
                matrixStack.translate(0d, -0.25d, 0d);
                matrixStack.mulPose(Vector3f.YP.rotationDegrees(180f));
                matrixStack.scale(0.625f, -0.625f, -0.625f);
                transforms.applyItemTransforms(matrixStack, baby);
                Minecraft.getInstance().getItemInHandRenderer().renderItem(entity, stack, TransformType.HEAD, false, matrixStack, buffer, packedLight);
            }
            matrixStack.popPose();
        }
    }

    protected void renderElytra(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, T entity, float animationPosition, float animationSpeed, float partialTick, float bob, float yRotation, float xRotation) {
        ItemStack stack = entity.getItemBySlot(EquipmentSlotType.CHEST);
        if (stack.getItem() == Items.ELYTRA) { // TODO: Remove hardcode
            EntityModel<T> elytraModel = this.modelData.getElytraModel(); // TODO: Potentially expand
            ResourceLocation loc = WINGS_LOCATION; // TODO: Remove hardcode
            matrixStack.pushPose();
            this.renderInfo.getElytraTransforms().apply(matrixStack, entity.isBaby());
            this.getParentModel().copyPropertiesTo(elytraModel);
            elytraModel.setupAnim(entity, animationPosition, animationSpeed, bob, yRotation, xRotation);
            elytraModel.renderToBuffer(matrixStack, ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(loc), false, stack.hasFoil()), packedLight, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f); // TODO: Maybe want to expand
            matrixStack.popPose();
        }
    }

    protected void renderBeeStings(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, T entity, float animationPosition, float animationSpeed, float partialTick, float bob, float yRotation, float xRotation) {
        this.renderStuckInBody(matrixStack, buffer, packedLight, entity, animationPosition, animationSpeed, partialTick, bob, yRotation, xRotation, T::getStingerCount,
                (matrixStackIn, bufferIn, packedLightIn, entityIn, xRandomIn, yRandomIn, zRandomIn, partialTickIn) -> {
                    matrixStackIn.mulPose(Vector3f.YP.rotationDegrees((float) (Math.atan2(xRandomIn, zRandomIn) * 180f / Math.PI) - 90f));
                    matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees((float) (Math.atan2(yRandomIn, MathHelper.sqrt(xRandomIn * xRandomIn + zRandomIn * zRandomIn)) * 180f / Math.PI)));
                    matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(45f));
                    matrixStackIn.scale(0.03125f, 0.03125f, 0.03125f);
                    matrixStackIn.translate(2.5d, 0d, 0d);
                    IVertexBuilder builder = bufferIn.getBuffer(RenderType.entityCutoutNoCull(BEE_STINGER_LOCATION));
                    for (int i = 0; i < 4; ++i) {
                        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(90f));
                        MatrixStack.Entry entry = matrixStackIn.last();
                        Matrix4f pose = entry.pose();
                        Matrix3f normal = entry.normal();
                        builder.vertex(pose, -4.5f, -1f, 0f).color(255, 255, 255, 255).uv(0f, 0f).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLightIn).normal(normal, 0f, 1f, 0f).endVertex();
                        builder.vertex(pose, 4.5f, -1f, 0f).color(255, 255, 255, 255).uv(0.125f, 0f).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLightIn).normal(normal, 0f, 1f, 0f).endVertex();
                        builder.vertex(pose, 4.5f, 1f, 0f).color(255, 255, 255, 255).uv(0.125f, 0.0625f).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLightIn).normal(normal, 0f, 1f, 0f).endVertex();
                        builder.vertex(pose, -4.5f, 1f, 0f).color(255, 255, 255, 255).uv(0f, 0.0625f).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLightIn).normal(normal, 0f, 1f, 0f).endVertex();
                    }
                });
    }

    protected void renderStuckInBody(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, T entity, float animationPosition, float animationSpeed, float partialTick, float bob, float yRotation, float xRotation, Function<T, Integer> numStuck, IStuckInBody<T> stuckInBody) {
        int stuck = numStuck.apply(entity);
        Random random = new Random(entity.getId());
        for (int rendered = 0; rendered < stuck; ++rendered) {
            matrixStack.pushPose();
            ModelRenderer modelRenderer = this.modelData.getRandomModelPart(random);
            ModelRenderer.ModelBox modelBox = modelRenderer.getRandomCube(random);
            modelRenderer.translateAndRotate(matrixStack);
            float xRandom = random.nextFloat();
            float yRandom = random.nextFloat();
            float zRandom = random.nextFloat();
            matrixStack.translate(MathHelper.lerp(xRandom, modelBox.minX, modelBox.maxX) / 16f,
                    MathHelper.lerp(yRandom, modelBox.minY, modelBox.maxY) / 16f,
                    MathHelper.lerp(zRandom, modelBox.minZ, modelBox.maxZ) / 16f);
            stuckInBody.renderStuckItem(matrixStack, buffer, packedLight, entity, -1f * (xRandom * 2f - 1f), -1f * (yRandom * 2f - 1f), -1f * (zRandom * 2f - 1f), partialTick);
            matrixStack.popPose();
        }
    }

    protected void renderArmorPiece(MatrixStack matrixStack, IRenderTypeBuffer buffer, T entity, EquipmentSlotType slot, int packedLight) {
        ItemStack stack = entity.getItemBySlot(slot);
        if (stack.getItem() instanceof ArmorItem) {
            ArmorItem armor = (ArmorItem) stack.getItem();
            if (armor.getSlot() == slot && this.renderInfo.getArmorMaterials().contains(CACHE.retrieveArmorMaterial(armor.getMaterial().getName()))) {
                A model = this.getArmorModel(entity, stack, slot);
                this.modelData.setPartVisibility(model, slot);
                boolean foil = stack.hasFoil();
                if (armor instanceof IDyeableArmorItem) {
                    int color = ((IDyeableArmorItem) armor).getColor(stack);
                    float red = (color >> 16 & 255) / 255f;
                    float green = (color >> 8 & 255) / 255f;
                    float blue = (color & 255) / 255f;
                    this.renderArmorModel(matrixStack, buffer, packedLight, foil, model, red, green, blue, this.getArmorTexture(entity, stack, slot, null));
                    this.renderArmorModel(matrixStack, buffer, packedLight, foil, model, 1f, 1f, 1f, this.getArmorTexture(entity, stack, slot, "overlay"));
                } else this.renderArmorModel(matrixStack, buffer, packedLight, foil, model, 1f, 1f, 1f, this.getArmorTexture(entity, stack, slot, null));
            }
        }
    }

    // TODO: Expand more
    protected A getArmorModel(T entity, ItemStack stack, EquipmentSlotType slot) {
        return slot == EquipmentSlotType.LEGS ? this.modelData.getInnerModel() : this.modelData.getOuterModel();
    }

    // TODO: Expand more
    protected ResourceLocation getArmorTexture(T entity, ItemStack stack, EquipmentSlotType slot, @Nullable String suffix) {
        String materialName = ((ArmorItem) stack.getItem()).getMaterial().getName();
        String domain = "minecraft";
        int index = materialName.indexOf(':');
        if (index != -1) {
            domain = materialName.substring(0, index);
            materialName = materialName.substring(index + 1);
        }
        ResourceLocation registryName = entity.getType().getRegistryName();
        String textureString = String.format("%s:textures/models/armor/%s/%s/%s_layer_%d%s.png", domain, registryName.getNamespace(), registryName.getPath(), materialName, slot == EquipmentSlotType.LEGS ? 2 : 1, suffix == null ? "" : String.format("_%s", suffix));
        return CACHE.retrieveArmorLocation(textureString);
    }

    // TODO: Maybe want to expand
    protected void renderArmorModel(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, boolean hasFoil, A model, float red, float green, float blue, ResourceLocation texture) {
        model.renderToBuffer(matrixStack, ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(texture), false, hasFoil), packedLight, OverlayTexture.NO_OVERLAY, red, green, blue, 1f);
    }

    @FunctionalInterface
    protected interface IStuckInBody<T extends LivingEntity> {
        void renderStuckItem(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, T entity, float xRandom, float yRandom, float zRandom, float partialTick);
    }
}
