package org.squiddev.iwasbored.api;

/**
 * A basic provider interface, which searches {@link TSource} and returns {@link TResult}.
 */
public interface IProvider<TSource, TResult> {
	/**
	 * Get the priority of this provider.
	 *
	 * @return This provider's priority. The larger the number the higher the priority.
	 */
	int getPriority();

	/**
	 * Get an instance from a particular value
	 *
	 * @param source The source to get from
	 * @return The result, or {@code null} if none can be found.
	 */
	TResult get(TSource source);
}
