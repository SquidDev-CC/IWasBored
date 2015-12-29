package org.squiddev.iwasbored.core.integration.vanilla;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import org.squiddev.iwasbored.core.api.provider.AbstractProvider;

import java.util.Collections;
import java.util.Map;

/**
 * Displays tool's material
 */
public class ItemMetaProviderMaterial extends AbstractProvider<ItemStack, Map<String, Object>> {
	@Override
	public Map<String, Object> get(ItemStack stack) {
		Item item = stack.getItem();
		if (item instanceof ItemTool) {
			return Collections.<String, Object>singletonMap("material", ((ItemTool) item).getToolMaterialName());
		} else if (item instanceof ItemSword) {
			return Collections.<String, Object>singletonMap("material", ((ItemSword) item).getToolMaterialName());
		}

		return Collections.emptyMap();
	}
}
