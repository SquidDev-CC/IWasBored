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
	 * Get the value.
	 * Whilst the reference is valid, it should always be the same object.
	 *
	 * @return The value. This will be {@code null} if the target is no longer valid.
	 */
	T get();

	/**
	 * The owner of this reference. This will probably be a tile entity or a entity.
	 * Whilst the reference is valid, it should always be the same object.
	 *
	 * @return The owner of this reference. This may be {@code null}.
	 */
	Object owner();
}
