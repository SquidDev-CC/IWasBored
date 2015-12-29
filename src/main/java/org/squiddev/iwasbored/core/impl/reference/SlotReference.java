package org.squiddev.iwasbored.core.impl.reference;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import org.squiddev.iwasbored.core.api.reference.AbstractReference;
import org.squiddev.iwasbored.core.api.reference.IInventorySlot;
import org.squiddev.iwasbored.core.api.reference.IReference;

public class SlotReference extends AbstractReference<IInventorySlot> {
	private final ItemStack stack;

	public SlotReference(IInventorySlot object) {
		super(object);

		this.stack = object.stack();
	}

	public SlotReference(IReference<IInventory> inventory, int slot) {
		this(new InventorySlot(inventory, slot));
	}

	@Override
	public boolean isValid() {
		if (!object.inventory().isValid()) return false;

		ItemStack current = object.stack();
		return current == stack || (current != null && stack != null && current.getItem() == stack.getItem());
	}

	@Override
	public Object owner() {
		return object.inventory().owner();
	}
}
