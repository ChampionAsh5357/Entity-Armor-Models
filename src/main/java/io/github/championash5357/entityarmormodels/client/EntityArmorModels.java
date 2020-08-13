package io.github.championash5357.entityarmormodels.client;

import org.apache.commons.lang3.tuple.Pair;

import io.github.championash5357.entityarmormodels.client.proxy.ClientProxy;
import io.github.championash5357.entityarmormodels.client.util.ClientConfigHolder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.network.FMLNetworkConstants;

@Mod(EntityArmorModels.ID)
public class EntityArmorModels {

	public static final String ID = "entityarmormodels";
	
	public EntityArmorModels() {
		ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.DISPLAYTEST, () -> Pair.of(() -> FMLNetworkConstants.IGNORESERVERONLY, (a, b) -> true));
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientProxy::new);
		ModLoadingContext.get().registerConfig(Type.CLIENT, ClientConfigHolder.CLIENT_SPEC);
	}
}
