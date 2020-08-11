package io.github.championash5357.entityarmormodels.client.renderer.entity.model.vanilla;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import com.google.common.collect.Lists;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public abstract class ExtendedAgeableModel<T extends LivingEntity, M extends AgeableModel<T>> extends AgeableModel<T> implements IVanillaEntityModel<T, M>{

	private List<ModelRenderer> modelRenderers = Lists.newArrayList();
	private final Method getHeadParts, getBodyParts;
	
	protected ExtendedAgeableModel(Class<?> modelClass, String unmappedHeadPartsFunc, String unmappedBodyPartsFunc) {
		this(modelClass, unmappedHeadPartsFunc, unmappedBodyPartsFunc, false, 5.0f, 2.0f);
	}
	
	protected ExtendedAgeableModel(Class<?> modelClass, String unmappedHeadPartsFunc, String unmappedBodyPartsFunc, boolean isChildHeadScaledIn, float childHeadOffsetYIn, float childHeadOffsetZIn) {
		this(modelClass, unmappedHeadPartsFunc, unmappedBodyPartsFunc, isChildHeadScaledIn, childHeadOffsetYIn, childHeadOffsetZIn, 2.0F, 2.0F, 24.0F);
	}

	protected ExtendedAgeableModel(Class<?> modelClass, String unmappedHeadPartsFunc, String unmappedBodyPartsFunc, boolean isChildHeadScaledIn, float childHeadOffsetYIn, float childHeadOffsetZIn, float childHeadScaleIn, float childBodyScaleIn, float childBodyOffsetYIn) {
		this(RenderType::getEntityCutoutNoCull, modelClass, unmappedHeadPartsFunc, unmappedBodyPartsFunc, isChildHeadScaledIn, childHeadOffsetYIn, childHeadOffsetZIn, childHeadScaleIn, childBodyScaleIn, childBodyOffsetYIn);
	}

	protected ExtendedAgeableModel(Function<ResourceLocation, RenderType> func, Class<?> modelClass, String unmappedHeadPartsFunc, String unmappedBodyPartsFunc, boolean isChildHeadScaledIn, float childHeadOffsetYIn, float childHeadOffsetZIn, float childHeadScaleIn, float childBodyScaleIn, float childBodyOffsetYIn) {
		super(func, isChildHeadScaledIn, childHeadOffsetYIn, childHeadOffsetZIn, childHeadScaleIn, childBodyScaleIn, childBodyOffsetYIn);
		this.getHeadParts = ObfuscationReflectionHelper.findMethod(modelClass, unmappedHeadPartsFunc);
		this.getBodyParts = ObfuscationReflectionHelper.findMethod(modelClass, unmappedBodyPartsFunc);
	}
	
	@Override
	public void copyAttributesOfModel(M model) {
		model.copyModelAttributesTo(this);
		try {
			Object resultHead = getHeadParts.invoke(model), resultBody = getBodyParts.invoke(model);
			if(resultHead instanceof Iterable<?> && resultBody instanceof Iterable<?>) {
				Iterator<ModelRenderer> it = this.getHeadParts().iterator();
				for(@SuppressWarnings("unchecked")
				Iterator<ModelRenderer> itm = ((Iterable<ModelRenderer>) resultHead).iterator(); itm.hasNext();) {
					if(it.hasNext()) {
						it.next().copyModelAngles(itm.next());
					} else break;
				}
				
				it = this.getBodyParts().iterator();
				for(@SuppressWarnings("unchecked")
				Iterator<ModelRenderer> itm = ((Iterable<ModelRenderer>) resultBody).iterator(); itm.hasNext();) {
					if(it.hasNext()) {
						it.next().copyModelAngles(itm.next());
					} else break;
				}
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}
	
	@Override
	public void accept(ModelRenderer renderer) {
		super.accept(renderer);
		if(this.modelRenderers == null) {
			this.modelRenderers = Lists.newArrayList();
		}
		this.modelRenderers.add(renderer);
	}

	@Override
	public ModelRenderer getRandomModelRenderer(Random randomIn) {
		return this.modelRenderers.get(randomIn.nextInt(this.modelRenderers.size()));
	}
}
