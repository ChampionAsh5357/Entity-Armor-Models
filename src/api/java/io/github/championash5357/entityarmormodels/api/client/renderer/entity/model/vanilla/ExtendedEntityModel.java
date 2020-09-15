package io.github.championash5357.entityarmormodels.api.client.renderer.entity.model.vanilla;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public abstract class ExtendedEntityModel<T extends LivingEntity, M extends EntityModel<T>> extends EntityModel<T> implements IVanillaEntityModel<T, M> {
	
	private List<ModelRenderer> modelRenderers = Lists.newArrayList();
	
	@Override
	public void accept(ModelRenderer renderer) {
		super.accept(renderer);
		if(this.modelRenderers == null) {
			this.modelRenderers = Lists.newArrayList();
		}
		this.modelRenderers.add(renderer);
	}
	
	@Override
	public ModelRenderer getRandomModelRenderer(Random random) {
		return this.modelRenderers.get(random.nextInt(this.modelRenderers.size()));
	}
}
