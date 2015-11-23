package org.squiddev.iwasbored.api.meta;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

/**
 * Reference to a item
 */
public interface ItemReference {
	/**
	 * Get the inventory that this item refers to
	 *
	 * @return The inventory
	 */
	IInventory getInventory();

	/**
	 * If the item is still there
	 *
	 * @return If this item is still here.
	 */
	boolean isValid();

	/**
	 * Get the item stack
	 *
	 * @return The item stack. This will be {@code null} if the item has moved (well, actually if the item is not the same as it was before).
	 */
	ItemStack getStack();

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
