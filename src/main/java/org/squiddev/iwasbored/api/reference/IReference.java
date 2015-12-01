package org.squiddev.iwasbored.api.reference;

/**
 * A reference to another object
 */
public interface IReference<T> {
	/**
	 * If the target is still valid
	 *
	 * @return If this target is still valid.
	 */
	boolean isValid();

	/**
	 * Get the value
	 *
	 * @return The value. This will be {@code null} if the target is no longer valid.
	 */
	T get();
}
