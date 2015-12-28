package org.squiddev.iwasbored.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.squiddev.iwasbored.IWasBored;

/**
 * Helper methods
 */
public final class Helpers {
	private Helpers() {
		throw new RuntimeException("Cannot create Helpers");
	}

	@SideOnly(Side.CLIENT)
	public static void setupModel(Item item, int damage, String name) {
		name = IWasBored.RESOURCE_DOMAIN + ":" + name;

		ModelResourceLocation res = new ModelResourceLocation(name, "inventory");
		ModelBakery.addVariantName(item, name);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, damage, res);
	}
}
