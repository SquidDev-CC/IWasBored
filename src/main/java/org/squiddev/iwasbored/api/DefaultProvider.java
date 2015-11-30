package org.squiddev.iwasbored.api;

/**
 * A basic provider that has the default priority.
 */
public abstract class DefaultProvider<TSource, TResult> implements IProvider<TSource, TResult> {
	@Override
	public int getPriority() {
		return 0;
	}
}
