package org.squiddev.iwasbored.lib;

import java.util.Comparator;
import java.util.HashMap;

/**
 * Sorted multiset for classes
 */
public class SortedClassLookup<T> {
	private final Comparator<T> comparator;
	private final HashMap<Class<?>, SortedCollection<T>> items = new HashMap<Class<?>, SortedCollection<T>>();

	public SortedClassLookup(Comparator<T> comparator) {
		this.comparator = comparator;
	}

	public void add(T provider, Class<?> target) {
		SortedCollection<T> targetItems = items.get(target);
		if (targetItems == null) {
			targetItems = new SortedCollection<T>(comparator);
			items.put(target, targetItems);
		}

		targetItems.add(provider);
	}

	public SortedCollection<T> get(Class<?> target) {
		return items.get(target);
	}
}
