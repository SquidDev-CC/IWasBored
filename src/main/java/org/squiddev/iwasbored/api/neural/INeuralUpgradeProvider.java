package org.squiddev.iwasbored.api.neural;


import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Factory for {@link INeuralUpgrade}. Register with {@link org.squiddev.iwasbored.api.IIWasBoredAPI}.
 */
public interface INeuralUpgradeProvider {
	/**
	 * Create an upgrade from a tag and a name
	 *
	 * @param name The name of the upgrade
	 * @param tag  The tag of the upgrade
	 * @return The upgrade, or null if none can be created
	 */
	INeuralUpgrade create(String name, NBTTagCompound tag);

	/**
	 * Create an upgrade from an item stack
	 *
	 * @param stack The stack to create it from
	 * @return The upgrade, or null if none can be created
	 */
	INeuralUpgrade create(ItemStack stack);
}
