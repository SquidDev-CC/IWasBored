package org.squiddev.iwasbored.impl.reference;

import com.google.common.base.Preconditions;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import org.squiddev.iwasbored.api.reference.IInventorySlot;
import org.squiddev.iwasbored.api.reference.IReference;

public final class InventorySlot implements IInventorySlot {
	public final IReference<IInventory> inventory;
	public final int slot;

	public InventorySlot(IReference<IInventory> inventory, int slot) {
		Preconditions.checkNotNull(inventory, "inventory cannot be null");
		Preconditions.checkArgument(inventory.isValid(), "inventory must be valid");

		this.inventory = inventory;
		this.slot = slot;
	}

	@Override
	public void replace(ItemStack stack) {
		if (stack != null && stack.stackSize == 0) stack = null;

		IInventory inventory = this.inventory.get();
		if (inventory == null) throw new IllegalStateException("Inventory is invalid");
		inventory.setInventorySlotContents(slot, stack);
	}

	@Override
	public int slot() {
		return slot;
	}

	@Override
	public ItemStack stack() {
		IInventory inventory = this.inventory.get();
		return inventory == null ? null : inventory.getStackInSlot(slot);
	}

	@Override
	public IReference<IInventory> inventory() {
		return inventory;
	}
}
