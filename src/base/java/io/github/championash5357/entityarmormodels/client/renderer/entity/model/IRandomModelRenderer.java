package io.github.championash5357.entityarmormodels.client.renderer.entity.model;

import java.util.Random;

import io.github.championash5357.entityarmormodels.client.renderer.entity.layers.LivingEntityArmorLayer;
import io.github.championash5357.entityarmormodels.client.renderer.entity.layers.LivingEntityBeeStingerLayer;
import net.minecraft.client.renderer.model.ModelRenderer;

/**
 * Added to a model class to allow a random {@link ModelRenderer}
 * to return when called. Used in {@link LivingEntityArmorLayer} and
 * {@link LivingEntityBeeStingerLayer} to attach their respective values 
 * when hit.
 * */
public interface IRandomModelRenderer {

	/**
	 * Gets a random {@link ModelRenderer} from the model.
	 * 
	 * @param randomIn
	 * 			An instance of random.
	 * @return A {@link ModelRenderer} in the model.
	 * */
	ModelRenderer getRandomModelRenderer(Random randomIn);
}
