/*
 * Entity Armor Models
 * Copyright (c) 2021-2021 ChampionAsh5357.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.ashwork.entityarmormodels.client.util;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.util.ResourceLocation;

public class ClientCache {

    private final Map<String, ResourceLocation> armorLocations;
    private final Map<String, ResourceLocation> armorMaterials;

    public ClientCache() {
        this.armorLocations = new HashMap<>();
        this.armorMaterials = new HashMap<>();
    }

    public ResourceLocation retrieveArmorLocation(String loc) {
        return this.armorLocations.computeIfAbsent(loc, ResourceLocation::new);
    }

    public ResourceLocation retrieveArmorMaterial(String loc) {
        return this.armorMaterials.computeIfAbsent(loc, ResourceLocation::new);
    }
}
