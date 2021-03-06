/*
 * Entity Armor Models
 * Copyright (C) 2020 ChampionAsh5357
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation version 3.0 of the License.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.github.championash5357.entityarmormodels.client;

import org.apache.commons.lang3.tuple.Pair;

import io.github.championash5357.entityarmormodels.data.client.EAMLanguageProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.FMLNetworkConstants;

@Mod(EntityArmorModels.ID)
public class EntityArmorModels {

	public static final String ID = "entityarmormodels";
	
	private static final String[] LOCALE_CODES = new String[] {
			"en_us"
	};
	
	public EntityArmorModels() {
		ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.DISPLAYTEST, () -> Pair.of(() -> FMLNetworkConstants.IGNORESERVERONLY, (a, b) -> true));
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientReference::new);
		ModLoadingContext.get().registerConfig(Type.CLIENT, ClientConfigHolder.CLIENT_SPEC);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::data);
	}
	
	private void data(final GatherDataEvent event) {
		if(event.includeClient()) addLanguageProviders(event.getGenerator());
	}
	
	private void addLanguageProviders(final DataGenerator gen) {
		for(String locale : LOCALE_CODES) gen.addProvider(new EAMLanguageProvider(gen, locale));
	}
}
