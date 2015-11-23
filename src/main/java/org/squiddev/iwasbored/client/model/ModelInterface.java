package org.squiddev.iwasbored.client.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * Model for the neural interface
 */
public class ModelInterface extends ModelBiped {
	public final ModelRenderer main;
	public final ModelRenderer side;

	private static ModelInterface instance;

	public static ModelInterface get() {
		if (instance == null) return instance = new ModelInterface();
		return instance;
	}

	public ModelInterface() {
		this.textureWidth = 21;
		this.textureHeight = 9;
		this.main = new ModelRenderer(this, 0, 0);
		this.main.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.main.addBox(-0.1F, -5.5F, -5.1F, 5, 3, 1, 0.0F);
		this.side = new ModelRenderer(this, 5, 0);
		this.side.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.side.addBox(3.9F, -5.5F, -4.1F, 1, 2, 7, 0.0F);

		bipedHeadwear = new ModelRenderer(this, 0, 0);
		bipedHeadwear.addChild(main);
		bipedHeadwear.addChild(side);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		bipedHeadwear.render(f5);
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
