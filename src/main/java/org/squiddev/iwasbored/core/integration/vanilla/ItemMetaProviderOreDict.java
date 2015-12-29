package org.squiddev.iwasbored.core.integration.vanilla;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import org.squiddev.iwasbored.core.api.provider.AbstractProvider;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides Ore Dictionary lookup for items
 */
public class ItemMetaProviderOreDict extends AbstractProvider<ItemStack, Map<String, Object>> {
	@Override
	public Map<String, Object> get(ItemStack stack) {
		int[] oreIds = OreDictionary.getOreIDs(stack);
		if (oreIds.length > 0) {
			Map<String, Object> result = new HashMap<String, Object>();
			Map<String, Boolean> list = new HashMap<String, Boolean>();
			result.put("ores", list);

			for (int id : oreIds) {
				list.put(OreDictionary.getOreName(id), true);
			}

			return result;
		} else {
			return Collections.emptyMap();
		}
	}
}
