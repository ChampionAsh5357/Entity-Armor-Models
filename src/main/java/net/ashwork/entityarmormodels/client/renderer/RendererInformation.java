/*
 * Entity Armor Models
 * Copyright (c) 2021-2021 ChampionAsh5357.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.ashwork.entityarmormodels.client.renderer;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.ashwork.entityarmormodels.client.renderer.entity.EntityTransformationMatrix;
import net.ashwork.entityarmormodels.client.renderer.entity.HeadTransformationMatrix;
import net.ashwork.entityarmormodels.util.Helper;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.TransformationMatrix;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.common.model.TransformationHelper;

public class RendererInformation {

    public static final RendererInformation EMPTY = new RendererInformation();
    public static final Codec<RendererInformation> CODEC = RecordCodecBuilder.create(builder ->
    builder.group(ResourceLocation.CODEC.listOf().fieldOf("armor_materials").xmap(Helper::toSet, Helper::toList).forGetter(RendererInformation::getArmorMaterials),
            Codec.BOOL.optionalFieldOf("layer_first", false).forGetter(RendererInformation::shouldLayerFirst),
            Codec.BOOL.optionalFieldOf("armor", false).forGetter(RendererInformation::showArmor),
            Codec.BOOL.optionalFieldOf("head", false).forGetter(RendererInformation::showHead),
            Codec.BOOL.optionalFieldOf("mainhand", false).forGetter(RendererInformation::showMainhandItem),
            Codec.BOOL.optionalFieldOf("offhand", false).forGetter(RendererInformation::showOffhandItem),
            Codec.BOOL.optionalFieldOf("elytra", false).forGetter(RendererInformation::showElytra),
            Codec.BOOL.optionalFieldOf("arrows", false).forGetter(RendererInformation::showArrows),
            Codec.BOOL.optionalFieldOf("bee_stings", false).forGetter(RendererInformation::showBeeStings),
            EntityTransformationMatrix.CODEC.optionalFieldOf("elytra_transforms", EntityTransformationMatrix.IDENTITY).forGetter(RendererInformation::getElytraTransforms),
            HeadTransformationMatrix.CODEC.optionalFieldOf("head_transforms", HeadTransformationMatrix.IDENTITY).forGetter(RendererInformation::getHeadTransforms))
    .apply(builder, RendererInformation::new));

    private final Set<ResourceLocation> armorMaterials;
    private boolean shouldLayerFirst, armor, head, mainhand, offhand, elytra, arrows, beeStings;
    private EntityTransformationMatrix elytraTransforms;
    private HeadTransformationMatrix headTransforms;

    public RendererInformation() {
        this(Collections.emptySet(), false, false, false, false, false, false, false, false, EntityTransformationMatrix.IDENTITY, HeadTransformationMatrix.IDENTITY);
    }

    public RendererInformation(Set<ResourceLocation> armorMaterials, boolean shouldLayerFirst, boolean armor, boolean head, boolean mainhand, boolean offhand, boolean elytra, boolean arrows, boolean beeStings, EntityTransformationMatrix elytraTransforms, HeadTransformationMatrix headTransforms) {
        this.armorMaterials = new HashSet<>(armorMaterials);
        this.shouldLayerFirst = shouldLayerFirst;
        this.armor = armor;
        this.head = head;
        this.mainhand = mainhand;
        this.offhand = offhand;
        this.elytra = elytra;
        this.arrows = arrows;
        this.beeStings = beeStings;
        this.elytraTransforms = elytraTransforms;
        this.headTransforms = headTransforms;
    }

    public void merge(RendererInformation info, boolean replace) {
        boolean invReplace = !replace;
        if (replace) this.armorMaterials.clear();
        this.armorMaterials.addAll(info.armorMaterials);
        this.shouldLayerFirst = (this.shouldLayerFirst & invReplace) | info.shouldLayerFirst;
        this.armor = (this.armor & invReplace) | info.armor;
        this.head = (this.head & invReplace) | info.head;
        this.mainhand = (this.mainhand & invReplace) | info.mainhand;
        this.offhand = (this.offhand & invReplace) | info.offhand;
        this.elytra = (this.elytra & invReplace) | info.elytra;
        this.arrows = (this.arrows & invReplace) | info.arrows;
        this.beeStings = (this.beeStings & invReplace) | info.beeStings;
        this.elytraTransforms = this.elytraTransforms.replace(info.elytraTransforms);
        this.headTransforms = this.headTransforms.replace(info.headTransforms);
    }

    public Set<ResourceLocation> getArmorMaterials() {
        return Collections.unmodifiableSet(this.armorMaterials);
    }

    public boolean shouldLayerFirst() {
        return this.shouldLayerFirst;
    }

    public boolean showArmor() {
        return this.armor;
    }

    public boolean showHead() {
        return this.head;
    }

    public boolean showMainhandItem() {
        return this.mainhand;
    }

    public boolean showOffhandItem() {
        return this.offhand;
    }

    public boolean showElytra() {
        return this.elytra;
    }

    public boolean showArrows() {
        return this.arrows;
    }

    public boolean showBeeStings() {
        return this.beeStings;
    }

    public EntityTransformationMatrix getElytraTransforms() {
        return this.elytraTransforms;
    }

    public HeadTransformationMatrix getHeadTransforms() {
        return headTransforms;
    }

    public static class Builder {

        private final EntityType<?> type;
        private final Set<ResourceLocation> armorMaterials;
        private boolean shouldLayerFirst, armor, head, mainhand, offhand, elytra, arrows, beeStings;
        private EntityTransformationMatrix elytraTransforms;
        private HeadTransformationMatrix headTransforms;

        public static Builder of(EntityType<?> type) {
            return new Builder(type);
        }

        private Builder(EntityType<?> type) {
            this.type = type;
            this.armorMaterials = new HashSet<>();
            this.elytraTransforms = EntityTransformationMatrix.IDENTITY;
            this.headTransforms = HeadTransformationMatrix.IDENTITY;
        }

        public Builder addVanillaArmors() {
            Arrays.stream(ArmorMaterial.values()).forEach(this::addArmorMaterial);
            return this;
        }

        public Builder addArmorMaterial(IArmorMaterial material) {
            return this.addArmorMaterial(material.getName());
        }

        public Builder addArmorMaterial(String material) {
            return this.addArmorMaterial(new ResourceLocation(material));
        }

        public Builder addArmorMaterial(ResourceLocation material) {
            if (!this.armorMaterials.add(material))
                throw new IllegalArgumentException("The armor material " + material + " has already been added to " + type.getRegistryName());
            return this;
        }

        public Builder layerArmorFirst() {
            this.shouldLayerFirst = true;
            return this;
        }

        public Builder showArmor() {
            this.armor = true;
            return this;
        }

        public Builder showHead() {
            this.head = true;
            return this;
        }

        public Builder showMainhandItem() {
            this.mainhand = true;
            return this;
        }

        public Builder showOffhandItem() {
            this.offhand = true;
            return this;
        }

        public Builder showElytra() {
            this.elytra = true;
            return this;
        }

        public Builder showArrows() {
            this.arrows = true;
            return this;
        }

        public Builder showBeeStings() {
            this.beeStings = true;
            return this;
        }

        public Transforms<Builder> transformElytra() {
            return new Transforms<>(this, matrix -> this.elytraTransforms = matrix);
        }

        public HeadTransforms transformHead() {
            return new HeadTransforms(matrix -> this.headTransforms = matrix);
        }

        public void build(BiConsumer<EntityType<?>, RendererInformation> consumer) {
            consumer.accept(type, new RendererInformation(armorMaterials, shouldLayerFirst, armor, head, mainhand, offhand, elytra, arrows, beeStings, elytraTransforms, headTransforms));
        }

        @Override
        public String toString() {
            return "RendererInformation$Builder[" + type.getRegistryName() + "]";
        }

        public class HeadTransforms {

            private final Consumer<HeadTransformationMatrix> cons;
            private EntityTransformationMatrix transforms, skullTransforms, itemTransforms;

            private HeadTransforms(Consumer<HeadTransformationMatrix> cons) {
                this.cons = cons;
                this.transforms = EntityTransformationMatrix.IDENTITY;
                this.skullTransforms = EntityTransformationMatrix.IDENTITY;
                this.itemTransforms = EntityTransformationMatrix.IDENTITY;
            }

            public Transforms<HeadTransforms> main() {
                return new Transforms<>(this, matrix -> this.transforms = matrix);
            }

            public Transforms<HeadTransforms> skull() {
                return new Transforms<>(this, matrix -> this.skullTransforms = matrix);
            }

            public Transforms<HeadTransforms> item() {
                return new Transforms<>(this, matrix -> this.itemTransforms = matrix);
            }

            public Builder apply() {
                cons.accept(new HeadTransformationMatrix(transforms, skullTransforms, itemTransforms));
                return Builder.this;
            }
        }

        public class Transforms<T> {

            private final T returnInstance;
            private final Consumer<EntityTransformationMatrix> cons;
            private Vector3f translation, leftRotation, scale, rightRotation, childTranslation, childLeftRotation, childScale, childRightRotation;

            private Transforms(T returnInstance, Consumer<EntityTransformationMatrix> cons) {
                this.returnInstance = returnInstance;
                this.cons = cons;
                this.translation = Helper.ORIGIN_VEC3F;
                this.leftRotation = Helper.ORIGIN_VEC3F;
                this.scale = Helper.ONE_VEC3F;
                this.rightRotation = Helper.ORIGIN_VEC3F;
                this.childTranslation = Helper.ORIGIN_VEC3F;
                this.childLeftRotation = Helper.ORIGIN_VEC3F;
                this.childScale = Helper.ONE_VEC3F;
                this.childRightRotation = Helper.ORIGIN_VEC3F;
            }

            public Transforms<T> translate(float x, float y, float z) {
                this.translation = new Vector3f(x, y, z);
                return this;
            }

            public Transforms<T> leftRotate(float x, float y, float z) {
                this.leftRotation = new Vector3f(x, y, z);
                return this;
            }

            public Transforms<T> scale(float s) {
                return this.scale(s, s, s);
            }

            public Transforms<T> scale(float x, float y, float z) {
                this.scale = new Vector3f(x, y, z);
                return this;
            }

            public Transforms<T> rightRotate(float x, float y, float z) {
                this.rightRotation = new Vector3f(x, y, z);
                return this;
            }

            public Transforms<T> childTranslate(float x, float y, float z) {
                this.childTranslation = new Vector3f(x, y, z);
                return this;
            }

            public Transforms<T> childLeftRotate(float x, float y, float z) {
                this.childLeftRotation = new Vector3f(x, y, z);
                return this;
            }

            public Transforms<T> childScale(float s) {
                return this.childScale(s, s, s);
            }

            public Transforms<T> childScale(float x, float y, float z) {
                this.childScale = new Vector3f(x, y, z);
                return this;
            }

            public Transforms<T> childRightRotate(float x, float y, float z) {
                this.childRightRotation = new Vector3f(x, y, z);
                return this;
            }

            public T apply() {
                cons.accept(new EntityTransformationMatrix(new TransformationMatrix(translation, TransformationHelper.quatFromXYZ(leftRotation, true), scale, TransformationHelper.quatFromXYZ(rightRotation, true)),
                        new TransformationMatrix(childTranslation, TransformationHelper.quatFromXYZ(childLeftRotation, true), childScale, TransformationHelper.quatFromXYZ(childRightRotation, true))));
                return returnInstance;
            }
        }
    }
}
