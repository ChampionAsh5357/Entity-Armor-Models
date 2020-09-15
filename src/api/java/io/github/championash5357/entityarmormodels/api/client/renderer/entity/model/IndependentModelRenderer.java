package io.github.championash5357.entityarmormodels.api.client.renderer.entity.model;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

/**
 * An extension of {@link ModelRenderer} that allows 
 * dynamic control of the child models instead of 
 * relying on the parent.
 * */
public class IndependentModelRenderer extends ModelRenderer {

	private static final Field CUBE_LIST = ObfuscationReflectionHelper.findField(ModelRenderer.class, "field_78804_l");
	private static final Method DO_RENDER = ObfuscationReflectionHelper.findMethod(ModelRenderer.class, "func_228306_a_", MatrixStack.Entry.class, IVertexBuilder.class, int.class, int.class, float.class, float.class, float.class, float.class);
	private final ObjectList<IndependentModelRenderer> childModels = new ObjectArrayList<>();

	public IndependentModelRenderer(Model model) {
		super(model);
	}

	public IndependentModelRenderer(Model model, int texOffX, int texOffY) {
		super(model, texOffX, texOffY);
	}
	
	public void addChild(IndependentModelRenderer renderer) {
		this.childModels.add(renderer);
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		if(!getCubeList(this).isEmpty() || !childModels.isEmpty()) {
			matrixStack.push();
			this.translateRotate(matrixStack);
			if(this.showModel) {
				try {
					DO_RENDER.invoke(this, matrixStack.getLast(), buffer, packedLight, packedOverlay, red, green, blue, alpha);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}

			for(ModelRenderer renderer : childModels) renderer.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			matrixStack.pop();
		}
	}

	@SuppressWarnings("unchecked")
	private ObjectList<ModelRenderer.ModelBox> getCubeList(ModelRenderer parentstance) {
		try {
			return (ObjectList<ModelRenderer.ModelBox>) CUBE_LIST.get(parentstance);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}
