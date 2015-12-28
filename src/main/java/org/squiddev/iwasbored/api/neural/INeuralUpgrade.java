package org.squiddev.iwasbored.api.neural;

import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public interface INeuralUpgrade {
	/**
	 * Get the Lua Object used to interface with this upgrade
	 * This is cached by the neural interface so you don't have to.
	 */
	IPeripheral getLuaObject();

	/**
	 * Called when the computer is turned on.
	 * This is called on the computer thread.
	 */
	void attach(INeuralInterface neuralInterface);

	/**
	 * Called when the computer is turned off.
	 * This is called on the computer thread.
	 */
	void detach();

	/**
	 * Called every update tick.
	 * This is called on the server thread.
	 */
	void update();

	/**
	 * Get the name for this upgrade
	 */
	ResourceLocation getName();

	/**
	 * Write this upgrade to an NBT compound.
	 *
	 * @return The written tag
	 */
	NBTTagCompound toNBT();

	/**
	 * Convert this upgrade to an item stack
	 *
	 * @return The produced ItemStack
	 */
	ItemStack toStack();
}

