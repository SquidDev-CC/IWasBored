package org.squiddev.iwasbored;

import com.google.common.base.Preconditions;
import dan200.computercraft.api.lua.ILuaObject;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.squiddev.iwasbored.api.IIWasBoredAPI;
import org.squiddev.iwasbored.api.meta.IItemMetaProvider;
import org.squiddev.iwasbored.api.meta.IMetaRegistry;
import org.squiddev.iwasbored.api.meta.ItemReference;
import org.squiddev.iwasbored.api.neural.INeuralUpgrade;
import org.squiddev.iwasbored.api.neural.INeuralUpgradeProvider;
import org.squiddev.iwasbored.api.neural.INeuralUpgradeRegistry;
import org.squiddev.iwasbored.inventory.InventoryUtils;

import java.util.*;

public class API implements IIWasBoredAPI {
	private final INeuralUpgradeRegistry neuralRegistry = new NeuralUpgrades();
	private final MetaRegistry metaRegistry = new MetaRegistry();

	@Override
	public INeuralUpgradeRegistry neuralRegistry() {
		return neuralRegistry;
	}

	@Override
	public IMetaRegistry metaRegistry() {
		return metaRegistry;
	}

	private static class NeuralUpgrades implements INeuralUpgradeRegistry {
		private List<INeuralUpgradeProvider> generics = new ArrayList<INeuralUpgradeProvider>();
		private Map<String, INeuralUpgradeProvider> stringLookup = new HashMap<String, INeuralUpgradeProvider>();
		private Map<Item, INeuralUpgradeProvider> itemLookup = new HashMap<Item, INeuralUpgradeProvider>();

		@Override
		public void registerNeuralUpgrade(INeuralUpgradeProvider provider) {
			Preconditions.checkNotNull(provider, "factory cannot be null");
			generics.add(provider);
		}

		@Override
		public void registerNeuralUpgrade(INeuralUpgradeProvider provider, String name, Item item) {
			Preconditions.checkNotNull(provider, "factory cannot be null");
			Preconditions.checkNotNull(name, "name cannot be null");
			Preconditions.checkNotNull(item, "item cannot be null");

			if (stringLookup.containsKey(name)) {
				throw new IllegalArgumentException(name + " already exists, registered by " + stringLookup.get(name));
			}

			if (itemLookup.containsKey(item)) {
				throw new IllegalArgumentException(item + " already exists, registered by " + itemLookup.get(item));
			}

			stringLookup.put(name, provider);
			itemLookup.put(item, provider);
		}

		@Override
		public INeuralUpgrade create(String name, NBTTagCompound tag) {
			Preconditions.checkNotNull(name, "name cannot be null");
			Preconditions.checkNotNull(tag, "tag cannot be null");


			{
				INeuralUpgradeProvider factory = stringLookup.get(name);
				if (factory != null) {
					INeuralUpgrade upgrade = factory.create(name, tag);
					if (upgrade != null) return upgrade;
				}
			}

			for (INeuralUpgradeProvider factory : generics) {
				INeuralUpgrade upgrade = factory.create(name, tag);
				if (upgrade != null) return upgrade;
			}

			return null;
		}

		@Override
		public INeuralUpgrade create(ItemStack stack) {
			Preconditions.checkNotNull(stack, "stack cannot be null");

			{
				INeuralUpgradeProvider factory = itemLookup.get(stack.getItem());
				if (factory != null) {
					INeuralUpgrade upgrade = factory.create(stack);
					if (upgrade != null) return upgrade;
				}
			}

			for (INeuralUpgradeProvider factory : generics) {
				INeuralUpgrade upgrade = factory.create(stack);
				if (upgrade != null) return upgrade;
			}

			return null;
		}
	}

	private static class MetaRegistry implements IMetaRegistry {
		private final Set<IItemMetaProvider> providers = new HashSet<IItemMetaProvider>();

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
			List<ILuaObject> objects = new ArrayList<ILuaObject>();

			DebugLogger.debug("Loading with " + providers);
			for (IItemMetaProvider provider : providers) {
				ILuaObject object = provider.getObject(stack);
				if (object != null) objects.add(object);
			}
			DebugLogger.debug("Got " + objects);

			return objects;
		}
	}
}
