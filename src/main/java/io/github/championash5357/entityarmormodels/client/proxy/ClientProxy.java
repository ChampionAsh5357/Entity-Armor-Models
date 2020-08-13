package io.github.championash5357.entityarmormodels.client.proxy;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.maven.artifact.versioning.ComparableVersion;

import io.github.championash5357.entityarmormodels.client.EntityArmorModels;
import io.github.championash5357.entityarmormodels.client.renderer.entity.model.ExtendedBipedModel;
import io.github.championash5357.entityarmormodels.client.renderer.entity.model.ExtendedBlazeModel;
import io.github.championash5357.entityarmormodels.client.renderer.entity.model.ExtendedBoarModel;
import io.github.championash5357.entityarmormodels.client.renderer.entity.model.ExtendedCreeperModel;
import io.github.championash5357.entityarmormodels.client.renderer.entity.model.ExtendedDrownedModel;
import io.github.championash5357.entityarmormodels.client.renderer.entity.model.ExtendedEndermanModel;
import io.github.championash5357.entityarmormodels.client.renderer.entity.model.ExtendedEndermiteModel;
import io.github.championash5357.entityarmormodels.client.renderer.entity.model.ExtendedGhastModel;
import io.github.championash5357.entityarmormodels.client.renderer.entity.model.ExtendedGuardianModel;
import io.github.championash5357.entityarmormodels.client.renderer.entity.model.ExtendedHorseModel;
import io.github.championash5357.entityarmormodels.client.renderer.entity.model.ExtendedIllagerModel;
import io.github.championash5357.entityarmormodels.client.renderer.entity.model.ExtendedMagmaCubeModel;
import io.github.championash5357.entityarmormodels.client.renderer.entity.model.ExtendedPhantomModel;
import io.github.championash5357.entityarmormodels.client.renderer.entity.model.ExtendedSilverfishModel;
import io.github.championash5357.entityarmormodels.client.renderer.entity.model.ExtendedSkeletonModel;
import io.github.championash5357.entityarmormodels.client.renderer.entity.model.ExtendedSlimeModel;
import io.github.championash5357.entityarmormodels.client.renderer.entity.model.ExtendedSpiderModel;
import io.github.championash5357.entityarmormodels.client.renderer.entity.model.ExtendedVexModel;
import io.github.championash5357.entityarmormodels.client.renderer.entity.model.ExtendedWitchModel;
import io.github.championash5357.entityarmormodels.client.renderer.entity.model.ExtendedZombieVillagerModel;
import io.github.championash5357.entityarmormodels.client.util.ClientConfigHolder;
import io.github.championash5357.entityarmormodels.client.util.RendererCast;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Util;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.ClickEvent.Action;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.VersionChecker;
import net.minecraftforge.fml.VersionChecker.CheckResult;
import net.minecraftforge.fml.VersionChecker.Status;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forgespi.language.IConfigurable;
import net.minecraftforge.forgespi.language.IModInfo;

public class ClientProxy {

	public ClientProxy() {
		setup();
	}

	public void setup() {
		IEventBus mod = FMLJavaModLoadingContext.get().getModEventBus();
		IEventBus forge = MinecraftForge.EVENT_BUS;

		mod.addListener(this::clientSetup);
		forge.addListener(this::clientLoggedIn);
	}

