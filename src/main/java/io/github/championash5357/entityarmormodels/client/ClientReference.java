package io.github.championash5357.entityarmormodels.client;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.maven.artifact.versioning.ComparableVersion;

import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.ExtendedBlazeModel;
import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.ExtendedCreeperModel;
import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.ExtendedEndermanModel;
import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.ExtendedEndermiteModel;
import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.ExtendedGhastModel;
import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.ExtendedGuardianModel;
import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.ExtendedHoglinModel;
import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.ExtendedIllagerModel;
import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.ExtendedIronGolemModel;
import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.ExtendedLlamaModel;
import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.ExtendedMagmaCubeModel;
import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.ExtendedPhantomModel;
import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.ExtendedPolarBearModel;
import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.ExtendedRavagerModel;
import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.ExtendedShulkerModel;
import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.ExtendedSilverfishModel;
import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.ExtendedSlimeModel;
import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.ExtendedSpiderModel;
import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.ExtendedVexModel;
import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.ExtendedVillagerModel;
import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.ExtendedWitchModel;
import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.vanilla.ExtendedBipedModel;
import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.vanilla.ExtendedDrownedModel;
import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.vanilla.ExtendedHorseArmorChestsModel;
import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.vanilla.ExtendedHorseModel;
import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.vanilla.ExtendedSkeletonModel;
import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.vanilla.ExtendedZombieVillagerModel;
import io.github.championash5357.entityarmormodels.client.util.RendererCast;
import io.github.championash5357.entityarmormodels.client.util.RendererCast.Constants;
import net.minecraft.client.Minecraft;
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
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.VersionChecker;
import net.minecraftforge.fml.VersionChecker.CheckResult;
import net.minecraftforge.fml.VersionChecker.Status;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forgespi.language.IModInfo;

public class ClientReference {

	private Minecraft mc;
	
	public ClientReference() {
		setup();
	}

	public void setup() {
		IEventBus mod = FMLJavaModLoadingContext.get().getModEventBus();
		IEventBus forge = MinecraftForge.EVENT_BUS;

		mod.addListener(this::clientSetup);
		mod.addListener(this::processMessage);
		mod.addListener(this::loadComplete);
		forge.addListener(this::clientLoggedIn);
	}

	private void clientLoggedIn(final ClientPlayerNetworkEvent.LoggedInEvent event) {
		IModInfo info = ModList.get().getModContainerById(EntityArmorModels.ID).get().getModInfo();
		CheckResult result = VersionChecker.getResult(info);
		if (ClientConfigHolder.CLIENT.enableUpdateNotifications.get() && (result.status == Status.OUTDATED || result.status == Status.BETA_OUTDATED)) {
			TextComponent clickableVersion = new StringTextComponent(TextFormatting.GOLD + "" + result.target);
			clickableVersion.setStyle(clickableVersion.getStyle().setClickEvent(new ClickEvent(Action.OPEN_URL, result.url)));
			event.getPlayer().sendMessage(new TranslationTextComponent("notification.entityarmormodels.outdated", new StringTextComponent(TextFormatting.BLUE + "Entity " + TextFormatting.RED + "Armor " + TextFormatting.WHITE + "Models")).mergeStyle(TextFormatting.GRAY), Util.DUMMY_UUID);
			event.getPlayer().sendMessage(new TranslationTextComponent("notification.entityarmormodels.current_version", clickableVersion).mergeStyle(TextFormatting.GRAY), Util.DUMMY_UUID);
			event.getPlayer().sendMessage(new TranslationTextComponent("notification.entityarmormodels.changelog").mergeStyle(TextFormatting.WHITE), Util.DUMMY_UUID);
			for(Entry<ComparableVersion, String> entry : result.changes.entrySet()) {
				event.getPlayer().sendMessage(new StringTextComponent(" - " + TextFormatting.GOLD + entry.getKey() + ": " + TextFormatting.GRAY + entry.getValue()), Util.DUMMY_UUID);
			}
		}
	}
	
	private void clientSetup(final FMLClientSetupEvent event) {
		this.mc = event.getMinecraftSupplier().get();
	}
	
