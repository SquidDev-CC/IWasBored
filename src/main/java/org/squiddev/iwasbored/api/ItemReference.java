package org.squiddev.iwasbored.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

/**
 * Reference to a item
 */
public interface ItemReference extends IReference<ItemStack> {
	/**
	 * Get the inventory that this item refers to
	 *
	 * @return The inventory
	 */
	IInventory getInventory();

	/**
	 * The player that that owns this item.
	 *
	 * @return The player that holds this item, or {@code null} if there is no player.
	 */
	EntityPlayer getPlayer();

	/**
	 * Replace this item with another item
	 *
	 * @param stack The item to replace it with
	 */
	void replace(ItemStack stack);
}
