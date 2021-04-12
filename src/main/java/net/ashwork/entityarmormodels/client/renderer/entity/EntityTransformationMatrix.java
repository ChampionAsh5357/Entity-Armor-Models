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

import net.ashwork.entityarmormodels.client.util.ClientHelper;
import net.minecraft.util.math.vector.TransformationMatrix;

public class EntityTransformationMatrix {

    public static final EntityTransformationMatrix IDENTITY = new EntityTransformationMatrix(TransformationMatrix.identity(), TransformationMatrix.identity());
    public static final Codec<EntityTransformationMatrix> CODEC = RecordCodecBuilder.create(builder ->
    builder.group(ClientHelper.TRANSFORMATION_MATRIX_CODEC.optionalFieldOf("main", TransformationMatrix.identity()).forGetter(EntityTransformationMatrix::getTransforms),
            ClientHelper.TRANSFORMATION_MATRIX_CODEC.optionalFieldOf("child", TransformationMatrix.identity()).forGetter(EntityTransformationMatrix::getChildTransforms))
    .apply(builder, EntityTransformationMatrix::new));
    private final TransformationMatrix transforms, childTransforms;

    public EntityTransformationMatrix(TransformationMatrix transforms, TransformationMatrix childTransforms) {
        this.transforms = transforms;
        this.childTransforms = childTransforms;
    }

    public TransformationMatrix getTransforms() {
        return transforms;
    }

    public TransformationMatrix getChildTransforms() {
        return childTransforms;
    }

    public EntityTransformationMatrix replace(EntityTransformationMatrix matrix) {
        return new EntityTransformationMatrix(matrix.transforms.isIdentity() ? this.transforms : matrix.transforms,
                matrix.childTransforms.isIdentity() ? this.childTransforms : matrix.childTransforms);
    }

    public void apply(MatrixStack matrixStack, boolean isChild) {
        apply(transforms, matrixStack);
        if (isChild) apply(childTransforms, matrixStack);
    }

    private static void apply(TransformationMatrix transforms, MatrixStack matrixStack) {
        MatrixStack transformsStack = new MatrixStack();
        if (!transforms.isIdentity()) transforms.push(transformsStack);
        if (!transformsStack.clear()) {
            matrixStack.last().pose().multiply(transformsStack.last().pose());
            matrixStack.last().normal().mul(transformsStack.last().normal());
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.transforms.hashCode(), this.childTransforms.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        else if (obj != null && this.getClass() == obj.getClass()) {
            EntityTransformationMatrix matrix = (EntityTransformationMatrix) obj;
            return this.transforms.equals(matrix.transforms) && this.childTransforms.equals(matrix.childTransforms);
        }
        else return false;
    }
}