	private void processMessage(final InterModProcessEvent event) {
		InterModComms.getMessages("backslot", (str) -> str == "enableRendering").forEach(message -> {
			RendererCast.backLayerPresent = (boolean) message.getMessageSupplier().get();
		});
	}
	
	/**
	 * TODO
	 * BipedArmorLayer Application (done)
	 * HeldItemLayer Application (need to eventually do)
	 * HeadLayer Application (maybe done?)
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
	 * Mobs In order of Age:
	 * Pig
	 * Sheep
	 * Cow
	 * Chicken
	 * Squid
	 * Wolf
	 * Snow Golem
	 * Mooshroom
	 * Ender Dragon
	 * Ocelot/Cat
	 * Wither
	 * Bat
	 * Rabbit
	 * Parrot
	 * Turtle
	 * Cod/Salmon/Pufferfish
	 * Tropical Fish
	 * Dolphin
	 * Panda
	 * Fox
	 * Bee
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
	 * - Donkey				(armor/?/arrow/head/elytra/bee)
	 * - Drowned			(na/na/arrow/na/na/bee)
	 * - Elder Guardian		(armor/?/arrow/head/na/bee)
	 * - Ender Dragon		
	 * - Enderman 			(armor/na/na/head/elytra/bee)
	 * - Endermite			(armor/?/arrow/head/elytra/bee)
	 * - Evoker				(armor/na/arrow/na/elytra/bee)
	 * - Fox				
	 * - Ghast				(armor/?/arrow/head/na/bee)
	 * - Giant				(na/na/arrow/head/elytra/bee)
	 * - Guardian			(armor/?/arrow/head/na/bee)
	 * - Hoglin				(armor/?/arrow/head/elytra/bee)
	 * - Horse				(na/?/arrow/head/elytra/bee)
	 * - Husk				(na/na/arrow/na/na/bee)
	 * - Illusioner			(armor/na/arrow/na/elytra/bee)
	 * - Iron Golem			(armor/na/arrow/head/elytra/bee)
	 * - Llama				(armor/?/arrow/head/elytra/bee)
	 * - Magma Cube			(armor/?/arrow/head/na/bee)
	 * - Mule				(armor/?/arrow/head/elytra/bee)
	 * - Mooshroom			
	 * - Ocelot				
	 * - Panda				
	 * - Parrot				
	 * - Phantom			(armor/?/arrow/head/elytra/bee)
	 * - Pig				
	 * - Piglin				(na/na/arrow/na/na/bee)
	 * - Piglin Brute		(na/na/arrow/na/na/bee)
	 * - Pillager			(armor/na/arrow/na/elytra/bee)
	 * - Polar Bear			(armor/na/arrow/head/elytra/bee)
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
	 * - Trader Llama		(armor/?/arrow/head/elytra/bee)
	 * - Tropical Fish		
	 * - Turtle				
	 * - Vex 				(armor/na/arrow/na/na/bee)
	 * - Villager			(armor/na/arrow/na/elytra/bee)
	 * - Vindicator			(armor/na/arrow/na/elytra/bee)
	 * - Wandering Trader	(armor/na/arrow/na/elytra/bee)
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
	private void loadComplete(final FMLLoadCompleteEvent event) {
		event.enqueueWork(() -> {
			Map<EntityType<?>, EntityRenderer<?>> rendererMap = mc.getRenderManager().renderers;
			if(ClientConfigHolder.CLIENT.creeper.get()) (new RendererCast<>(ExtendedCreeperModel::new, (matrixStack, b) -> {matrixStack.translate(0.0D, 0.4375D, 0.125D);matrixStack.scale(0.75f, 0.75f, 0.75f);}, ClientConfigHolder.CLIENT.creeperLayerRenderingOrdered.get())).castAndApply(rendererMap.get(EntityType.CREEPER));
			if(ClientConfigHolder.CLIENT.enderman.get()) (new RendererCast<>(ClientConfigHolder.CLIENT.endermanExpandedTexture.get() ? (scale) -> new ExtendedEndermanModel<>(scale, 64, 64) : ExtendedEndermanModel::new, (byte)(Constants.NO_HELD_ITEM_LAYERS & ~Constants.ARROW_LAYER), (matrixStack, b) -> {matrixStack.scale(1.0f, 1.5f, 1.0f);matrixStack.translate(0.0D, -0.5625D, 0.0D);}, false)).castAndApply(rendererMap.get(EntityType.ENDERMAN));
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
			if(ClientConfigHolder.CLIENT.skeleton_horse.get()) (new RendererCast<>(ExtendedHorseModel::new, Constants.HORSE_ARMOR_LAYERS, (matrixStack, child) -> {matrixStack.rotate(Vector3f.XP.rotationDegrees(90.0f));if(child) matrixStack.translate(0.0d, -1.0d, -1.0d);else matrixStack.translate(0.0d, -0.5d, -0.25d);}, false)).castAndApply(rendererMap.get(EntityType.SKELETON_HORSE));
			if(ClientConfigHolder.CLIENT.zombie_horse.get()) (new RendererCast<>(ExtendedHorseModel::new, Constants.HORSE_ARMOR_LAYERS, (matrixStack, child) -> {matrixStack.rotate(Vector3f.XP.rotationDegrees(90.0f));if(child) matrixStack.translate(0.0d, -1.0d, -1.0d);else matrixStack.translate(0.0d, -0.5d, -0.25d);}, false)).castAndApply(rendererMap.get(EntityType.ZOMBIE_HORSE));
			if(ClientConfigHolder.CLIENT.horse.get()) (new RendererCast<>(ExtendedHorseModel::new, (byte)(Constants.NO_HELD_ARMOR_LAYERS & ~Constants.ELYTRA_LAYER), (matrixStack, child) -> {matrixStack.rotate(Vector3f.XP.rotationDegrees(90.0f));if(child) matrixStack.translate(0.0d, -1.0d, -1.0d);else matrixStack.translate(0.0d, -0.5d, -0.25d);}, false)).castAndApply(rendererMap.get(EntityType.HORSE));
			if(ClientConfigHolder.CLIENT.spider.get()) (new RendererCast<>(ExtendedSpiderModel::new, (matrixStack, b) -> {matrixStack.rotate(Vector3f.XP.rotationDegrees(90.0f));matrixStack.translate(0.0d, -0.125d, -0.8125d);}, false)).castAndApply(rendererMap.get(EntityType.SPIDER));
			if(ClientConfigHolder.CLIENT.cave_spider.get()) (new RendererCast<>(ExtendedSpiderModel::new, (matrixStack, b) -> {matrixStack.rotate(Vector3f.XP.rotationDegrees(90.0f));matrixStack.translate(0.0d, -0.125d, -0.8125d);}, false)).castAndApply(rendererMap.get(EntityType.CAVE_SPIDER));
			if(ClientConfigHolder.CLIENT.witch.get()) (new RendererCast<>(ExtendedWitchModel::new, (a, b) -> {}, false)).castAndApply(rendererMap.get(EntityType.WITCH));
			if(ClientConfigHolder.CLIENT.ghast.get()) (new RendererCast<>(ExtendedGhastModel::new, Constants.NO_HELD_ELYTRA_LAYERS, (a, b) -> {}, false)).castAndApply(rendererMap.get(EntityType.GHAST));
			if(ClientConfigHolder.CLIENT.blaze.get()) (new RendererCast<>(ExtendedBlazeModel::new, Constants.NO_HELD_ELYTRA_LAYERS, (a, b) -> {}, false)).castAndApply(rendererMap.get(EntityType.BLAZE));
			if(ClientConfigHolder.CLIENT.silverfish.get()) (new RendererCast<>(ExtendedSilverfishModel::new, (matrixStack, b) -> {matrixStack.translate(0.0d, 1.375d, -0.125d);matrixStack.rotate(Vector3f.XP.rotationDegrees(90.0f));matrixStack.scale(0.5f, 0.5f, 0.5f);}, false)).castAndApply(rendererMap.get(EntityType.SILVERFISH));
			if(ClientConfigHolder.CLIENT.endermite.get()) (new RendererCast<>(ExtendedEndermiteModel::new, (matrixStack, b) -> {matrixStack.translate(0.0d, 1.375d, -0.125d);matrixStack.rotate(Vector3f.XP.rotationDegrees(90.0f));matrixStack.scale(0.5f, 0.25f, 0.5f);}, false)).castAndApply(rendererMap.get(EntityType.ENDERMITE));
			if(ClientConfigHolder.CLIENT.evoker.get()) (new RendererCast<>(ExtendedIllagerModel::new, Constants.NO_HELD_HEAD_LAYERS, (a, b) -> {}, false)).castAndApply(rendererMap.get(EntityType.EVOKER));
			if(ClientConfigHolder.CLIENT.illusioner.get()) (new RendererCast<>(ExtendedIllagerModel::new, Constants.NO_HELD_HEAD_LAYERS, (a, b) -> {}, false)).castAndApply(rendererMap.get(EntityType.ILLUSIONER));
			if(ClientConfigHolder.CLIENT.pillager.get()) (new RendererCast<>(ExtendedIllagerModel::new, Constants.NO_HELD_HEAD_LAYERS, (a, b) -> {}, false)).castAndApply(rendererMap.get(EntityType.PILLAGER));
			if(ClientConfigHolder.CLIENT.vindicator.get()) (new RendererCast<>(ExtendedIllagerModel::new, Constants.NO_HELD_HEAD_LAYERS, (a, b) -> {}, false)).castAndApply(rendererMap.get(EntityType.VINDICATOR));
			if(ClientConfigHolder.CLIENT.guardian.get()) (new RendererCast<>(ExtendedGuardianModel::new, Constants.NO_HELD_ELYTRA_LAYERS, (a, b) -> {}, false)).castAndApply(rendererMap.get(EntityType.GUARDIAN));
			if(ClientConfigHolder.CLIENT.elder_guardian.get()) (new RendererCast<>(ExtendedGuardianModel::new, Constants.NO_HELD_ELYTRA_LAYERS, (a, b) -> {}, false)).castAndApply(rendererMap.get(EntityType.ELDER_GUARDIAN));
			if(ClientConfigHolder.CLIENT.hoglin.get()) (new RendererCast<>(ExtendedHoglinModel::new, (matrixStack, child) -> {matrixStack.scale(1.25f, 1.25f, 1.25f);matrixStack.rotate(Vector3f.XP.rotationDegrees(90.0f));if(child) matrixStack.translate(0.0d, -1.25d, -0.9375d);else matrixStack.translate(0.0d, -0.625d, -0.125d);}, false)).castAndApply(rendererMap.get(EntityType.HOGLIN));
			if(ClientConfigHolder.CLIENT.zoglin.get()) (new RendererCast<>(ExtendedHoglinModel::new, (matrixStack, child) -> {matrixStack.scale(1.25f, 1.25f, 1.25f);matrixStack.rotate(Vector3f.XP.rotationDegrees(90.0f));if(child) matrixStack.translate(0.0d, -1.25d, -0.9375d);else matrixStack.translate(0.0d, -0.625d, -0.125d);}, false)).castAndApply(rendererMap.get(EntityType.ZOGLIN));
			if(ClientConfigHolder.CLIENT.phantom.get()) (new RendererCast<>(ExtendedPhantomModel::new, (matrixStack, b) -> {matrixStack.rotate(Vector3f.XP.rotationDegrees(90.0f));matrixStack.translate(0.0d, -0.4375d, 0.0d);}, false)).castAndApply(rendererMap.get(EntityType.PHANTOM));
			if(ClientConfigHolder.CLIENT.ravager.get()) (new RendererCast<>(ExtendedRavagerModel::new, (matrixStack, b) -> {matrixStack.scale(1.25f, 1.25f, 1.25f);matrixStack.rotate(Vector3f.XP.rotationDegrees(90.0f));matrixStack.translate(0.0d, -0.375d, 0.5d);}, false)).castAndApply(rendererMap.get(EntityType.RAVAGER));
			if(ClientConfigHolder.CLIENT.shulker.get()) (new RendererCast<>(ExtendedShulkerModel::new, Constants.NO_HELD_ELYTRA_LAYERS, (a, b) -> {}, false)).castAndApply(rendererMap.get(EntityType.SHULKER));
			if(ClientConfigHolder.CLIENT.giant.get()) (new RendererCast<>(ExtendedBipedModel::new, Constants.NO_HELD_ARMOR_LAYERS, (a, b) -> {}, false)).castAndApply(rendererMap.get(EntityType.GIANT));
			if(ClientConfigHolder.CLIENT.iron_golem.get()) (new RendererCast<>(ExtendedIronGolemModel::new, Constants.NO_HELD_ITEM_LAYERS, (matrixStack, b) -> {matrixStack.scale(1.5f, 1.5f, 1.5f);matrixStack.translate(0.0d, -0.35d, 0.0d);}, false)).castAndApply(rendererMap.get(EntityType.IRON_GOLEM));
			if(ClientConfigHolder.CLIENT.polar_bear.get()) (new RendererCast<>(ExtendedPolarBearModel::new, Constants.NO_HELD_ITEM_LAYERS, (matrixStack, child) -> {if(child) matrixStack.translate(0.0d, 1.375d, -1.3125d);else matrixStack.translate(0.0d, 0.5d, -0.75d);matrixStack.rotate(Vector3f.XP.rotationDegrees(90.0f));}, false)).castAndApply(rendererMap.get(EntityType.POLAR_BEAR));
			if(ClientConfigHolder.CLIENT.villager.get()) (new RendererCast<>(ExtendedVillagerModel::new, Constants.NO_HELD_HEAD_LAYERS, (a, b) -> {}, false)).castAndApply(rendererMap.get(EntityType.VILLAGER));
			if(ClientConfigHolder.CLIENT.wandering_trader.get()) (new RendererCast<>(ExtendedVillagerModel::new, Constants.NO_HELD_HEAD_LAYERS, (a, b) -> {}, false)).castAndApply(rendererMap.get(EntityType.WANDERING_TRADER));
			if(ClientConfigHolder.CLIENT.mule.get()) (new RendererCast<>(ExtendedHorseArmorChestsModel::new, Constants.HORSE_ARMOR_LAYERS, (matrixStack, child) -> {matrixStack.rotate(Vector3f.XP.rotationDegrees(90.0f));if(child) matrixStack.translate(0.0d, -1.0d, -1.0d);else matrixStack.translate(0.0d, -0.5d, -0.25d);}, false)).castAndApply(rendererMap.get(EntityType.MULE));
			if(ClientConfigHolder.CLIENT.donkey.get()) (new RendererCast<>(ExtendedHorseArmorChestsModel::new, Constants.HORSE_ARMOR_LAYERS, (matrixStack, child) -> {matrixStack.rotate(Vector3f.XP.rotationDegrees(90.0f));if(child) matrixStack.translate(0.0d, -1.0d, -1.0d);else matrixStack.translate(0.0d, -0.5d, -0.25d);}, false)).castAndApply(rendererMap.get(EntityType.DONKEY));
			if(ClientConfigHolder.CLIENT.llama.get()) (new RendererCast<>(ExtendedLlamaModel::new, Constants.HORSE_ARMOR_LAYERS, (matrixStack, child) -> {matrixStack.rotate(Vector3f.XP.rotationDegrees(90.0f));if(child) matrixStack.translate(0.0d, -1.0d, -1.4375d);else matrixStack.translate(0.0d, -0.5d, -0.25d);}, false)).castAndApply(rendererMap.get(EntityType.LLAMA));
			if(ClientConfigHolder.CLIENT.trader_llama.get()) (new RendererCast<>(ExtendedLlamaModel::new, Constants.HORSE_ARMOR_LAYERS, (matrixStack, child) -> {matrixStack.rotate(Vector3f.XP.rotationDegrees(90.0f));if(child) matrixStack.translate(0.0d, -1.0d, -1.4375d);else matrixStack.translate(0.0d, -0.5d, -0.25d);}, false)).castAndApply(rendererMap.get(EntityType.TRADER_LLAMA));
		});
	}
}
