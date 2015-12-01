package org.squiddev.iwasbored.api.reference;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

/**
 * Reference to a item
 */
public interface ItemReference extends IReferenceWithEntity<ItemStack> {
	/**
	 * Get the inventory that this item refers to
	 *
	 * @return The inventory
	 */
	IInventory getInventory();

	/**
	 * Replace this item with another item
	 *
	 * @param stack The item to replace it with
	 */
	void replace(ItemStack stack);
}
