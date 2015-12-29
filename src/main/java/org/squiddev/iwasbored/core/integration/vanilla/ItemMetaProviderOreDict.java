package org.squiddev.iwasbored.core.integration.vanilla;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import org.squiddev.iwasbored.core.api.provider.NamespacedMetaProvider;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides Ore Dictionary lookup for items
 */
public class ItemMetaProviderOreDict extends NamespacedMetaProvider<ItemStack> {
	@Override
	public String getNamespace() {
		return "ores";
	}

	@Override
	public Object getMeta(ItemStack stack) {
		int[] oreIds = OreDictionary.getOreIDs(stack);
		if (oreIds.length > 0) {
			Map<String, Boolean> list = new HashMap<String, Boolean>();

			for (int id : oreIds) {
				list.put(OreDictionary.getOreName(id), true);
			}

			return list;
		} else {
			return null;
		}
	}
}
