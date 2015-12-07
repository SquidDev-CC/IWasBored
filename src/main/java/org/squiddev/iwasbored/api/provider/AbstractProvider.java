package org.squiddev.iwasbored.api.provider;

/**
 * A basic provider that has the default priority.
 */
public abstract class AbstractProvider<TSource, TResult> implements IProvider<TSource, TResult> {
	@Override
	public int getPriority() {
		return 0;
	}
}
