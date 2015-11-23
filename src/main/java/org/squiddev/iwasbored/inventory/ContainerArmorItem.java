package org.squiddev.iwasbored.inventory;

import baubles.common.lib.PlayerHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

/**
 * Basic container for a single item
 */
public class ContainerArmorItem extends Container {
	public final ItemStack stack;
	public final int slot;
	public final boolean baubles;

	public ContainerArmorItem(ArmorItem item) {
		this.slot = item.slot;
		this.stack = item.stack.copy();
		this.baubles = item.baubles;
	}

	public ItemStack getStack() {
		return this.stack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		if (player != null && player.isEntityAlive()) {
			IInventory inventory = baubles ? PlayerHandler.getPlayerBaubles(player) : player.inventory;
			ItemStack other = inventory.getStackInSlot(slot);
			if (other == stack || other != null && stack != null && other.getItem() == stack.getItem()) {
				return true;
			}
		}

		return false;
	}
}
