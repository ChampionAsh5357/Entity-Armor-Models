package io.github.championash5357.entityarmormodels.client.proxy;

import java.util.Map;
import java.util.Map.Entry;

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
import io.github.championash5357.entityarmormodels.client.renderer.entity.model.ExtendedRavagerModel;
import io.github.championash5357.entityarmormodels.client.renderer.entity.model.ExtendedShulkerModel;
import io.github.championash5357.entityarmormodels.client.renderer.entity.model.ExtendedSilverfishModel;
import io.github.championash5357.entityarmormodels.client.renderer.entity.model.ExtendedSkeletonModel;
import io.github.championash5357.entityarmormodels.client.renderer.entity.model.ExtendedSlimeModel;
import io.github.championash5357.entityarmormodels.client.renderer.entity.model.ExtendedSpiderModel;
import io.github.championash5357.entityarmormodels.client.renderer.entity.model.ExtendedVexModel;
import io.github.championash5357.entityarmormodels.client.renderer.entity.model.ExtendedWitchModel;
import io.github.championash5357.entityarmormodels.client.renderer.entity.model.ExtendedZombieVillagerModel;
import io.github.championash5357.entityarmormodels.client.util.ClientConfigHolder;
import io.github.championash5357.entityarmormodels.client.util.RendererCast;
import io.github.championash5357.entityarmormodels.client.util.RendererCast.Constants;
import net.minecraft.client.renderer.entity.EntityRenderer;
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
	 * - Piglin	Brute		(na/na/arrow/na/na/bee)
	 * - Pillager			(armor/na/arrow/na/elytra/bee)
	 * - Polar Bear			
	 * - Pufferfish			
	 * - Rabbit				
	 * - Ravager			(armor/?/arrow/head/elytra/bee)
	 * - Salmon				
	 * - Sheep				
	 * - Shulker			(armor/?/arrow/head/na/bee)
	 * - Silverfish			(armor/?/arrow/head/elytra/bee)
	 * - Skeleton			(na/na/arrow/na/na/bee)
	 * - Skeleton Horse		(na/?/arrow/head/elytra/bee)
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
	 * - Zombie Horse		(na/?/arrow/head/elytra/bee)
	 * - Zombie Villager 	(na/na/arrow/na/na/bee)
	 * - Zombified Piglin	(na/na/arrow/na/na/bee)
	 * */
	private void clientSetup(final FMLClientSetupEvent event) {
		Map<EntityType<?>, EntityRenderer<?>> rendererMap = event.getMinecraftSupplier().get().getRenderManager().renderers;
		if(ClientConfigHolder.CLIENT.creeper.get()) (new RendererCast<>(ExtendedCreeperModel::new, (matrixStackIn, b) -> {matrixStackIn.translate(0.0D, 0.4375D, 0.125D);matrixStackIn.scale(0.75f, 0.75f, 0.75f);}, ClientConfigHolder.CLIENT.creeperLayerRenderingOrdered.get())).castAndApply(rendererMap.get(EntityType.CREEPER));
		if(ClientConfigHolder.CLIENT.enderman.get()) (new RendererCast<>(ClientConfigHolder.CLIENT.endermanExpandedTexture.get() ? (scale) -> new ExtendedEndermanModel<>(scale, 64, 64) : ExtendedEndermanModel::new, (byte)(Constants.NO_HELD_ITEM_LAYERS & ~Constants.ARROW_LAYER), (matrixStackIn, b) -> {matrixStackIn.scale(1.0f, 1.5f, 1.0f);matrixStackIn.translate(0.0D, -0.5625D, 0.0D);}, false)).castAndApply(rendererMap.get(EntityType.ENDERMAN));
		if(ClientConfigHolder.CLIENT.drowned.get()) (new RendererCast<>(ExtendedDrownedModel::new, Constants.CONTACT_LAYERS, (a, b) -> {}, false)).castAndApply(rendererMap.get(EntityType.DROWNED));
		if(ClientConfigHolder.CLIENT.husk.get()) (new RendererCast<>(ExtendedBipedModel::new, Constants.CONTACT_LAYERS, (a, b) -> {}, false)).castAndApply(rendererMap.get(EntityType.HUSK));
		if(ClientConfigHolder.CLIENT.skeleton.get()) (new RendererCast<>(ExtendedSkeletonModel::new, Constants.CONTACT_LAYERS, (a, b) -> {}, false)).castAndApply(rendererMap.get(EntityType.SKELETON));
		if(ClientConfigHolder.CLIENT.stray.get()) (new RendererCast<>(ExtendedSkeletonModel::new, Constants.CONTACT_LAYERS, (a, b) -> {}, false)).castAndApply(rendererMap.get(EntityType.STRAY));
		if(ClientConfigHolder.CLIENT.wither_skeleton.get()) (new RendererCast<>(ExtendedSkeletonModel::new, Constants.CONTACT_LAYERS, (a, b) -> {}, false)).castAndApply(rendererMap.get(EntityType.WITHER_SKELETON));
		if(ClientConfigHolder.CLIENT.zombie.get()) (new RendererCast<>(ExtendedBipedModel::new, Constants.CONTACT_LAYERS, (a, b) -> {}, false)).castAndApply(rendererMap.get(EntityType.ZOMBIE));
		if(ClientConfigHolder.CLIENT.zombie_villager.get()) (new RendererCast<>(ExtendedZombieVillagerModel::new, Constants.CONTACT_LAYERS, (a, b) -> {}, false)).castAndApply(rendererMap.get(EntityType.ZOMBIE_VILLAGER));
		if(ClientConfigHolder.CLIENT.vex.get()) (new RendererCast<>(ExtendedVexModel::new, (byte)(Constants.CONTACT_LAYERS | Constants.ARMOR_LAYER), (a, b) -> {}, false)).castAndApply(rendererMap.get(EntityType.VEX));
		if(ClientConfigHolder.CLIENT.piglin.get()) (new RendererCast<>(ExtendedBipedModel::new, Constants.CONTACT_LAYERS, (a, b) -> {}, false)).castAndApply(rendererMap.get(EntityType.PIGLIN));
		if(ClientConfigHolder.CLIENT.piglin_brute.get()) (new RendererCast<>(ExtendedBipedModel::new, Constants.CONTACT_LAYERS, (a, b) -> {}, false)).castAndApply(rendererMap.get(EntityType.field_242287_aj));
		if(ClientConfigHolder.CLIENT.zombified_piglin.get()) (new RendererCast<>(ExtendedBipedModel::new, Constants.CONTACT_LAYERS, (a, b) -> {}, false)).castAndApply(rendererMap.get(EntityType.ZOMBIFIED_PIGLIN));
		if(ClientConfigHolder.CLIENT.slime.get()) {
			if(ClientConfigHolder.CLIENT.inSlimeGel.get()) (new RendererCast<>((scale) -> new ExtendedSlimeModel<>(scale, 16), Constants.NO_HELD_ELYTRA_LAYERS, new float[] {0.0f, 0.5f, 0.75f}, (a, b) -> {}, true).setHeadLayer(0.6875f, 0.6875f, 0.6875f)).castAndApply(rendererMap.get(EntityType.SLIME));
			else (new RendererCast<>(ExtendedSlimeModel::new, Constants.NO_HELD_ELYTRA_LAYERS, (a, b) -> {}, false)).castAndApply(rendererMap.get(EntityType.SLIME));
		}
		if(ClientConfigHolder.CLIENT.magma_cube.get()) {
			if(ClientConfigHolder.CLIENT.magmaCubeArmorLayerSanity.get()) (new RendererCast<>(ExtendedMagmaCubeModel::new, Constants.NO_HELD_ELYTRA_LAYERS, (a, b) -> {}, false)).castAndApply(rendererMap.get(EntityType.MAGMA_CUBE));
			else (new RendererCast<>((scale) -> new ExtendedMagmaCubeModel<>(scale, 64, 64, false), Constants.NO_HELD_ELYTRA_LAYERS, (a, b) -> {}, false)).castAndApply(rendererMap.get(EntityType.MAGMA_CUBE));
		}
		if(ClientConfigHolder.CLIENT.skeleton_horse.get()) (new RendererCast<>(ExtendedHorseModel::new, Constants.HORSE_ARMOR_LAYERS, (matrixStackIn, child) -> {matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90.0f));if(child) matrixStackIn.translate(0.0d, -1.0d, -1.0d);else matrixStackIn.translate(0.0d, -0.5d, -0.25d);}, false)).castAndApply(rendererMap.get(EntityType.SKELETON_HORSE));
		if(ClientConfigHolder.CLIENT.zombie_horse.get()) (new RendererCast<>(ExtendedHorseModel::new, Constants.HORSE_ARMOR_LAYERS, (matrixStackIn, child) -> {matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90.0f));if(child) matrixStackIn.translate(0.0d, -1.0d, -1.0d);else matrixStackIn.translate(0.0d, -0.5d, -0.25d);}, false)).castAndApply(rendererMap.get(EntityType.ZOMBIE_HORSE));
		if(ClientConfigHolder.CLIENT.horse.get()) (new RendererCast<>(ExtendedHorseModel::new, (byte)(Constants.NO_HELD_ARMOR_LAYERS & ~Constants.ELYTRA_LAYER), (matrixStackIn, child) -> {matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90.0f));if(child) matrixStackIn.translate(0.0d, -1.0d, -1.0d);else matrixStackIn.translate(0.0d, -0.5d, -0.25d);}, false)).castAndApply(rendererMap.get(EntityType.HORSE));
		if(ClientConfigHolder.CLIENT.spider.get()) (new RendererCast<>(ExtendedSpiderModel::new, (matrixStackIn, b) -> {matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90.0f));matrixStackIn.translate(0.0d, -0.125d, -0.8125d);}, false)).castAndApply(rendererMap.get(EntityType.SPIDER));
		if(ClientConfigHolder.CLIENT.cave_spider.get()) (new RendererCast<>(ExtendedSpiderModel::new, (matrixStackIn, b) -> {matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90.0f));matrixStackIn.translate(0.0d, -0.125d, -0.8125d);}, false)).castAndApply(rendererMap.get(EntityType.CAVE_SPIDER));
		if(ClientConfigHolder.CLIENT.witch.get()) (new RendererCast<>(ExtendedWitchModel::new, (a, b) -> {}, false)).castAndApply(rendererMap.get(EntityType.WITCH));
		if(ClientConfigHolder.CLIENT.ghast.get()) (new RendererCast<>(ExtendedGhastModel::new, Constants.NO_HELD_ELYTRA_LAYERS, (a, b) -> {}, false)).castAndApply(rendererMap.get(EntityType.GHAST));
		if(ClientConfigHolder.CLIENT.blaze.get()) (new RendererCast<>(ExtendedBlazeModel::new, Constants.NO_HELD_ELYTRA_LAYERS, (a, b) -> {}, false)).castAndApply(rendererMap.get(EntityType.BLAZE));
		if(ClientConfigHolder.CLIENT.silverfish.get()) (new RendererCast<>(ExtendedSilverfishModel::new, (matrixStackIn, b) -> {matrixStackIn.translate(0.0d, 1.375d, -0.125d);matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90.0f));matrixStackIn.scale(0.5f, 0.5f, 0.5f);}, false)).castAndApply(rendererMap.get(EntityType.SILVERFISH));
		if(ClientConfigHolder.CLIENT.endermite.get()) (new RendererCast<>(ExtendedEndermiteModel::new, (matrixStackIn, b) -> {matrixStackIn.translate(0.0d, 1.375d, -0.125d);matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90.0f));matrixStackIn.scale(0.5f, 0.25f, 0.5f);}, false)).castAndApply(rendererMap.get(EntityType.ENDERMITE));
		if(ClientConfigHolder.CLIENT.evoker.get()) (new RendererCast<>(ExtendedIllagerModel::new, Constants.NO_HELD_HEAD_LAYERS, (a, b) -> {}, false)).castAndApply(rendererMap.get(EntityType.EVOKER));
		if(ClientConfigHolder.CLIENT.illusioner.get()) (new RendererCast<>(ExtendedIllagerModel::new, Constants.NO_HELD_HEAD_LAYERS, (a, b) -> {}, false)).castAndApply(rendererMap.get(EntityType.ILLUSIONER));
		if(ClientConfigHolder.CLIENT.pillager.get()) (new RendererCast<>(ExtendedIllagerModel::new, Constants.NO_HELD_HEAD_LAYERS, (a, b) -> {}, false)).castAndApply(rendererMap.get(EntityType.PILLAGER));
		if(ClientConfigHolder.CLIENT.vindicator.get()) (new RendererCast<>(ExtendedIllagerModel::new, Constants.NO_HELD_HEAD_LAYERS, (a, b) -> {}, false)).castAndApply(rendererMap.get(EntityType.VINDICATOR));
		if(ClientConfigHolder.CLIENT.guardian.get()) (new RendererCast<>(ExtendedGuardianModel::new, Constants.NO_HELD_ELYTRA_LAYERS, (a, b) -> {}, false)).castAndApply(rendererMap.get(EntityType.GUARDIAN));
		if(ClientConfigHolder.CLIENT.elder_guardian.get()) (new RendererCast<>(ExtendedGuardianModel::new, Constants.NO_HELD_ELYTRA_LAYERS, (a, b) -> {}, false)).castAndApply(rendererMap.get(EntityType.ELDER_GUARDIAN));
		if(ClientConfigHolder.CLIENT.hoglin.get()) (new RendererCast<>(ExtendedBoarModel::new, (matrixStackIn, child) -> {matrixStackIn.scale(1.25f, 1.25f, 1.25f);matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90.0f));if(child) matrixStackIn.translate(0.0d, -1.25d, -0.9375d);else matrixStackIn.translate(0.0d, -0.625d, -0.125d);}, false)).castAndApply(rendererMap.get(EntityType.HOGLIN));
		if(ClientConfigHolder.CLIENT.zoglin.get()) (new RendererCast<>(ExtendedBoarModel::new, (matrixStackIn, child) -> {matrixStackIn.scale(1.25f, 1.25f, 1.25f);matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90.0f));if(child) matrixStackIn.translate(0.0d, -1.25d, -0.9375d);else matrixStackIn.translate(0.0d, -0.625d, -0.125d);}, false)).castAndApply(rendererMap.get(EntityType.ZOGLIN));
		if(ClientConfigHolder.CLIENT.phantom.get()) (new RendererCast<>(ExtendedPhantomModel::new, (matrixStackIn, b) -> {matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90.0f));matrixStackIn.translate(0.0d, -0.4375d, 0.0d);}, false)).castAndApply(rendererMap.get(EntityType.PHANTOM));
		if(ClientConfigHolder.CLIENT.ravager.get()) (new RendererCast<>(ExtendedRavagerModel::new, (matrixStackIn, b) -> {matrixStackIn.scale(1.25f, 1.25f, 1.25f);matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90.0f));matrixStackIn.translate(0.0d, -0.375d, 0.5d);}, false)).castAndApply(rendererMap.get(EntityType.RAVAGER));
		if(ClientConfigHolder.CLIENT.shulker.get()) (new RendererCast<>(ExtendedShulkerModel::new, Constants.NO_HELD_ELYTRA_LAYERS, (a, b) -> {}, false)).castAndApply(rendererMap.get(EntityType.SHULKER));
	}
}
