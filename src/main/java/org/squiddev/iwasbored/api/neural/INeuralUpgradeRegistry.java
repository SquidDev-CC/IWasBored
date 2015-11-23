package org.squiddev.iwasbored.api.neural;

import net.minecraft.item.Item;

/**
 * The registry for neural upgrades.
 *
 * When trying to create a neural upgrade, we first lookup a item or string
 * specific factory (one registered via {@link #registerNeuralUpgrade(INeuralUpgradeProvider, String, Item)}, then
 * lookup a generic one (one registered via {@link #registerNeuralUpgrade(INeuralUpgradeProvider)}).
 */
public interface INeuralUpgradeRegistry extends INeuralUpgradeProvider {
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
	 * @param name     The name this upgrade will use
	 * @param item     The item this upgrade will use
	 * @throws IllegalArgumentException When the name or item already exists
	 */
	void registerNeuralUpgrade(INeuralUpgradeProvider provider, String name, Item item);
}
