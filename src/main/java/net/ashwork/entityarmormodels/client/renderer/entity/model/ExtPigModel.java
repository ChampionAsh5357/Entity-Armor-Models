/*
 * Entity Armor Models
 * Copyright (c) 2021-2021 ChampionAsh5357.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.ashwork.entityarmormodels.client.renderer.entity.model;

import net.minecraft.entity.LivingEntity;

public class ExtPigModel<T extends LivingEntity> extends ExtQuadrupedModel<T> {

    public ExtPigModel(float inflation) {
        this(inflation, 64, 32);
    }

    protected ExtPigModel(float inflation, int texWidth, int texHeight) {
        super(inflation, texWidth, texHeight, false, 4f, 4f, 2f, 2f, 24f, 6f);
        this.head.texOffs(16, 16).addBox(-2.0F, 0.0F, -9.0F, 4.0F, 3.0F, 1.0F, inflation);
    }
}
