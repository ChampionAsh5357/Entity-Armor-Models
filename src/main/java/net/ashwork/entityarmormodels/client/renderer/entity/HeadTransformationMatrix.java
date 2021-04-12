/*
 * Entity Armor Models
 * Copyright (c) 2021-2021 ChampionAsh5357.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.ashwork.entityarmormodels.client.renderer.entity;

import java.util.Objects;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class HeadTransformationMatrix {

    public static final HeadTransformationMatrix IDENTITY = new HeadTransformationMatrix(EntityTransformationMatrix.IDENTITY, EntityTransformationMatrix.IDENTITY, EntityTransformationMatrix.IDENTITY);
    public static final Codec<HeadTransformationMatrix> CODEC = RecordCodecBuilder.create(builder ->
    builder.group(EntityTransformationMatrix.CODEC.optionalFieldOf("main", EntityTransformationMatrix.IDENTITY).forGetter(HeadTransformationMatrix::getTransforms),
            EntityTransformationMatrix.CODEC.optionalFieldOf("skull", EntityTransformationMatrix.IDENTITY).forGetter(HeadTransformationMatrix::getSkullTransforms),
            EntityTransformationMatrix.CODEC.optionalFieldOf("item", EntityTransformationMatrix.IDENTITY).forGetter(HeadTransformationMatrix::getItemTransforms))
    .apply(builder, HeadTransformationMatrix::new));
    private final EntityTransformationMatrix transforms, skullTransforms, itemTransforms;

    public HeadTransformationMatrix(EntityTransformationMatrix transforms, EntityTransformationMatrix skullTransforms, EntityTransformationMatrix itemTransforms) {
        this.transforms = transforms;
        this.skullTransforms = skullTransforms;
        this.itemTransforms = itemTransforms;
    }

    public EntityTransformationMatrix getTransforms() {
        return transforms;
    }

    public EntityTransformationMatrix getSkullTransforms() {
        return skullTransforms;
    }

    public EntityTransformationMatrix getItemTransforms() {
        return itemTransforms;
    }

    public HeadTransformationMatrix replace(HeadTransformationMatrix matrix) {
        return new HeadTransformationMatrix(this.transforms.replace(matrix.transforms),
                this.skullTransforms.replace(matrix.skullTransforms),
                this.itemTransforms.replace(matrix.itemTransforms));
    }

    public void applyTransforms(MatrixStack matrixStack, boolean isChild) {
        this.transforms.apply(matrixStack, isChild);
    }

    public void applySkullTransforms(MatrixStack matrixStack, boolean isChild) {
        this.skullTransforms.apply(matrixStack, isChild);
    }

    public void applyItemTransforms(MatrixStack matrixStack, boolean isChild) {
        this.itemTransforms.apply(matrixStack, isChild);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.transforms, this.skullTransforms, this.itemTransforms);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        else if (obj != null && this.getClass() == obj.getClass()) {
            HeadTransformationMatrix matrix = (HeadTransformationMatrix) obj;
            return this.transforms.equals(matrix.transforms) &&
                    this.skullTransforms.equals(matrix.skullTransforms) &&
                    this.itemTransforms.equals(matrix.itemTransforms);
        }
        else return false;
    }
}
