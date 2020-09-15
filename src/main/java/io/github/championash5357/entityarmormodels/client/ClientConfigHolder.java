package io.github.championash5357.entityarmormodels.client;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;

public class ClientConfigHolder {

	//General Settings
	public final BooleanValue enableUpdateNotifications;
	
	//Layer Settings
	public final BooleanValue enableArmorLayer;
	public final BooleanValue enableHeldItemLayer;
	public final BooleanValue enableArrowLayer;
	public final BooleanValue enableHeadLayer;
	public final BooleanValue enableElytraLayer;
	public final BooleanValue enableBeeStingLayer;

	//Special Settings
	public final BooleanValue creeperLayerRenderingOrdered;
	public final BooleanValue endermanExpandedTexture;
	public final BooleanValue inSlimeGel;
	public final BooleanValue magmaCubeArmorLayerSanity;

	//Entity Settings
	public final BooleanValue bat;
	public final BooleanValue bee;
	public final BooleanValue blaze;
	public final BooleanValue cat;
	public final BooleanValue cave_spider;
	public final BooleanValue chicken;
	public final BooleanValue cod;
	public final BooleanValue cow;
	public final BooleanValue creeper;
	public final BooleanValue dolphin;
	public final BooleanValue donkey;
	public final BooleanValue drowned;
	public final BooleanValue elder_guardian;
	public final BooleanValue ender_dragon;
	public final BooleanValue enderman;
	public final BooleanValue endermite;
	public final BooleanValue evoker;
	public final BooleanValue fox;
	public final BooleanValue ghast;
	public final BooleanValue giant;
	public final BooleanValue guardian;
	public final BooleanValue hoglin;
	public final BooleanValue horse;
	public final BooleanValue husk;
	public final BooleanValue illusioner;
	public final BooleanValue iron_golem;
	public final BooleanValue llama;
	public final BooleanValue magma_cube;
	public final BooleanValue mooshroom;
	public final BooleanValue mule;
	public final BooleanValue ocelot;
	public final BooleanValue panda;
	public final BooleanValue parrot;
	public final BooleanValue phantom;
	public final BooleanValue pig;
	public final BooleanValue piglin;
	public final BooleanValue piglin_brute;
	public final BooleanValue pillager;
	public final BooleanValue polar_bear;
	public final BooleanValue pufferfish;
	public final BooleanValue rabbit;
	public final BooleanValue ravager;
	public final BooleanValue salmon;
	public final BooleanValue sheep;
	public final BooleanValue shulker;
	public final BooleanValue silverfish;
	public final BooleanValue skeleton_horse;
	public final BooleanValue skeleton;
	public final BooleanValue slime;
	public final BooleanValue snow_golem;
	public final BooleanValue spider;
	public final BooleanValue squid;
	public final BooleanValue stray;
	public final BooleanValue trader_llama;
	public final BooleanValue tropical_fish;
	public final BooleanValue turtle;
	public final BooleanValue vex;
	public final BooleanValue villager;
	public final BooleanValue vindicator;
	public final BooleanValue wandering_trader;
	public final BooleanValue witch;
	public final BooleanValue wither;
	public final BooleanValue wither_skeleton;
	public final BooleanValue wolf;
	public final BooleanValue zoglin;
	public final BooleanValue zombie_horse;
	public final BooleanValue zombie;
	public final BooleanValue zombified_piglin;
	public final BooleanValue zombie_villager;
	public final BooleanValue strider;

