package org.squiddev.iwasbored.core.integration.vanilla;

import net.minecraft.item.ItemStack;
import org.squiddev.iwasbored.core.api.provider.AbstractProvider;
import org.squiddev.iwasbored.lib.inventory.InventoryUtils;

import java.util.Map;

/**
 * Adds basic properties for item stacks.
 */
public class ItemMetaProviderBasic extends AbstractProvider<ItemStack, Map<String, Object>> {
	@Override
	public Map<String, Object> get(ItemStack stack) {
		Map<String, Object> data = InventoryUtils.getBasicProperties(stack);

		String display = stack.getDisplayName();
		data.put("displayName", display == null || display.length() == 0 ? stack.getUnlocalizedName() : display);
		data.put("rawName", stack.getUnlocalizedName());

		data.put("maxCount", stack.getMaxStackSize());
		data.put("maxDamage", stack.getMaxDamage());

		if (stack.getItem().showDurabilityBar(stack)) {
			data.put("durability", stack.getItem().getDurabilityForDisplay(stack));
		}

		return data;
	}
}
