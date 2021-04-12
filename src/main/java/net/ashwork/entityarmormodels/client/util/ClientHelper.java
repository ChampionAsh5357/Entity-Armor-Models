/*
 * Entity Armor Models
 * Copyright (c) 2021-2021 ChampionAsh5357.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.ashwork.entityarmormodels.client.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;

import net.ashwork.entityarmormodels.util.Helper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.TransformationMatrix;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.common.model.TransformationHelper;

public class ClientHelper {

    private static final Gson GSON_CODEC_HELPER = new GsonBuilder()
            .registerTypeAdapter(TransformationMatrix.class, new TransformationHelper.Deserializer())
            .create();

    public static final Codec<TransformationMatrix> TRANSFORMATION_MATRIX_CODEC = Codec.PASSTHROUGH.comapFlatMap(dyn -> {
        JsonElement element = dyn.convert(JsonOps.INSTANCE).getValue();
        try {
            TransformationMatrix matrix = GSON_CODEC_HELPER.fromJson(element, TransformationMatrix.class);
            return DataResult.success(matrix, Lifecycle.stable());
        } catch (JsonSyntaxException e) {
            return DataResult.error("A syntax exception has occured: " + element, Lifecycle.stable());
        }
    }, matrix -> {
        JsonObject obj = new JsonObject();
        if (!matrix.getTranslation().equals(Helper.ORIGIN_VEC3F))
            obj.add("translation", toJsonArray(matrix.getTranslation()));
        if (!matrix.getLeftRotation().equals(Helper.ORIGIN_QUAT))
            obj.add("rotation", toJsonArray(matrix.getLeftRotation()));
        if (!matrix.getScale().equals(Helper.ONE_VEC3F))
            obj.add("scale", toJsonArray(matrix.getScale()));
        if (!matrix.getRightRot().equals(Helper.ORIGIN_QUAT))
            obj.add("post-rotation", toJsonArray(matrix.getRightRot()));

        return new Dynamic<>(JsonOps.INSTANCE, obj);
    });

    private static JsonArray toJsonArray(Vector3f vec) {
        JsonArray array = new JsonArray();
        array.add(vec.x());
        array.add(vec.y());
        array.add(vec.z());
        return array;
    }

    private static JsonArray toJsonArray(Quaternion quat) {
        JsonArray array = new JsonArray();
        array.add(quat.i());
        array.add(quat.j());
        array.add(quat.k());
        array.add(quat.r());
        return array;
    }
}
