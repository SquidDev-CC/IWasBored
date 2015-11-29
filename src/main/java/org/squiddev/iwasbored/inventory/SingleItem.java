package org.squiddev.iwasbored.inventory;

import com.google.common.base.Preconditions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import org.squiddev.iwasbored.api.ItemReference;

public final class SingleItem implements ItemReference {
	public final IInventory inventory;
	public final int slot;
	public final EntityPlayer player;

	private final ItemStack stack;

	public SingleItem(IInventory inventory, int slot, EntityPlayer player) {
		Preconditions.checkNotNull(inventory, "inventory cannot be null");
		this.inventory = inventory;
		this.slot = slot;
		this.player = player;
		this.stack = inventory.getStackInSlot(slot);
	}

	@Override
	public ItemStack get() {
		if (player != null && !player.isEntityAlive()) return null;

		ItemStack current = inventory.getStackInSlot(slot);
		return current == stack || current != null && stack != null && current.getItem() == stack.getItem() ? current : null;
	}

	@Override
	public EntityPlayer getPlayer() {
		return player;
	}

	@Override
	public void replace(ItemStack stack) {
		if (!isValid()) return;

		if (stack != null && stack.stackSize == 0) stack = null;
		inventory.setInventorySlotContents(slot, stack);
	}

	@Override
	public IInventory getInventory() {
		return inventory;
	}

	@Override
	public boolean isValid() {
		return get() != null;
	}
}
