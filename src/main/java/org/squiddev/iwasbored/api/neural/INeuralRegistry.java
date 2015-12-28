package org.squiddev.iwasbored.api.neural;

import dan200.computercraft.api.lua.ILuaObject;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import org.squiddev.iwasbored.api.provider.IProvider;

/**
 * The registry for various neural interface components.
 *
 * When trying to create a neural upgrade, we first lookup a item or string
 * specific factory (one registered via {@link #registerNeuralUpgrade(INeuralUpgradeProvider, ResourceLocation, Item)}, then
 * lookup a generic one (one registered via {@link #registerNeuralUpgrade(INeuralUpgradeProvider)}).
 */
public interface INeuralRegistry extends INeuralUpgradeProvider {
	/**
	 * Register a neural upgrade
	 *
	 * @param provider The provider for this upgrade
	 */
	void registerNeuralUpgrade(INeuralUpgradeProvider provider);

	/**
	 * Register a neural upgrade with a string and item key
	 *
	 * @param provider The provider for this upgrade
	 * @param id       The id this upgrade will use
	 * @param item     The item this upgrade will use
	 * @throws IllegalArgumentException When the name or item already exists
	 */
	void registerNeuralUpgrade(INeuralUpgradeProvider provider, ResourceLocation id, Item item);

	/**
	 * Register a neural provider. This searches an entity for a neural interface.
	 *
	 * @param provider The provider to register.
	 */
	void registerNeuralProvider(IProvider<EntityLivingBase, INeuralReference> provider);

	/**
	 * Get a neural interface from an entity
	 *
	 * @param entity The entity to get from.
	 * @return The interface, or {@code null} if none can be found.
	 */
	INeuralReference getNeuralInterface(EntityLivingBase entity);

	/**
	 * Register a provider for extensions the core interface API
	 *
	 * @param provider The provider to register
	 */
	void registerLuaProvider(IProvider<INeuralInterface, ILuaObject> provider);

	/**
	 * Get all methods to invoke on the Lua interface
	 *
	 * @param iFace The neural interface
	 * @return All methods available
	 */
	Iterable<ILuaObject> getLua(INeuralInterface iFace);
}
