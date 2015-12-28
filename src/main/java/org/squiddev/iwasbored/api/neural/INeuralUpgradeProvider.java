package org.squiddev.iwasbored.api.neural;


import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

/**
 * Factory for {@link INeuralUpgrade}. Register with {@link org.squiddev.iwasbored.api.neural.INeuralRegistry}.
 */
public interface INeuralUpgradeProvider {
	/**
	 * Create an upgrade from a tag and an id
	 *
	 * @param id  The id of the upgrade
	 * @param tag The tag of the upgrade
	 * @return The upgrade, or null if none can be created
	 */
	INeuralUpgrade create(ResourceLocation id, NBTTagCompound tag);

	/**
	 * Create an upgrade from an item stack
	 *
	 * @param stack The stack to create it from
	 * @return The upgrade, or null if none can be created
	 */
	INeuralUpgrade create(ItemStack stack);
}
