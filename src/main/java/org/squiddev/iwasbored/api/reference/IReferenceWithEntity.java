package org.squiddev.iwasbored.api.reference;

import net.minecraft.entity.EntityLivingBase;

/**
 * A reference with an attached entity
 */
public interface IReferenceWithEntity<T> extends IReference<T> {
	/**
	 * The inventory's entity
	 *
	 * @return The inventory's entity
	 */
	EntityLivingBase getEntity();
}
