package org.squiddev.iwasbored;

import com.google.common.base.Preconditions;
import dan200.computercraft.api.lua.ILuaObject;
import net.minecraft.item.ItemStack;
import org.squiddev.iwasbored.api.IIWasBoredAPI;
import org.squiddev.iwasbored.api.IProvider;
import org.squiddev.iwasbored.api.ItemReference;
import org.squiddev.iwasbored.api.meta.IItemMetaProvider;
import org.squiddev.iwasbored.api.meta.IMetaRegistry;
import org.squiddev.iwasbored.api.neural.INeuralRegistry;
import org.squiddev.iwasbored.inventory.InventoryUtils;
import org.squiddev.iwasbored.neural.NeuralRegistry;
import org.squiddev.iwasbored.utils.SortedCollection;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

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
		private final SortedCollection<IItemMetaProvider> providers = new SortedCollection<IItemMetaProvider>(new Comparator<IItemMetaProvider>() {
			@Override
			public int compare(IItemMetaProvider a, IItemMetaProvider b) {
				return b.getPriority() - a.getPriority();
			}
		});

		@Override
		public void registerItemProvider(IItemMetaProvider provider) {
			Preconditions.checkNotNull(provider, "provider cannot be null");
			providers.add(provider);
		}

		@Override
		public Map<String, Object> getItemMetadata(ItemStack stack) {
			Preconditions.checkNotNull(stack, "stack cannot be null");

			Map<String, Object> data = InventoryUtils.getBasicProperties(stack);

			for (IItemMetaProvider provider : providers) {
				provider.getMetadata(stack, data);
			}

			return data;
		}

		@Override
		public Iterable<ILuaObject> getItemMethods(ItemReference stack) {
			Preconditions.checkNotNull(stack, "stack cannot be null");

			List<ILuaObject> objects = new ArrayList<ILuaObject>();

			for (IItemMetaProvider provider : providers) {
				ILuaObject object = provider.getObject(stack);
				if (object != null) objects.add(object);
			}

			return objects;
		}
	}
}
