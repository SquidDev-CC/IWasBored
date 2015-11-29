package org.squiddev.iwasbored.neural;

import com.google.common.base.Preconditions;
import dan200.computercraft.api.lua.ILuaObject;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.squiddev.iwasbored.API;
import org.squiddev.iwasbored.api.IProvider;
import org.squiddev.iwasbored.api.neural.*;
import org.squiddev.iwasbored.utils.SortedCollection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NeuralRegistry implements INeuralRegistry {
	private List<INeuralUpgradeProvider> generics = new ArrayList<INeuralUpgradeProvider>();
	private Map<String, INeuralUpgradeProvider> stringLookup = new HashMap<String, INeuralUpgradeProvider>();
	private Map<Item, INeuralUpgradeProvider> itemLookup = new HashMap<Item, INeuralUpgradeProvider>();

	private SortedCollection<IProvider<EntityLivingBase, INeuralReference>> neuralProvider = new SortedCollection<IProvider<EntityLivingBase, INeuralReference>>(API.providerComparer);
	private SortedCollection<IProvider<INeuralInterface, ILuaObject>> apiProviders = new SortedCollection<IProvider<INeuralInterface, ILuaObject>>(API.providerComparer);

	//region Neural upgrades
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
	//endregion

	@Override
	public void registerNeuralProvider(IProvider<EntityLivingBase, INeuralReference> provider) {
		Preconditions.checkNotNull(provider, "provider cannot be null");
		neuralProvider.add(provider);
	}

	@Override
	public INeuralReference getNeuralInterface(EntityLivingBase entity) {
		for (IProvider<EntityLivingBase, INeuralReference> provider : neuralProvider) {
			INeuralReference iFace = provider.get(entity);
			if (iFace != null && iFace.isValid()) return iFace;
		}

		return null;
	}

	@Override
	public void registerLuaProvider(IProvider<INeuralInterface, ILuaObject> provider) {
		Preconditions.checkNotNull(provider, "provider cannot be null");
		apiProviders.add(provider);
	}

	@Override
	public Iterable<ILuaObject> getLua(INeuralInterface iFace) {
		Preconditions.checkNotNull(iFace, "iFace cannot be null");

		List<ILuaObject> objects = new ArrayList<ILuaObject>();

		for (IProvider<INeuralInterface, ILuaObject> provider : apiProviders) {
			ILuaObject object = provider.get(iFace);
			if (object != null) objects.add(object);
		}

		return objects;
	}
}
