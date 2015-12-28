package org.squiddev.iwasbored.api.neural;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;

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
	Map<EnumFacing, INeuralUpgrade> getUpgrades();

	/**
	 * Get an upgrade on this interface.
	 *
	 * @param direction The direction the upgrade is mounted on. This cannot be {@link EnumFacing#UP}.
	 * @return The upgrade, or {@code null} if none.
	 */
	INeuralUpgrade getUpgrade(EnumFacing direction);

	/**
	 * Add an upgrade to this interface.
	 *
	 * @param direction The direction to mount on. This cannot be {@link EnumFacing#UP}.
	 * @param upgrade   The upgrade to add
	 * @return If the upgrade was added (if there is not one there already).
	 */
	boolean addUpgrade(EnumFacing direction, INeuralUpgrade upgrade);

	/**
	 * Remove an upgrade from this interface
	 *
	 * @param direction The direction to remove from. This cannot be {@link EnumFacing#UP}.
	 * @return If the upgrade was removed (if there was one).
	 */
	boolean removeUpgrade(EnumFacing direction);

	/**
	 * Mark this, or one of its upgrades dirty
	 */
	void markDirty();

	/**
	 * Force the neural interface to be on
	 */
	void turnOn();
}
