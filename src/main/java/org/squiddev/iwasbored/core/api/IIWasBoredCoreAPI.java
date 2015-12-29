package org.squiddev.iwasbored.core.api;

import dan200.computercraft.api.lua.ILuaObject;
import org.squiddev.iwasbored.core.api.provider.IProvider;
import org.squiddev.iwasbored.core.api.reference.IInventorySlot;
import org.squiddev.iwasbored.core.api.reference.IReference;

import java.util.Map;

public interface IIWasBoredCoreAPI {
	/**
	 * Register a provider for metadata.
	 *
	 * This can be any object, but some common ones include:
	 * - {@link net.minecraft.item.ItemStack}
	 * - {@link net.minecraft.entity.Entity}
	 * - {@link net.minecraftforge.fluids.FluidStack}
	 * - {@link net.minecraft.tileentity.TileEntity}
	 * - {@link net.minecraft.block.state.BlockState}
	 *
	 * @param target   The class/interface this provider targets.
	 * @param provider The provider
	 * @see #getMetadata(Object, Class)
	 */
	<T> void registerMetadataProvider(IProvider<T, Map<String, Object>> provider, Class<T> target);

	/**
	 * Get all metadata for an object.
	 *
	 * @param object The object to provide metadata for.
	 * @param target The target class/interface.
	 * @return The metadata for the object
	 * @see #registerMetadataProvider(IProvider, Class)
	 */
	<T> Map<String, Object> getMetadata(T object, Class<T> target);

	/**
	 * Add a method provider for an object.
	 *
	 * You should only need to check if the reference is valid if there has been some delay since calling: the parent
	 * wrapper will check for this otherwise.
	 *
	 * Most of the time you can register as the standard class or interface
	 * (such as {@link net.minecraft.inventory.IInventory} or {@link net.minecraft.entity.Entity}).
	 *
	 * For items though you should register with {@link IInventorySlot}.
	 *
	 * @param provider The provider
	 * @param target   The class/interface this provider targets.
	 * @param <T>      The type this provider targets
	 * @see #getMethods(IReference, Class)
	 */
	<T> void registerMethodProvider(IProvider<IReference<T>, ILuaObject> provider, Class<T> target);

	/**
	 * Get all methods for an object.
	 *
	 * @param object The object to provide methods for.
	 * @param target The target class/interface.
	 * @return All methods provided
	 * @see #registerMethodProvider(IProvider, Class)
	 */
	<T> Iterable<ILuaObject> getMethods(IReference<T> object, Class<T> target);
}
