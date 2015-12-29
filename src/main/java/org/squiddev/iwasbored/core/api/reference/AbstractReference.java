package org.squiddev.iwasbored.core.api.reference;

/**
 * A basic reference
 */
public abstract class AbstractReference<T> implements IReference<T> {
	protected final T object;

	public AbstractReference(T object) {
		this.object = object;
	}

	@Override
	public T get() {
		return isValid() ? object : null;
	}
}
