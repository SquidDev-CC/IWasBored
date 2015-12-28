package org.squiddev.iwasbored.api.provider;

import dan200.computercraft.api.lua.ILuaObject;
import net.minecraft.item.ItemStack;
import org.squiddev.iwasbored.api.reference.IReference;

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
	 * Add a method provider for an object.
	 *
	 * You should only need to check if the reference is valid if there has been some delay since calling: the parent
	 * wrapper will check for this otherwise.
	 *
	 * Most of the time you can register as the standard class or interface
	 * (such as {@link net.minecraft.inventory.IInventory} or {@link net.minecraft.entity.Entity}).
	 *
	 * For items though you should register with {@link org.squiddev.iwasbored.api.reference.IInventorySlot}.
	 *
	 * @param provider The provider
	 * @param target   The class this provider targets. This can be an interface.
	 * @param <T>      The type this provider targets
	 */
	<T> void registerMethodProvider(IProvider<IReference<T>, ILuaObject> provider, Class<T> target);

	/**
	 * Get all methods for an object
	 *
	 * @param object The object to provide methods for
	 * @param target The target class. This should be faster than the above method, an resolves interface ambiguities.
	 * @return All methods provided
	 */
	<T> Iterable<ILuaObject> getObjectMethods(IReference<T> object, Class<T> target);
}
