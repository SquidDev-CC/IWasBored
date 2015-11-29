package org.squiddev.iwasbored.utils;

import java.util.*;

/**
 * Non thread-safe sorted collection
 */
@SuppressWarnings("NullableProblems")
public class SortedCollection<E> implements Collection<E> {
	private ArrayList<E> list = new ArrayList<E>();
	private boolean sorted = true;

	private final Comparator<? super E> comparator;

	public SortedCollection(Comparator<? super E> comparator) {
		this.comparator = comparator;
	}

	@Override
	public Iterator<E> iterator() {
		if (!sorted) {
			Collections.sort(list, comparator);
			sorted = true;
		}

		return list.iterator();
	}

	@Override
	public Object[] toArray() {
		return list.toArray();
	}

	@Override
	public <T> T[] toArray(T[] array) {
		return list.toArray(array);
	}

	@Override
	public boolean add(E t) {
		if (list.add(t)) {
			sorted = false;
			return true;
		}

		return false;
	}

	@Override
	public boolean remove(Object o) {
		return list.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> collection) {
		return list.containsAll(collection);
	}

	@Override
	public boolean addAll(Collection<? extends E> collection) {
		if (list.addAll(collection)) {
			sorted = false;
			return true;
		}

		return false;
	}

	@Override
	public boolean removeAll(Collection<?> collection) {
		return list.removeAll(collection);
	}

	@Override
	public boolean retainAll(Collection<?> collection) {
		return list.retainAll(collection);
	}

	@Override
	public void clear() {
		list.clear();
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return list.contains(o);
	}
}
