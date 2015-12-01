package org.squiddev.iwasbored.inventory;

import com.google.common.base.Preconditions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import org.squiddev.iwasbored.api.reference.ItemReference;

public final class SingleItem implements ItemReference {
	public final IInventory inventory;
	public final int slot;
	public final EntityLivingBase entity;

	private final ItemStack stack;

	public SingleItem(IInventory inventory, int slot, EntityLivingBase entity) {
		Preconditions.checkNotNull(inventory, "inventory cannot be null");
		this.inventory = inventory;
		this.slot = slot;
		this.entity = entity;
		this.stack = inventory.getStackInSlot(slot);
	}

	@Override
	public ItemStack get() {
		if (entity != null && !entity.isEntityAlive()) return null;

		ItemStack current = inventory.getStackInSlot(slot);
		return current == stack || current != null && stack != null && current.getItem() == stack.getItem() ? current : null;
	}

	@Override
	public EntityLivingBase getEntity() {
		return entity;
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
