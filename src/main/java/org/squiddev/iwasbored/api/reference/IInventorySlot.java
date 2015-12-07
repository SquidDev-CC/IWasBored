package org.squiddev.iwasbored.api.reference;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

/**
 * A slot in the inventory
 */
public interface IInventorySlot {
	/**
	 * Get the slot this item exists in
	 *
	 * @return The slot this item exists in
	 */
	int slot();

	/**
	 * The item stack at this slot.
	 *
	 * @return The item stack at this slot.
	 */
	ItemStack stack();

	/**
	 * Get the inventory that this item refers to
	 *
	 * @return The inventory
	 */
	IReference<IInventory> inventory();

	/**
	 * Replace this item with another item
	 *
	 * @param stack The item to replace it with
	 */
	void replace(ItemStack stack);
}
