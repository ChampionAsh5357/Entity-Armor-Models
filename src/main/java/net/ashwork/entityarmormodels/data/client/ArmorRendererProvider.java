/*
 * Entity Armor Models
 * Copyright (c) 2021-2021 ChampionAsh5357.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.ashwork.entityarmormodels.data.client;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonSyntaxException;
import com.mojang.serialization.JsonOps;

import net.ashwork.entityarmormodels.client.renderer.ArmorRendererManager;
import net.ashwork.entityarmormodels.client.renderer.RendererInformation;
import net.ashwork.entityarmormodels.util.Helper;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;

public abstract class ArmorRendererProvider implements IDataProvider {

    private static final Logger LOGGER = LogManager.getLogger();
    protected final DataGenerator generator;

    public ArmorRendererProvider(DataGenerator generator) {
        this.generator = generator;
    }

    @Override
    public void run(DirectoryCache cache) throws IOException {
        Path mainOutput = this.generator.getOutputFolder().resolve("assets/");
        Set<ResourceLocation> types = new HashSet<>();
        buildArmorRenderers((type, info) -> {
            ResourceLocation name = type.getRegistryName();
            if (!types.add(name))
                throw new IllegalStateException("Duplicate armor renderer information " + name);
            else {
                try {
                    IDataProvider.save(Helper.GSON,
                            cache,
                            RendererInformation.CODEC.encodeStart(JsonOps.INSTANCE, info)
                            .resultOrPartial(Util.prefix("Armor renderer information for {} could not be encoded: ", str -> LOGGER.error(str, name)))
                            .orElseThrow(() -> new JsonSyntaxException("Armor renderer information for " + name + " could not be encoded")),
                            mainOutput.resolve(name.getNamespace() + "/" + ArmorRendererManager.NAME + "/" + name.getPath() + ".json"));
                } catch (IOException e) {
                    LOGGER.error("An error occured when writing armor renderer information for {}", name, e);
                }
            }
        });
    }

    protected abstract void buildArmorRenderers(BiConsumer<EntityType<?>, RendererInformation> consumer);

    @Override
    public String getName() {
        return "Armor Renderer Information";
    }
}
