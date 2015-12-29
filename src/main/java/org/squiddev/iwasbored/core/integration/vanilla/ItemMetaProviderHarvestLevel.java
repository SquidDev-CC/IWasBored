package org.squiddev.iwasbored.core.integration.vanilla;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.squiddev.iwasbored.core.api.provider.AbstractProvider;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Displays classes for tools
 */
public class ItemMetaProviderHarvestLevel extends AbstractProvider<ItemStack, Map<String, Object>> {
	@Override
	public Map<String, Object> get(ItemStack stack) {
		Item item = stack.getItem();
		if (!item.getToolClasses(stack).isEmpty()) {
			Map<String, Object> properties = new HashMap<String, Object>();
			Map<String, Object> types = new HashMap<String, Object>();
			properties.put("toolClass", types);

			for (String tool : item.getToolClasses(stack)) {
				types.put(tool, item.getHarvestLevel(stack, tool));
			}

			return properties;
		}

		return Collections.emptyMap();
	}
}
