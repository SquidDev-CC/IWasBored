package org.squiddev.iwasbored.api.provider;

import dan200.computercraft.api.lua.ILuaObject;
import net.minecraft.item.ItemStack;

import java.util.Map;

public interface IProviderRegistry {
	/**
	 * Register a provider for item metadata
	 *
	 * @param provider The provider
	 */
	void registerItemMetadata(IProvider<ItemStack, Map<String, Object>> provider);

	/**
	 * Get all metadata for an item
	 *
	 * @param stack The item to provide metadata for
	 * @return The metadata for the item
	 */
	Map<String, Object> getItemMetadata(ItemStack stack);

	/**
	 * Add a method provider for an object
	 *
	 * @param provider The provider
	 * @param target   The class this provider targets. This can be an interface.
	 * @param <T>      The type this provider targets
	 */
	<T> void registerMethodProvider(IProvider<T, ILuaObject> provider, Class<T> target);

	/**
	 * Get all methods for an object
	 *
	 * @param object The object to provide methods for
	 * @return All methods provided
	 */
	<T> Iterable<ILuaObject> getObjectMethods(T object);

	/**
	 * Get all methods for an object
	 *
	 * @param object The object to provide methods for
	 * @param target The target class. This should be faster than the above method, an resolves interface ambiguities.
	 * @return All methods provided
	 */
	<T> Iterable<ILuaObject> getObjectMethods(T object, Class<T> target);
}
