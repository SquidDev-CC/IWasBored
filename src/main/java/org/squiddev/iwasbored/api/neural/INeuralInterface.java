package org.squiddev.iwasbored.api.neural;

import net.minecraft.entity.player.EntityPlayer;

import java.util.Map;

public interface INeuralInterface {
	/**
	 * The player this interface is attached to
	 *
	 * @return The player this interface is attached to.
	 */
	EntityPlayer getPlayer();

	/**
	 * Get a read only list of upgrades
	 *
	 * @return All upgrades
	 */
	Map<String, INeuralUpgrade> getUpgrades();

	/**
	 * Add an upgrade to this interface
	 *
	 * @param upgrade The upgrade to add
	 * @return If the upgrade was added
	 */
	boolean addUpgrade(INeuralUpgrade upgrade);

	/**
	 * Remove an upgrade from this interface
	 *
	 * @param upgrade The upgrade to remove
	 * @return If the upgrade was removed
	 */
	boolean removeUpgrade(INeuralUpgrade upgrade);

	/**
	 * Mark this, or one of its upgrades dirty
	 */
	void markDirty();

	/**
	 * Force the neural interface to be on
	 */
	void turnOn();
}