	private void clientLoggedIn(final ClientPlayerNetworkEvent.LoggedInEvent event) {
		IModInfo info = ModList.get().getModContainerById(EntityArmorModels.ID).get().getModInfo();
		CheckResult result = VersionChecker.getResult(info);
		if (ClientConfigHolder.CLIENT.enableUpdateNotifications.get() && (result.status == Status.OUTDATED || result.status == Status.BETA_OUTDATED)) {
			TextComponent clickableVersion = new StringTextComponent(TextFormatting.GOLD + "" + result.target);
			if (info instanceof IConfigurable) {
				((IConfigurable) info).getConfigElement("displayURL").ifPresent(url -> {
					if (url instanceof String) {
						clickableVersion.setStyle(clickableVersion.getStyle().setClickEvent(new ClickEvent(Action.OPEN_URL, (String) url)));
					}
				});	
			}
			event.getPlayer().sendMessage(new TranslationTextComponent("notification.entityarmormodels.outdated", new StringTextComponent(TextFormatting.BLUE + "Entity " + TextFormatting.RED + "Armor " + TextFormatting.WHITE + "Models")).mergeStyle(TextFormatting.GRAY), Util.DUMMY_UUID);
			event.getPlayer().sendMessage(new TranslationTextComponent("notification.entityarmormodels.current_version", clickableVersion).mergeStyle(TextFormatting.GRAY), Util.DUMMY_UUID);
			event.getPlayer().sendMessage(new TranslationTextComponent("notification.entityarmormodels.changelog").mergeStyle(TextFormatting.WHITE), Util.DUMMY_UUID);
			for(Entry<ComparableVersion, String> entry : result.changes.entrySet()) {
				event.getPlayer().sendMessage(new StringTextComponent(" - " + TextFormatting.GOLD + entry.getKey() + ": " + TextFormatting.GRAY + entry.getValue()), Util.DUMMY_UUID);
			}
		}
	}

	/**
	 * TODO
	 * BipedArmorLayer Application (done)
	 * HeldItemLayer Application (need to eventually do)
	 * HeadLayer Application (done)
	 * ElytraLayer Application (done)
	 * 
	 * StuckInBodyLayer Application (done)
	 * - ArrowLayer Application (done)
	 * - BeeStingerLayer Application (done)
	 * 
	 * ORDER
	 * - Armor
	 * - Held Item
	 * - Arrow
	 * - Head
	 * - Elytra
	 * - Bee Sting
	 * - Cutout Rendering (if ordered first)
	 * 
	 * 1.0 RELEASE
	 * - Ravager		Segmented  - 25.0 Beta (armor/?/arrow/head/elytra/bee)
	 * - Shulker		Segmented  - 1.0 Release
	 * 
	 * MOBS
	 * - Bat				
	 * - Bee				
	 * - Blaze				(armor/?/arrow/head/na/bee)
	 * - Cat				
	 * - Cave Spider		(armor/?/arrow/head/na/bee)
	 * - Chicken			
	 * - Cod				
	 * - Cow				
	 * - Creeper 			(armor/?/arrow/head/elytra/bee)
	 * - Dolphin			
	 * - Donkey				
	 * - Drowned			(na/na/arrow/na/na/bee)
	 * - Elder Guardian		(armor/?/arrow/head/na/bee)
	 * - Ender Dragon		
	 * - Enderman 			(armor/na/na/head/elytra/bee)
	 * - Endermite			(armor/?/arrow/head/elytra/bee)
	 * - Evoker				(armor/na/arrow/na/elytra/bee)
	 * - Fox				
	 * - Ghast				(armor/?/arrow/head/na/bee)
	 * - Giant				
	 * - Guardian			(armor/?/arrow/head/na/bee)
	 * - Hoglin				(armor/?/arrow/head/elytra/bee)
	 * - Horse				(na/?/arrow/head/elytra/bee) //Has problems for elytra?
	 * - Husk				(na/na/arrow/na/na/bee)
	 * - Illusioner			(armor/na/arrow/na/elytra/bee)
	 * - Iron Golem			
	 * - Llama				
	 * - Magma Cube			(armor/?/arrow/head/na/bee)
	 * - Mule				
	 * - Mooshroom			
	 * - Ocelot				
	 * - Panda				
	 * - Parrot				
	 * - Phantom			(armor/?/arrow/head/elytra/bee)
	 * - Pig				
	 * - Piglin				(na/na/arrow/na/na/bee)
	 * - Pillager			(armor/na/arrow/na/elytra/bee)
	 * - Polar Bear			
	 * - Pufferfish			
	 * - Rabbit				
	 * - Ravager			
	 * - Salmon				
	 * - Sheep				
	 * - Shulker			
	 * - Silverfish			(armor/?/arrow/head/elytra/bee)
	 * - Skeleton			(na/na/arrow/na/na/bee)
	 * - Skeleton Horse		(na/?/arrow/head/elytra/bee) //Might need to reevaulate for armor
	 * - Slime				(armor/?/arrow/head/na/bee)
	 * - Snow Golem			
	 * - Spider				(armor/?/arrow/head/na/bee)
	 * - Squid				
	 * - Stray				(na/na/arrow/na/na/bee)
	 * - Strider			
	 * - Trader Llama		
	 * - Tropical Fish		
	 * - Turtle				
	 * - Vex 				(armor/na/arrow/na/na/bee)
	 * - Villager			
	 * - Vindicator			(armor/na/arrow/na/elytra/bee)
	 * - Wandering Trader	
	 * - Witch				(armor/na/arrow/head/elytra/bee)
	 * - Wither				
	 * - Wither Skeleton	(na/na/arrow/na/na/bee)
	 * - Wolf				
	 * - Zoglin				(armor/?/arrow/head/elytra/bee)
	 * - Zombie				(na/na/arrow/na/na/bee)
	 * - Zombie Horse		(na/?/arrow/head/elytra/bee) //Might need to reevaulate for armor
	 * - Zombie Villager 	(na/na/arrow/na/na/bee)
	 * - Zombified Piglin	(na/na/arrow/na/na/bee)
	 * */
	private void clientSetup(final FMLClientSetupEvent event) {
		final Map<EntityType<?>, RendererCast<?, ?, ?>> renderer_map = getRendererMap();

		event.getMinecraftSupplier().get().getRenderManager().renderers.forEach((type, renderer) -> {
			Optional<RendererCast<?, ?, ?>> opt = Optional.ofNullable(renderer_map.get(type));
			opt.ifPresent(cast -> cast.castAndApply(renderer));
		});
	}

