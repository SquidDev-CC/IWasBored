package org.squiddev.iwasbored.gameplay.api.neural;

import net.minecraft.nbt.NBTTagCompound;
import org.squiddev.iwasbored.core.api.reference.IReference;

/**
 * Reference to a neural interface
 */
public interface INeuralReference extends IReference<INeuralInterface> {
	/**
	 * Get NBT information about the interface.
	 *
	 * This will be used on the client side.
	 *
	 * @return NBT information about the interface
	 */
	NBTTagCompound getTag();
}
