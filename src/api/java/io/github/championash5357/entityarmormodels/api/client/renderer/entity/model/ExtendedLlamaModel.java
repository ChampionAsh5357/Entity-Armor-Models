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

package io.github.championash5357.entityarmormodels.api.client.renderer.entity.model;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.vanilla.ExtendedEntityModel;
import net.minecraft.client.renderer.entity.model.LlamaModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.horse.AbstractChestedHorseEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

/**
 * An extended version of {@link LlamaModel}. 
 * Extend this model to apply custom entity armors 
 * to an Llama.
 * */
public class ExtendedLlamaModel<T extends AbstractChestedHorseEntity> extends ExtendedEntityModel<T, LlamaModel<T>> {

	private static final Map<String, ResourceLocation> ARMOR_TEXTURE_RES_MAP = new HashMap<>();
	private final ImmutableList<ModelRenderer> parts;
	private static final ImmutableList<Field> VANILLA_PARTS = ImmutableList.of(
			ObfuscationReflectionHelper.findField(LlamaModel.class, "field_228273_a_"),
			ObfuscationReflectionHelper.findField(LlamaModel.class, "field_228274_b_"),
			ObfuscationReflectionHelper.findField(LlamaModel.class, "field_228275_f_"),
			ObfuscationReflectionHelper.findField(LlamaModel.class, "field_228276_g_"),
			ObfuscationReflectionHelper.findField(LlamaModel.class, "field_228277_h_"),
			ObfuscationReflectionHelper.findField(LlamaModel.class, "field_228278_i_"),
			ObfuscationReflectionHelper.findField(LlamaModel.class, "field_191226_i"),
			ObfuscationReflectionHelper.findField(LlamaModel.class, "field_191227_j"));

	protected final ModelRenderer head;
	protected final ModelRenderer body;
	protected final ModelRenderer legBackRight;
	protected final ModelRenderer legBackLeft;
	protected final ModelRenderer legFrontRight;
	protected final ModelRenderer legFrontLeft;
	protected final ModelRenderer chest1;
	protected final ModelRenderer chest2;

	public ExtendedLlamaModel(float modelSize) {
		this(modelSize, 128, 64);
	}

