/*
 * Entity Armor Models
 * Copyright (c) 2021-2021 ChampionAsh5357.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.ashwork.entityarmormodels;

import org.apache.commons.lang3.tuple.Pair;

import net.ashwork.entityarmormodels.client.ClientReference;
import net.ashwork.entityarmormodels.util.ISidedReference;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.FMLNetworkConstants;

@Mod(EntityArmorModels.ID)
public final class EntityArmorModels {

    public static final String ID = "entityarmormodels";
    private final ISidedReference sidedReference;

    public EntityArmorModels() {
        this.sidedReference = DistExecutor.safeCallWhenOn(Dist.CLIENT, () -> ClientReference::new);
        IEventBus mod = FMLJavaModLoadingContext.get().getModEventBus();

        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.DISPLAYTEST,
                () -> Pair.of(() -> FMLNetworkConstants.IGNORESERVERONLY, (a, b) -> true));
        this.sidedReference.setup(mod, MinecraftForge.EVENT_BUS);
    }
}
