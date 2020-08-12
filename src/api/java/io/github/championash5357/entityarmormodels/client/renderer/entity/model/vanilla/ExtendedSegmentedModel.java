package io.github.championash5357.entityarmormodels.client.renderer.entity.model.vanilla;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import com.google.common.collect.Lists;

import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public abstract class ExtendedSegmentedModel<T extends LivingEntity, M extends SegmentedModel<T>> extends SegmentedModel<T> implements IVanillaEntityModel<T, M> {

	private List<ModelRenderer> modelRenderers = Lists.newArrayList();
	private static final Field CHILD_MODELS = ObfuscationReflectionHelper.findField(ModelRenderer.class, "field_78805_m");
	
	protected ExtendedSegmentedModel() {
		this(RenderType::getEntityCutoutNoCull);
	}
	
	protected ExtendedSegmentedModel(Function<ResourceLocation, RenderType> func) {
		super(func);
	}

	@Override
	public void copyAttributesOfModel(M model) {
		model.copyModelAttributesTo(this);
		Iterator<ModelRenderer> it = this.getParts().iterator();
		for(Iterator<ModelRenderer> itm = model.getParts().iterator(); itm.hasNext();) {
			if(it.hasNext()) {
				it.next().copyModelAngles(itm.next());
			} else break;
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
	
	@SuppressWarnings("unchecked")
	protected ObjectList<ModelRenderer> getChildModels(ModelRenderer parentInstance) {
		try {
			return (ObjectList<ModelRenderer>) CHILD_MODELS.get(parentInstance);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}
