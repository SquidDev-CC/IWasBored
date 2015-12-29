package org.squiddev.iwasbored.core.api.provider;

import java.util.Collections;
import java.util.Map;

/**
 * A meta provider that gives items within a namespace.
 */
public abstract class NamespacedMetaProvider<T> extends AbstractProvider<T, Map<String, Object>> {
	/**
	 * Get the namespace for this provider
	 *
	 * @return The namespace
	 */
	public abstract String getNamespace();

	/**
	 * Get the metadata for this object
	 *
	 * @param object The object to get for
	 * @return The data, or {@code null} if none is relevant.
	 */
	public abstract Object getMeta(T object);

	@Override
	public Map<String, Object> get(T object) {
		Object result = getMeta(object);
		return result == null ? Collections.<String, Object>emptyMap() : Collections.singletonMap(getNamespace(), result);
	}
}
