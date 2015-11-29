package org.squiddev.iwasbored.api.meta;

import dan200.computercraft.api.lua.ILuaObject;
import net.minecraft.item.ItemStack;
import org.squiddev.iwasbored.api.ItemReference;

import java.util.Map;

public interface IMetaRegistry {
	/**
	 * Register an item provider
	 *
	 * @param provider The item provider
	 */
	void registerItemProvider(IItemMetaProvider provider);

	/**
	 * Get all properties for a method
	 *
	 * @param stack The item to provide metadata for
	 * @return The metadata for the item
	 */
	Map<String, Object> getItemMetadata(ItemStack stack);

	/**
	 * Get all methods for an item
	 *
	 * @param stack The item to provide methods for
	 * @return All methods provided
	 */
	Iterable<ILuaObject> getItemMethods(ItemReference stack);
}
