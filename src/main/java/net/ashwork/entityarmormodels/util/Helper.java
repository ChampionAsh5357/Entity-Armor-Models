/*
 * Entity Armor Models
 * Copyright (c) 2021-2021 ChampionAsh5357.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.ashwork.entityarmormodels.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.fml.InterModComms.IMCMessage;

public class Helper {

    public static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
    public static final Vector3f ORIGIN_VEC3F = new Vector3f(0f, 0f, 0f);
    public static final Vector3f ONE_VEC3F = new Vector3f(1f, 1f, 1f);
    public static final Quaternion ORIGIN_QUAT = new Quaternion(0f, 0f, 0f, true);

    public static <T> Set<T> toSet(Collection<T> collection) {
        return new HashSet<>(collection);
    }

    public static <T> List<T> toList(Collection<T> collection) {
        return new ArrayList<>(collection);
    }

    public static <T> T getMessageData(IMCMessage msg) {
        Supplier<T> sup = msg.getMessageSupplier();
        return sup.get();
    }
}
