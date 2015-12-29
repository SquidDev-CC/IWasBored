package org.squiddev.iwasbored.core.integration.vanilla;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.squiddev.iwasbored.core.api.provider.NamespacedMetaProvider;

import java.util.HashMap;
import java.util.Map;

/**
 * Displays classes for tools
 */
public class ItemMetaProviderHarvestLevel extends NamespacedMetaProvider<ItemStack> {
	@Override
	public String getNamespace() {
		return "toolClass";
	}

	@Override
	public Object getMeta(ItemStack stack) {
		Item item = stack.getItem();
		if (!item.getToolClasses(stack).isEmpty()) {
			Map<String, Object> types = new HashMap<String, Object>();

			for (String tool : item.getToolClasses(stack)) {
				types.put(tool, item.getHarvestLevel(stack, tool));
			}

			return types;
		} else {
			return null;
		}
	}
}
