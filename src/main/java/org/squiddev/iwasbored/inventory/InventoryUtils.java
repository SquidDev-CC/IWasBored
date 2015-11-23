package org.squiddev.iwasbored.inventory;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public final class InventoryUtils {
	public static Map<String, Object> getBasicProperties(ItemStack stack) {
		HashMap<String, Object> data = new HashMap<String, Object>();

		data.put("name", Item.itemRegistry.getNameForObject(stack.getItem()));
		data.put("damage", stack.getItemDamage());
		data.put("count", stack.stackSize);

		return data;
	}
}