	public ClientConfigHolder(final ForgeConfigSpec.Builder builder) {
		builder.comment("Entity Armor Models Configurations").push("eam");

		builder.comment("General Settings").push("general");
		
		enableUpdateNotifications = builder
				.comment("Set this to true if you want update notifications about the mod on world load.")
				.translation("entityarmormodels.configgui.enableupdatenotifications")
				.define("enableUpdateNotifications", true);
		
		builder.pop();
		builder.comment("Layer Settings").push("layer");

		enableArmorLayer = builder
				.comment("Set this to true if you want the armor layer to be enabled.",
						"This only affects vanilla mobs. Modded mobs are handled through their own mod.",
						"Do note that the game must be restarted for the change to take place.")
				.translation("entityarmormodels.configgui.enablearmorlayer")
				.define("enableArmorLayer", true);

		enableHeldItemLayer = builder
				.comment("Set this to true if you want the held item layer to be enabled.",
						"This only affects vanilla mobs. Modded mobs are handled through their own mod.",
						"Do note that the game must be restarted for the change to take place.")
				.translation("entityarmormodels.configgui.enablehelditemlayer")
				.define("enableHeldItemLayer", false);

		enableArrowLayer = builder
				.comment("Set this to true if you want the arrow layer to be enabled.",
						"This only affects vanilla mobs. Modded mobs are handled through their own mod.",
						"Do note that the game must be restarted for the change to take place.")
				.translation("entityarmormodels.configgui.enablearrowlayer")
				.define("enableArrowLayer", true);

		enableHeadLayer = builder
				.comment("Set this to true if you want the head layer to be enabled.",
						"This only affects vanilla mobs. Modded mobs are handled through their own mod.",
						"Do note that the game must be restarted for the change to take place.")
				.translation("entityarmormodels.configgui.enableheadlayer")
				.define("enableHeadLayer", true);

		enableElytraLayer = builder
				.comment("Set this to true if you want the elytra layer to be enabled.",
						"This only affects vanilla mobs. Modded mobs are handled through their own mod.",
						"Do note that the game must be restarted for the change to take place.")
				.translation("entityarmormodels.configgui.enableelytralayer")
				.define("enableElytraLayer", true);

		enableBeeStingLayer = builder
				.comment("Set this to true if you want the bee sting layer to be enabled.",
						"This only affects vanilla mobs. Modded mobs are handled through their own mod.",
						"Do note that the game must be restarted for the change to take place.")
				.translation("entityarmormodels.configgui.enablebeestinglayer")
				.define("enableBeeStingLayer", true);

		builder.pop();
		builder.comment("Entity Settings", "Set true to enable the layers.").push("entity");

		bat = builder
				.translation("entityarmormodels.configgui.bat")
				.define("bat", true);

		bee = builder
				.translation("entityarmormodels.configgui.bee")
				.define("bee", true);

		blaze = builder
				.translation("entityarmormodels.configgui.blaze")
				.define("blaze", true);

		cat = builder
				.translation("entityarmormodels.configgui.cat")
				.define("cat", true);

		cave_spider = builder
				.translation("entityarmormodels.configgui.cave_spider")
				.define("cave_spider", true);

		chicken = builder
				.translation("entityarmormodels.configgui.chicken")
				.define("chicken", true);

		cod = builder
				.translation("entityarmormodels.configgui.cod")
				.define("cod", true);

		cow = builder
				.translation("entityarmormodels.configgui.cow")
				.define("cow", true);

		creeper = builder
				.translation("entityarmormodels.configgui.creeper")
				.define("creeper", true);

		dolphin = builder
				.translation("entityarmormodels.configgui.dolphin")
				.define("dolphin", true);

		donkey = builder
				.translation("entityarmormodels.configgui.donkey")
				.define("donkey", true);

		drowned = builder
				.translation("entityarmormodels.configgui.drowned")
				.define("drowned", true);

		elder_guardian = builder
				.translation("entityarmormodels.configgui.elder_guardian")
				.define("elder_guardian", true);

		ender_dragon = builder
				.translation("entityarmormodels.configgui.ender_dragon")
				.define("ender_dragon", true);

		enderman = builder
				.translation("entityarmormodels.configgui.enderman")
				.define("enderman", true);

		endermite = builder
				.translation("entityarmormodels.configgui.endermite")
				.define("endermite", true);

		evoker = builder
				.translation("entityarmormodels.configgui.evoker")
				.define("evoker", true);

		fox = builder
				.translation("entityarmormodels.configgui.fox")
				.define("fox", true);

		ghast = builder
				.translation("entityarmormodels.configgui.ghast")
				.define("ghast", true);

		giant = builder
				.translation("entityarmormodels.configgui.giant")
				.define("giant", true);

		guardian = builder
				.translation("entityarmormodels.configgui.guardian")
				.define("guardian", true);

		hoglin = builder
				.translation("entityarmormodels.configgui.hoglin")
				.define("hoglin", true);

		horse = builder
				.translation("entityarmormodels.configgui.horse")
				.define("horse", true);

		husk = builder
				.translation("entityarmormodels.configgui.husk")
				.define("husk", true);

		illusioner = builder
				.translation("entityarmormodels.configgui.illusioner")
				.define("illusioner", true);

		iron_golem = builder
				.translation("entityarmormodels.configgui.iron_golem")
				.define("iron_golem", true);

		llama = builder
				.translation("entityarmormodels.configgui.llama")
				.define("llama", true);

		magma_cube = builder
				.translation("entityarmormodels.configgui.magma_cube")
				.define("magma_cube", true);

		mooshroom = builder
				.translation("entityarmormodels.configgui.mooshroom")
				.define("mooshroom", true);

		mule = builder
				.translation("entityarmormodels.configgui.mule")
				.define("mule", true);

		ocelot = builder
				.translation("entityarmormodels.configgui.ocelot")
				.define("ocelot", true);

		panda = builder
				.translation("entityarmormodels.configgui.panda")
				.define("panda", true);

		parrot = builder
				.translation("entityarmormodels.configgui.parrot")
				.define("parrot", true);

		phantom = builder
				.translation("entityarmormodels.configgui.phantom")
				.define("phantom", true);

		pig = builder
				.translation("entityarmormodels.configgui.pig")
				.define("pig", true);

		piglin = builder
				.translation("entityarmormodels.configgui.piglin")
				.define("piglin", true);
		
		piglin_brute = builder
				.translation("entityarmormodels.configgui.piglin_brute")
				.define("piglin_brute", true);

		pillager = builder
				.translation("entityarmormodels.configgui.pillager")
				.define("pillager", true);

		polar_bear = builder
				.translation("entityarmormodels.configgui.polar_bear")
				.define("polar_bear", true);

		pufferfish = builder
				.translation("entityarmormodels.configgui.pufferfish")
				.define("pufferfish", true);

		rabbit = builder
				.translation("entityarmormodels.configgui.rabbit")
				.define("rabbit", true);

		ravager = builder
				.translation("entityarmormodels.configgui.ravager")
				.define("ravager", true);

		salmon = builder
				.translation("entityarmormodels.configgui.salmon")
				.define("salmon", true);

		sheep = builder
				.translation("entityarmormodels.configgui.sheep")
				.define("sheep", true);

		shulker = builder
				.translation("entityarmormodels.configgui.shulker")
				.define("shulker", true);

		silverfish = builder
				.translation("entityarmormodels.configgui.silverfish")
				.define("silverfish", true);

		skeleton_horse = builder
				.translation("entityarmormodels.configgui.skeleton_horse")
				.define("skeleton_horse", true);

		skeleton = builder
				.translation("entityarmormodels.configgui.skeleton")
				.define("skeleton", true);

		slime = builder
				.translation("entityarmormodels.configgui.slime")
				.define("slime", true);

		snow_golem = builder
				.translation("entityarmormodels.configgui.snow_golem")
				.define("snow_golem", true);

		spider = builder
				.translation("entityarmormodels.configgui.spider")
				.define("spider", true);

		squid = builder
				.translation("entityarmormodels.configgui.squid")
				.define("squid", true);

		stray = builder
				.translation("entityarmormodels.configgui.stray")
				.define("stray", true);

		trader_llama = builder
				.translation("entityarmormodels.configgui.trader_llama")
				.define("trader_llama", true);

		tropical_fish = builder
				.translation("entityarmormodels.configgui.tropical_fish")
				.define("tropical_fish", true);

		turtle = builder
				.translation("entityarmormodels.configgui.turtle")
				.define("turtle", true);

		vex = builder
				.translation("entityarmormodels.configgui.vex")
				.define("vex", true);

		villager = builder
				.translation("entityarmormodels.configgui.villager")
				.define("villager", true);

		vindicator = builder
				.translation("entityarmormodels.configgui.vindicator")
				.define("vindicator", true);

		wandering_trader = builder
				.translation("entityarmormodels.configgui.wandering_trader")
				.define("wandering_trader", true);

		witch = builder
				.translation("entityarmormodels.configgui.witch")
				.define("witch", true);

		wither = builder
				.translation("entityarmormodels.configgui.wither")
				.define("wither", true);

		wither_skeleton = builder
				.translation("entityarmormodels.configgui.wither_skeleton")
				.define("wither_skeleton", true);

		wolf = builder
				.translation("entityarmormodels.configgui.wolf")
				.define("wolf", true);

		zoglin = builder
				.translation("entityarmormodels.configgui.zoglin")
				.define("zoglin", true);

		zombie_horse = builder
				.translation("entityarmormodels.configgui.zombie_horse")
				.define("zombie_horse", true);

		zombie = builder
				.translation("entityarmormodels.configgui.zombie")
				.define("zombie", true);

		zombified_piglin = builder
				.translation("entityarmormodels.configgui.zombified_piglin")
				.define("zombified_piglin", true);

		zombie_villager = builder
				.translation("entityarmormodels.configgui.zombie_villager")
				.define("zombie_villager", true);

		strider = builder
				.translation("entityarmormodels.configgui.strider")
				.define("strider", true);

		builder.pop();
		builder.comment("Special Settings").push("special");

		endermanExpandedTexture = builder
				.comment("Set this to true if you want Enderman to be rendered using the expanded texture rather than the vanilla one.",
						"By default, Enderman use the space for arms and legs as the same texture so they will all have boots renderered onto them.",
						"This fixes that issue by moving the arms texture to another layer.",
						"Do note that the game must be restarted for the change to take place.")
				.translation("entityarmormodels.configgui.endermanexpandedtexture")
				.define("endermanExpandedTexture", true);

		creeperLayerRenderingOrdered = builder
				.comment("Set this to true if you want Creeper layers to be ordered correctly.",
						"All this will do is fix a rendering issue when a charged creeper wears armor.",
						"Since this rendering issue looks like a cool effect, I made it a config option.",
						"Do note that the game must be restarted for the change to take place.")
				.translation("entityarmormodels.configgui.creeperrendering")
				.define("creeperLayerRenderingOrdered", true);

		inSlimeGel = builder
				.comment("Set this to true if you want added layers to be located inside the slime gel.",
						"Do note that the game must be restarted for the change to take place.")
				.translation("entityarmormodels.configgui.inslimegel")
				.define("inSlimeGel", false);

		magmaCubeArmorLayerSanity = builder
				.comment("Set this to true if you want the armor layer to split as the magma cube separates.",
						"Do note that the game must be restarted for the change to take place.")
				.translation("entityarmormodels.configgui.magmacubearmorlayersanity")
				.define("magmaCubeArmorLayerSanity", true);

		builder.pop();
		builder.pop();
	}

	public static final ForgeConfigSpec CLIENT_SPEC;
	public static final ClientConfigHolder CLIENT;
	static {
		final Pair<ClientConfigHolder, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ClientConfigHolder::new);
		CLIENT_SPEC = specPair.getRight();
		CLIENT = specPair.getLeft();
	}
}
