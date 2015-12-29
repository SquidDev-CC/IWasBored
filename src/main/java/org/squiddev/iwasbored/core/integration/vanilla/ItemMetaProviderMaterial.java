package org.squiddev.iwasbored.core.integration.vanilla;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import org.squiddev.iwasbored.core.api.provider.NamespacedMetaProvider;

/**
 * Displays tool's material
 */
public class ItemMetaProviderMaterial extends NamespacedMetaProvider<ItemStack> {
	@Override
	public String getNamespace() {
		return "material";
	}

	@Override
	public Object getMeta(ItemStack stack) {
		Item item = stack.getItem();
		if (item instanceof ItemTool) {
			return ((ItemTool) item).getToolMaterialName();
		} else if (item instanceof ItemSword) {
			return ((ItemSword) item).getToolMaterialName();
		} else {
			return null;
		}
	}
}