	public ExtendedLlamaModel(float modelSize, int textureWidth, int textureHeight) {
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;
		this.head = new ModelRenderer(this, 0, 0);
		this.head.addBox(-2.0F, -14.0F, -10.0F, 4.0F, 4.0F, 9.0F, modelSize);
		this.head.setRotationPoint(0.0F, 7.0F, -6.0F);
		this.head.setTextureOffset(0, 14).addBox(-4.0F, -16.0F, -6.0F, 8.0F, 18.0F, 6.0F, modelSize);
		this.head.setTextureOffset(17, 0).addBox(-4.0F, -19.0F, -4.0F, 3.0F, 3.0F, 2.0F, modelSize);
		this.head.setTextureOffset(17, 0).addBox(1.0F, -19.0F, -4.0F, 3.0F, 3.0F, 2.0F, modelSize);
		this.body = new ModelRenderer(this, 29, 0);
		this.body.addBox(-6.0F, -10.0F, -7.0F, 12.0F, 18.0F, 10.0F, modelSize);
		this.body.setRotationPoint(0.0F, 5.0F, 2.0F);
		this.chest1 = new ModelRenderer(this, 45, 28);
		this.chest1.addBox(-3.0F, 0.0F, 0.0F, 8.0F, 8.0F, 3.0F, modelSize);
		this.chest1.setRotationPoint(-8.5F, 3.0F, 3.0F);
		this.chest1.rotateAngleY = ((float)Math.PI / 2F);
		this.chest2 = new ModelRenderer(this, 45, 41);
		this.chest2.addBox(-3.0F, 0.0F, 0.0F, 8.0F, 8.0F, 3.0F, modelSize);
		this.chest2.setRotationPoint(5.5F, 3.0F, 3.0F);
		this.chest2.rotateAngleY = ((float)Math.PI / 2F);
		this.legBackRight = new ModelRenderer(this, 29, 29);
		this.legBackRight.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, modelSize);
		this.legBackRight.setRotationPoint(-2.5F, 10.0F, 6.0F);
		this.legBackLeft = new ModelRenderer(this, 29, 29);
		this.legBackLeft.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, modelSize);
		this.legBackLeft.setRotationPoint(2.5F, 10.0F, 6.0F);
		this.legFrontRight = new ModelRenderer(this, 29, 29);
		this.legFrontRight.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, modelSize);
		this.legFrontRight.setRotationPoint(-2.5F, 10.0F, -4.0F);
		this.legFrontLeft = new ModelRenderer(this, 29, 29);
		this.legFrontLeft.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, modelSize);
		this.legFrontLeft.setRotationPoint(2.5F, 10.0F, -4.0F);
		--this.legBackRight.rotationPointX;
		++this.legBackLeft.rotationPointX;
		this.legBackRight.rotationPointZ += 0.0F;
		this.legBackLeft.rotationPointZ += 0.0F;
		--this.legFrontRight.rotationPointX;
		++this.legFrontLeft.rotationPointX;
		--this.legFrontRight.rotationPointZ;
		--this.legFrontLeft.rotationPointZ;
		this.parts = ImmutableList.of(this.head, this.body, this.legBackRight, this.legBackLeft, this.legFrontRight, this.legFrontLeft, this.chest1, this.chest2);
	}

	@Override
	public void copyAttributesOfModel(LlamaModel<T> model) {
		model.copyModelAttributesTo(this);
		try {
			Iterator<ModelRenderer> it = this.parts.iterator();
			for(Iterator<Field> itm = VANILLA_PARTS.iterator(); itm.hasNext();) {
				if(it.hasNext()) {
					it.next().copyModelAngles((ModelRenderer) itm.next().get(model));
				} else break;
			}
		} catch (IllegalAccessException | IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setModelSlotVisible(T entity, EquipmentSlotType slotType) {}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		if (this.isChild) {
			matrixStack.push();
			matrixStack.scale(0.71428573F, 0.64935064F, 0.7936508F);
			matrixStack.translate(0.0D, 1.3125D, (double)0.22F);
			this.head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			matrixStack.pop();
			matrixStack.push();
			matrixStack.scale(0.625F, 0.45454544F, 0.45454544F);
			matrixStack.translate(0.0D, 2.0625D, 0.0D);
			this.body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			matrixStack.pop();
			matrixStack.push();
			matrixStack.scale(0.45454544F, 0.41322312F, 0.45454544F);
			matrixStack.translate(0.0D, 2.0625D, 0.0D);
			ImmutableList.of(this.legBackRight, this.legBackLeft, this.legFrontRight, this.legFrontLeft, this.chest1, this.chest2).forEach((renderer) -> {
				renderer.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			});
			matrixStack.pop();
		} else {
			this.parts.forEach((renderer) -> {
				renderer.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			});
		}

	}

	@Override
	public void preRender(MatrixStack stack, boolean isChild) {
		if(isChild) stack.translate(0.0d, -0.25d, -0.0625d);
		else stack.translate(0.0d, -0.5d, -0.25d);
	}

	@Override
	public void offset(LivingEntity entity, boolean wearingArmor, MatrixStack stack, boolean isChild) {
		if(isChild) stack.translate(0.0d, -0.25d, -0.25d);
		else stack.translate(0.0d, -0.25d, 0.0d);
	}

	@Override
	public ModelRenderer getModelHead() {
		return this.head;
	}

	@Override
	public ModelRenderer getBody() {
		return this.body;
	}

	@Override
	public ResourceLocation getHorseArmorLocation(EntityType<?> type, HorseArmorItem item) {
		String str = String.format("%s:textures/models/armor/%s/%s.png", item.getRegistryName().getNamespace(), type.getRegistryName().getNamespace() + "_" + type.getRegistryName().getPath(), item.getRegistryName().getPath());
		return ARMOR_TEXTURE_RES_MAP.computeIfAbsent(str, s -> new ResourceLocation(s));
	}

	@Override
	public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		boolean flag = !entityIn.isChild() && entityIn.hasChest();
		this.chest1.showModel = flag;
		this.chest2.showModel = flag;
	}
}
