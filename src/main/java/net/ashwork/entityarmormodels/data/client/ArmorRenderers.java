/*
 * Entity Armor Models
 * Copyright (c) 2021-2021 ChampionAsh5357.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.ashwork.entityarmormodels.data.client;

import java.util.function.BiConsumer;

import net.ashwork.entityarmormodels.client.renderer.RendererInformation;
import net.minecraft.data.DataGenerator;
import net.minecraft.entity.EntityType;

public class ArmorRenderers extends ArmorRendererProvider {

    public ArmorRenderers(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildArmorRenderers(BiConsumer<EntityType<?>, RendererInformation> consumer) {
        RendererInformation.Builder.of(EntityType.CREEPER)
        .layerArmorFirst()
        .showHead()
        .showArmor().addVanillaArmors()
        .showElytra().transformElytra()
        .translate(-0.125f, 0.1875f, 0f).leftRotate(10f, 0f, 0f).scale(0.75f)
        .apply()
        .showArrows().showBeeStings().build(consumer);
        RendererInformation.Builder.of(EntityType.PIG)
        .showHead()
        .transformHead()
        .main()
        .translate(0.03125f, 0.25f, -0.25f)
        .scale(1.0625f)
        .apply()
        .skull()
        .translate(0f, -0.0625f, 0f)
        .apply()
        .apply()
        .showArmor().addVanillaArmors()
        .showElytra().transformElytra()
        .translate(-0.125f, -0.125f, -0.5f).leftRotate(80f, 0f, 0f).scale(0.75f)
        .childTranslate(0f, -0.4375f, -0.5625f).apply()
        .showArrows().showBeeStings().build(consumer);
    }
}
