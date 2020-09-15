package io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.vanilla;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.renderer.entity.model.HorseArmorChestsModel;
import net.minecraft.client.renderer.entity.model.HorseModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.horse.AbstractChestedHorseEntity;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.util.ResourceLocation;

/**
 * An extended version of {@link HorseArmorChestsModel}. 
 * Custom armor models are already supported 
 * by Minecraft and Forge for this. You should 
 * extend {@link HorseModel} for custom armors 
 * relating to Mules and Donkeys.
 * */
public class ExtendedHorseArmorChestsModel<T extends AbstractChestedHorseEntity> extends ExtendedHorseModel<T> {

	private static final Map<String, ResourceLocation> ARMOR_TEXTURE_RES_MAP = new HashMap<>();
	
	protected final ModelRenderer leftChest;
	protected final ModelRenderer rightChest;

	public ExtendedHorseArmorChestsModel(float modelSize) {
		this(modelSize, 64, 64);
	}

	public ExtendedHorseArmorChestsModel(float modelSize, int textureWidth, int textureHeight) {
		super(modelSize, textureWidth, textureHeight);
		this.leftChest = new ModelRenderer(this, 26, 21);
		this.leftChest.addBox(-4.0F, 0.0F, -2.0F, 8.0F, 8.0F, 3.0F, modelSize);
		this.rightChest = new ModelRenderer(this, 26, 21);
		this.rightChest.addBox(-4.0F, 0.0F, -2.0F, 8.0F, 8.0F, 3.0F, modelSize);
		this.leftChest.rotateAngleY = (-(float)Math.PI / 2F);
		this.rightChest.rotateAngleY = ((float)Math.PI / 2F);
		this.leftChest.setRotationPoint(6.0F, -8.0F, 0.0F);
		this.rightChest.setRotationPoint(-6.0F, -8.0F, 0.0F);
		this.body.addChild(this.leftChest);
		this.body.addChild(this.rightChest);
	}

	@Override
	protected void createEars(ModelRenderer head, float modelSize) {
		this.leftEar = new ModelRenderer(this, 0, 12);
		this.leftEar.addBox(-1.0F, -7.0F, 0.0F, 2.0F, 7.0F, 1.0F, modelSize);
		this.leftEar.setRotationPoint(1.25F, -10.0F, 4.0F);
		this.rightEar = new ModelRenderer(this, 0, 12);
		this.rightEar.addBox(-1.0F, -7.0F, 0.0F, 2.0F, 7.0F, 1.0F, modelSize);
		this.rightEar.setRotationPoint(-1.25F, -10.0F, 4.0F);
		this.leftEar.rotateAngleX = 0.2617994F;
		this.leftEar.rotateAngleZ = 0.2617994F;
		this.rightEar.rotateAngleX = 0.2617994F;
		this.rightEar.rotateAngleZ = -0.2617994F;
		head.addChild(this.leftEar);
		head.addChild(this.rightEar);
	}

	@Override
	public void setRotationAngles(T entity, float limbSwing, float limbSwingAmount, float ageTicks, float netHeadYaw, float headPitch) {
		super.setRotationAngles(entity, limbSwing, limbSwingAmount, ageTicks, netHeadYaw, headPitch);
		if (entity.hasChest()) {
			this.leftChest.showModel = true;
			this.rightChest.showModel = true;
		} else {
			this.leftChest.showModel = false;
			this.rightChest.showModel = false;
		}
	}
	
	@Override
	public ResourceLocation getHorseArmorLocation(EntityType<?> type, HorseArmorItem item) {
		String str = String.format("%s:textures/models/armor/%s/%s.png", item.getRegistryName().getNamespace(), type.getRegistryName().getNamespace() + "_" + type.getRegistryName().getPath(), item.getRegistryName().getPath());
		return ARMOR_TEXTURE_RES_MAP.computeIfAbsent(str, s -> new ResourceLocation(s));
	}
}
