package org.squiddev.iwasbored;

import com.google.common.base.Preconditions;
import dan200.computercraft.api.lua.ILuaObject;
import net.minecraft.item.ItemStack;
import org.squiddev.iwasbored.api.IIWasBoredAPI;
import org.squiddev.iwasbored.api.IProvider;
import org.squiddev.iwasbored.api.meta.IMetaRegistry;
import org.squiddev.iwasbored.api.neural.INeuralRegistry;
import org.squiddev.iwasbored.inventory.InventoryUtils;
import org.squiddev.iwasbored.neural.NeuralRegistry;
import org.squiddev.iwasbored.utils.SortedCollection;

import java.util.*;

public class API implements IIWasBoredAPI {
	private final INeuralRegistry neuralRegistry = new NeuralRegistry();
	private final MetaRegistry metaRegistry = new MetaRegistry();

	@Override
	public INeuralRegistry neuralRegistry() {
		return neuralRegistry;
	}

	@Override
	public IMetaRegistry metaRegistry() {
		return metaRegistry;
	}

	public static final Comparator<IProvider> providerComparer = new Comparator<IProvider>() {
		@Override
		public int compare(IProvider a, IProvider b) {
			return b.getPriority() - a.getPriority();
		}
	};

	private static class MetaRegistry implements IMetaRegistry {
		private final SortedCollection<IProvider<ItemStack, Map<String, Object>>> metaProviders = new SortedCollection<IProvider<ItemStack, Map<String, Object>>>(providerComparer);

		private final HashMap<Class<?>, SortedCollection<IProvider>> targetedProviders = new HashMap<Class<?>, SortedCollection<IProvider>>();

		@Override
		public void registerItemMetadata(IProvider<ItemStack, Map<String, Object>> provider) {
			Preconditions.checkNotNull(provider, "provider cannot be null");
			metaProviders.add(provider);
		}

		@Override
		public Map<String, Object> getItemMetadata(ItemStack stack) {
			Preconditions.checkNotNull(stack, "stack cannot be null");

			Map<String, Object> data = InventoryUtils.getBasicProperties(stack);

			for (IProvider<ItemStack, Map<String, Object>> provider : metaProviders) {
				Map<String, Object> subData = provider.get(stack);
				if (subData != null) data.putAll(subData);
			}

			return data;
		}

		@Override
		public <T> void registerMethodProvider(IProvider<T, ILuaObject> provider, Class<T> target) {
			Preconditions.checkNotNull("provider", "provider cannot be null");
			Preconditions.checkNotNull("target", "target cannot be null");

			SortedCollection<IProvider> providers = targetedProviders.get(target);
			if (providers == null) {
				providers = new SortedCollection<IProvider>(providerComparer);
				targetedProviders.put(target, providers);
			}

			providers.add(provider);
		}

		@Override
		@SuppressWarnings("unchecked")
		public <T> Iterable<ILuaObject> getObjectMethods(T object) {
			Preconditions.checkNotNull(object, "object cannot be null");

			// Try classes first
			Class<?> klass = object.getClass();
			SortedCollection<IProvider> providers = targetedProviders.get(klass);
			while (providers == null && klass != null) {
				klass = klass.getSuperclass();
				providers = targetedProviders.get(klass);
			}

			if (providers == null) {
				for (Class<?> iFace : object.getClass().getInterfaces()) {
					providers = targetedProviders.get(iFace);
					if (providers == null) break;
				}

				if (providers == null) return Collections.emptyList();
			}


			List<ILuaObject> objects = new ArrayList<ILuaObject>();

			for (IProvider provider : providers) {
				ILuaObject luaObject = (ILuaObject) provider.get(object);
				if (luaObject != null) objects.add(luaObject);
			}

			return objects;
		}

		@Override
		@SuppressWarnings("unchecked")
		public <T> Iterable<ILuaObject> getObjectMethods(T object, Class<T> target) {
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
}
