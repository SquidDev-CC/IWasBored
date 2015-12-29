package org.squiddev.iwasbored.core;

import com.google.common.base.Preconditions;
import dan200.computercraft.api.lua.ILuaObject;
import org.squiddev.iwasbored.core.api.IIWasBoredCoreAPI;
import org.squiddev.iwasbored.core.api.provider.IProvider;
import org.squiddev.iwasbored.core.api.reference.IReference;
import org.squiddev.iwasbored.lib.SortedClassLookup;
import org.squiddev.iwasbored.lib.SortedCollection;

import java.util.*;

public class CoreAPI implements IIWasBoredCoreAPI {
	public static final Comparator<IProvider> providerComparer = new Comparator<IProvider>() {
		@Override
		public int compare(IProvider a, IProvider b) {
			return b.getPriority() - a.getPriority();
		}
	};

	private final SortedClassLookup<IProvider> metaProviders = new SortedClassLookup<IProvider>(providerComparer);
	private final SortedClassLookup<IProvider> targetedProviders = new SortedClassLookup<IProvider>(providerComparer);

	@Override
	public <T> void registerMetadataProvider(IProvider<T, Map<String, Object>> provider, Class<T> target) {
		Preconditions.checkNotNull(provider, "provider cannot be null");
		Preconditions.checkNotNull("target", "target cannot be null");

		metaProviders.add(provider, target);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> Map<String, Object> getMetadata(T stack, Class<T> target) {
		Preconditions.checkNotNull(stack, "stack cannot be null");
		SortedCollection<IProvider> providers = metaProviders.get(target);
		if (providers == null) return Collections.emptyMap();

		Map<String, Object> data = new HashMap<String, Object>();
		for (IProvider provider : providers) {
			Map<String, Object> subData = (Map<String, Object>) provider.get(stack);
			if (subData != null) data.putAll(subData);
		}

		return data;
	}

	@Override
	public <T> void registerMethodProvider(IProvider<IReference<T>, ILuaObject> provider, Class<T> target) {
		Preconditions.checkNotNull("provider", "provider cannot be null");
		Preconditions.checkNotNull("target", "target cannot be null");

		metaProviders.add(provider, target);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> Iterable<ILuaObject> getMethods(IReference<T> object, Class<T> target) {
		Preconditions.checkNotNull(object, "object cannot be null");
		Preconditions.checkNotNull(object, "target cannot be null");

		SortedCollection<IProvider> providers = targetedProviders.get(target);
		if (providers == null) return Collections.emptyList();

		List<ILuaObject> objects = new ArrayList<ILuaObject>();

		for (IProvider provider : providers) {
			ILuaObject luaObject = (ILuaObject) provider.get(object);
			if (luaObject != null) objects.add(luaObject);
		}

		return objects;
	}
}
