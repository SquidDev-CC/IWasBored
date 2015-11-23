package org.squiddev.iwasbored.api.neural;

import dan200.computercraft.api.lua.ILuaObject;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface INeuralUpgrade {
	/**
	 * Get the Lua Object used to interface with this upgrade
	 * This is cached by the neural interface so you don't have to.
	 */
	ILuaObject getLuaObject();

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
	 * Get the unique name for this type of upgrade:
	 * A neural interface cannot have more than one type.
	 */
	String getName();

	/**
	 * Write this upgrade to an NBT compound.
	 * Ideally upgrades shouldn't need to store state-specific information,
	 * but just in case.
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

