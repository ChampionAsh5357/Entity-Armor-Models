/*
 * Entity Armor Models
 * Copyright (c) 2021-2021 ChampionAsh5357.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.ashwork.entityarmormodels.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.client.resources.ReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public abstract class MultiJsonReloadListener extends ReloadListener<Map<ResourceLocation, List<JsonElement>>> {

    protected static final Logger LOGGER = LogManager.getLogger();
    protected static final String PATH_SUFFIX = ".json";
    protected static final int PATH_SUFFIX_LENGTH = PATH_SUFFIX.length();
    protected final Gson gson;
    private final String directory;

    public MultiJsonReloadListener(Gson gson, String directory) {
        this.gson = gson;
        this.directory = directory;
    }

    @Override
    protected Map<ResourceLocation, List<JsonElement>> prepare(IResourceManager manager, IProfiler profiler) {
        Map<ResourceLocation, List<JsonElement>> map = new HashMap<>();
        int directoryLength = this.directory.length() + 1;

        manager.listResources(this.directory, str -> str.endsWith(PATH_SUFFIX)).forEach(loc -> {
            String path = loc.getPath();
            ResourceLocation name = new ResourceLocation(loc.getNamespace(), path.substring(directoryLength, path.length() - PATH_SUFFIX_LENGTH));

            try {
                manager.getResources(loc).forEach(resource -> {
                    try (
                            InputStream stream = resource.getInputStream();
                            Reader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
                            ) {
                        JsonObject json = JSONUtils.fromJson(gson, reader, JsonObject.class);
                        if (json == null) LOGGER.error("Couldn't load data list {} from {} in data pack {} as it is empty or null", name, loc, resource.getSourceName());
                        else map.computeIfAbsent(name, x -> new ArrayList<>()).add(json);
                    } catch (RuntimeException | IOException e) {
                        LOGGER.error("Couldn't read data list {} from {} in data pack {}", name, loc, resource.getSourceName(), e);
                    } finally {
                        IOUtils.closeQuietly(resource);
                    }
                });
            } catch (IOException e) {
                LOGGER.error("Couldn't read data list {} from {}", name, loc, e);
            }
        });
        return map;
    }
}
