package org.squiddev.iwasbored.api.meta;

import dan200.computercraft.api.lua.ILuaObject;
import net.minecraft.item.ItemStack;

import java.util.Map;

/**
 * A provider for items. Register with {@link IMetaRegistry}.
 */
public interface IItemMetaProvider {
	/**
	 * Add additional items to the metadata
	 *
	 * @param stack The item to provide metadata for
	 * @param data  The map to fill with. Do not remove keys, and try to check for existing items.
	 */
	void getMetadata(ItemStack stack, Map<String, Object> data);

	/**
	 * Get a series of methods that can be called on this item
	 *
	 * @param stack The item to provide methods for
	 * @return The object
	 */
	ILuaObject getObject(ItemReference stack);
}
