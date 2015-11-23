package org.squiddev.iwasbored.inventory;

import net.minecraft.item.ItemStack;

public class ArmorItem {
	public final boolean baubles;
	public final ItemStack stack;
	public final int slot;

	public ArmorItem(boolean baubles, ItemStack stack, int slot) {
		this.baubles = baubles;
		this.stack = stack;
		this.slot = slot;
	}
}