	private Map<EntityType<?>, RendererCast<?, ?, ?>> getRendererMap() {
		return Util.make(new ConcurrentHashMap<>(), map -> {
			if(ClientConfigHolder.CLIENT.creeper.get()) map.putIfAbsent(EntityType.CREEPER, new RendererCast<>(ExtendedCreeperModel::new, (matrixStackIn, b) -> {matrixStackIn.translate(0.0D, 0.4375D, 0.125D);matrixStackIn.scale(0.75f, 0.75f, 0.75f);}, ClientConfigHolder.CLIENT.creeperLayerRenderingOrdered.get()));
			if(ClientConfigHolder.CLIENT.enderman.get()) map.putIfAbsent(EntityType.ENDERMAN, new RendererCast<>(ClientConfigHolder.CLIENT.endermanExpandedTexture.get() ? (scale) -> new ExtendedEndermanModel<>(scale, 64, 64) : ExtendedEndermanModel::new, 1, (matrixStackIn, b) -> {matrixStackIn.scale(1.0f, 1.5f, 1.0f);matrixStackIn.translate(0.0D, -0.5625D, 0.0D);}, false));
			if(ClientConfigHolder.CLIENT.drowned.get()) map.putIfAbsent(EntityType.DROWNED, new RendererCast<>(ExtendedDrownedModel::new, 2, (a, b) -> {}, false));
			if(ClientConfigHolder.CLIENT.husk.get()) map.putIfAbsent(EntityType.HUSK, new RendererCast<>(ExtendedBipedModel::new, 2, (a, b) -> {}, false));
			if(ClientConfigHolder.CLIENT.skeleton.get()) map.putIfAbsent(EntityType.SKELETON, new RendererCast<>(ExtendedSkeletonModel::new, 2, (a, b) -> {}, false));
			if(ClientConfigHolder.CLIENT.stray.get()) map.putIfAbsent(EntityType.STRAY, new RendererCast<>(ExtendedSkeletonModel::new, 2, (a, b) -> {}, false));
			if(ClientConfigHolder.CLIENT.wither_skeleton.get()) map.putIfAbsent(EntityType.WITHER_SKELETON, new RendererCast<>(ExtendedSkeletonModel::new, 2, (a, b) -> {}, false));
			if(ClientConfigHolder.CLIENT.zombie.get()) map.putIfAbsent(EntityType.ZOMBIE, new RendererCast<>(ExtendedBipedModel::new, 2, (a, b) -> {}, false));
			if(ClientConfigHolder.CLIENT.zombie_villager.get()) map.putIfAbsent(EntityType.ZOMBIE_VILLAGER, new RendererCast<>(ExtendedZombieVillagerModel::new, 2, (a, b) -> {}, false));
			if(ClientConfigHolder.CLIENT.vex.get()) map.putIfAbsent(EntityType.VEX, new RendererCast<>(ExtendedVexModel::new, 3, (a, b) -> {}, false));
			if(ClientConfigHolder.CLIENT.piglin.get()) map.putIfAbsent(EntityType.PIGLIN, new RendererCast<>(ExtendedBipedModel::new, 2, (a, b) -> {}, false));
			if(ClientConfigHolder.CLIENT.zombified_piglin.get()) map.putIfAbsent(EntityType.ZOMBIFIED_PIGLIN, new RendererCast<>(ExtendedBipedModel::new, 2, (a, b) -> {}, false));
			if(ClientConfigHolder.CLIENT.slime.get()) {
				if(ClientConfigHolder.CLIENT.inSlimeGel.get()) map.putIfAbsent(EntityType.SLIME, new RendererCast<>((scale) -> new ExtendedSlimeModel<>(scale, 16), 4, new float[] {0.0f, 0.5f, 0.75f}, (a, b) -> {}, true).setHeadLayer(0.6875f, 0.6875f, 0.6875f));
				else map.putIfAbsent(EntityType.SLIME, new RendererCast<>(ExtendedSlimeModel::new, 4, (a, b) -> {}, false));
			}
			if(ClientConfigHolder.CLIENT.magma_cube.get()) {
				if(ClientConfigHolder.CLIENT.magmaCubeArmorLayerSanity.get()) map.putIfAbsent(EntityType.MAGMA_CUBE, new RendererCast<>(ExtendedMagmaCubeModel::new, 4, (a, b) -> {}, false));
				else map.putIfAbsent(EntityType.MAGMA_CUBE, new RendererCast<>((scale) -> new ExtendedMagmaCubeModel<>(scale, 64, 64, false), 4, (a, b) -> {}, false));
			}
			if(ClientConfigHolder.CLIENT.skeleton_horse.get()) map.putIfAbsent(EntityType.SKELETON_HORSE, new RendererCast<>(ExtendedHorseModel::new, 5, (matrixStackIn, child) -> {matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90.0f));if(child) matrixStackIn.translate(0.0d, -1.0d, -1.0d);else matrixStackIn.translate(0.0d, -0.5d, -0.25d);}, false));
			if(ClientConfigHolder.CLIENT.zombie_horse.get()) map.putIfAbsent(EntityType.ZOMBIE_HORSE, new RendererCast<>(ExtendedHorseModel::new, 5, (matrixStackIn, child) -> {matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90.0f));if(child) matrixStackIn.translate(0.0d, -1.0d, -1.0d);else matrixStackIn.translate(0.0d, -0.5d, -0.25d);}, false));
			if(ClientConfigHolder.CLIENT.horse.get()) map.putIfAbsent(EntityType.HORSE, new RendererCast<>(ExtendedHorseModel::new, 5, (matrixStackIn, child) -> {matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90.0f));if(child) matrixStackIn.translate(0.0d, -1.0d, -1.0d);else matrixStackIn.translate(0.0d, -0.5d, -0.25d);}, false));
			if(ClientConfigHolder.CLIENT.spider.get()) map.putIfAbsent(EntityType.SPIDER, new RendererCast<>(ExtendedSpiderModel::new, (matrixStackIn, b) -> {matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90.0f));matrixStackIn.translate(0.0d, -0.125d, -0.8125d);}, false));
			if(ClientConfigHolder.CLIENT.cave_spider.get()) map.putIfAbsent(EntityType.CAVE_SPIDER, new RendererCast<>(ExtendedSpiderModel::new, (matrixStackIn, b) -> {matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90.0f));matrixStackIn.translate(0.0d, -0.125d, -0.8125d);}, false));
			if(ClientConfigHolder.CLIENT.witch.get()) map.putIfAbsent(EntityType.WITCH, new RendererCast<>(ExtendedWitchModel::new, (a, b) -> {}, false));
			if(ClientConfigHolder.CLIENT.ghast.get()) map.putIfAbsent(EntityType.GHAST, new RendererCast<>(ExtendedGhastModel::new, 4, (a, b) -> {}, false));
			if(ClientConfigHolder.CLIENT.blaze.get()) map.putIfAbsent(EntityType.BLAZE, new RendererCast<>(ExtendedBlazeModel::new, 4, (a, b) -> {}, false));
			if(ClientConfigHolder.CLIENT.silverfish.get()) map.putIfAbsent(EntityType.SILVERFISH, new RendererCast<>(ExtendedSilverfishModel::new, (matrixStackIn, b) -> {matrixStackIn.translate(0.0d, 1.375d, -0.125d);matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90.0f));matrixStackIn.scale(0.5f, 0.5f, 0.5f);}, false));
			if(ClientConfigHolder.CLIENT.endermite.get()) map.putIfAbsent(EntityType.ENDERMITE, new RendererCast<>(ExtendedEndermiteModel::new, (matrixStackIn, b) -> {matrixStackIn.translate(0.0d, 1.375d, -0.125d);matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90.0f));matrixStackIn.scale(0.5f, 0.25f, 0.5f);}, false));
			if(ClientConfigHolder.CLIENT.evoker.get()) map.putIfAbsent(EntityType.EVOKER, new RendererCast<>(ExtendedIllagerModel::new, 6, (a, b) -> {}, false));
			if(ClientConfigHolder.CLIENT.illusioner.get()) map.putIfAbsent(EntityType.ILLUSIONER, new RendererCast<>(ExtendedIllagerModel::new, 6, (a, b) -> {}, false));
			if(ClientConfigHolder.CLIENT.pillager.get()) map.putIfAbsent(EntityType.PILLAGER, new RendererCast<>(ExtendedIllagerModel::new, 6, (a, b) -> {}, false));
			if(ClientConfigHolder.CLIENT.vindicator.get()) map.putIfAbsent(EntityType.VINDICATOR, new RendererCast<>(ExtendedIllagerModel::new, 6, (a, b) -> {}, false));
			if(ClientConfigHolder.CLIENT.guardian.get()) map.putIfAbsent(EntityType.GUARDIAN, new RendererCast<>(ExtendedGuardianModel::new, 4, (a, b) -> {}, false));
			if(ClientConfigHolder.CLIENT.elder_guardian.get()) map.putIfAbsent(EntityType.ELDER_GUARDIAN, new RendererCast<>(ExtendedGuardianModel::new, 4, (a, b) -> {}, false));
			if(ClientConfigHolder.CLIENT.hoglin.get()) map.putIfAbsent(EntityType.HOGLIN, new RendererCast<>(ExtendedBoarModel::new, (matrixStackIn, child) -> {matrixStackIn.scale(1.25f, 1.25f, 1.25f);matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90.0f));if(child) matrixStackIn.translate(0.0d, -1.25d, -0.9375d);else matrixStackIn.translate(0.0d, -0.625d, -0.125d);}, false));
			if(ClientConfigHolder.CLIENT.zoglin.get()) map.putIfAbsent(EntityType.ZOGLIN, new RendererCast<>(ExtendedBoarModel::new, (matrixStackIn, child) -> {matrixStackIn.scale(1.25f, 1.25f, 1.25f);matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90.0f));if(child) matrixStackIn.translate(0.0d, -1.25d, -0.9375d);else matrixStackIn.translate(0.0d, -0.625d, -0.125d);}, false));
			if(ClientConfigHolder.CLIENT.phantom.get()) map.putIfAbsent(EntityType.PHANTOM, new RendererCast<>(ExtendedPhantomModel::new, (matrixStackIn, b) -> {matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90.0f));matrixStackIn.translate(0.0d, -0.4375d, 0.0d);}, false));
		});
	}
}
