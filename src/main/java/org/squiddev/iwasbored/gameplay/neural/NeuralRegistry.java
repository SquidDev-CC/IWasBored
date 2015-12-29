package org.squiddev.iwasbored.gameplay.neural;

import com.google.common.base.Preconditions;
import dan200.computercraft.api.lua.ILuaObject;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import org.squiddev.iwasbored.core.CoreAPI;
import org.squiddev.iwasbored.gameplay.api.neural.*;
import org.squiddev.iwasbored.core.api.provider.IProvider;
import org.squiddev.iwasbored.lib.SortedCollection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NeuralRegistry implements INeuralRegistry {
	private List<INeuralUpgradeProvider> generics = new ArrayList<INeuralUpgradeProvider>();
	private Map<ResourceLocation, INeuralUpgradeProvider> idLookup = new HashMap<ResourceLocation, INeuralUpgradeProvider>();
	private Map<Item, INeuralUpgradeProvider> itemLookup = new HashMap<Item, INeuralUpgradeProvider>();

	private SortedCollection<IProvider<EntityLivingBase, INeuralReference>> neuralProvider = new SortedCollection<IProvider<EntityLivingBase, INeuralReference>>(CoreAPI.providerComparer);
	private SortedCollection<IProvider<INeuralInterface, ILuaObject>> apiProviders = new SortedCollection<IProvider<INeuralInterface, ILuaObject>>(CoreAPI.providerComparer);

	//region Neural upgrades
	@Override
	public void registerNeuralUpgrade(INeuralUpgradeProvider provider) {
		Preconditions.checkNotNull(provider, "factory cannot be null");
		generics.add(provider);
	}

	@Override
	public void registerNeuralUpgrade(INeuralUpgradeProvider provider, ResourceLocation id, Item item) {
		Preconditions.checkNotNull(provider, "factory cannot be null");
		Preconditions.checkNotNull(id, "name cannot be null");
		Preconditions.checkNotNull(item, "item cannot be null");

		if (idLookup.containsKey(id)) {
			throw new IllegalArgumentException(id + " already exists, registered by " + idLookup.get(id));
		}

		if (itemLookup.containsKey(item)) {
			throw new IllegalArgumentException(item + " already exists, registered by " + itemLookup.get(item));
		}

		idLookup.put(id, provider);
		itemLookup.put(item, provider);
	}

	@Override
	public INeuralUpgrade create(ResourceLocation id, NBTTagCompound tag) {
		Preconditions.checkNotNull(id, "id cannot be null");
		Preconditions.checkNotNull(tag, "tag cannot be null");

		{
			INeuralUpgradeProvider factory = idLookup.get(id);
			if (factory != null) {
				INeuralUpgrade upgrade = factory.create(id, tag);
				if (upgrade != null) return upgrade;
			}
		}

		for (INeuralUpgradeProvider factory : generics) {
			INeuralUpgrade upgrade = factory.create(id, tag);
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
