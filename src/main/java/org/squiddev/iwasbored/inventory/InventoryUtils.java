package org.squiddev.iwasbored.inventory;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.squiddev.iwasbored.DebugLogger;

import java.util.HashMap;
import java.util.Map;

public final class InventoryUtils {
	public static Map<String, Object> getBasicProperties(ItemStack stack) {
		HashMap<String, Object> data = new HashMap<String, Object>();

		DebugLogger.debug("Name is " + Item.itemRegistry.getNameForObject(stack.getItem()));
		data.put("name", Item.itemRegistry.getNameForObject(stack.getItem()));
		data.put("damage", stack.getItemDamage());
		data.put("count", stack.stackSize);

		return data;
	}
}
