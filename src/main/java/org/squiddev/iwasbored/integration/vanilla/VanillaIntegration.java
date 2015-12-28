package org.squiddev.iwasbored.integration.vanilla;

import dan200.computercraft.api.lua.ILuaObject;
import net.minecraft.inventory.IInventory;
import org.squiddev.iwasbored.api.IWasBoredAPI;
import org.squiddev.iwasbored.api.neural.INeuralRegistry;
import org.squiddev.iwasbored.api.provider.AbstractProvider;
import org.squiddev.iwasbored.api.provider.IProviderRegistry;
import org.squiddev.iwasbored.api.reference.IInventorySlot;
import org.squiddev.iwasbored.api.reference.IReference;
import org.squiddev.iwasbored.inventory.LuaInventory;
import org.squiddev.iwasbored.registry.Module;

public class VanillaIntegration extends Module {
	@Override
	public void preInit() {
		IProviderRegistry registry = IWasBoredAPI.instance().coreRegistry();
		INeuralRegistry neuralRegistry = IWasBoredAPI.instance().neuralRegistry();

		// Item providers
		registry.registerMethodProvider(new BasicStackProvider(), IInventorySlot.class);
		registry.registerMethodProvider(new ConsumableStackProvider(), IInventorySlot.class);

		registry.registerItemMetadata(new BasicStackMetaProvider());
		registry.registerItemMetadata(new ConsumableStackMetaProvider());

		// Inventory providers
		registry.registerMethodProvider(new AbstractProvider<IReference<IInventory>, ILuaObject>() {
			@Override
			public ILuaObject get(IReference<IInventory> inventory) {
				return new LuaInventory(inventory);
			}
		}, IInventory.class);

		// Neural interface
		neuralRegistry.registerLuaProvider(new BasicNeuralLuaProvider());
		neuralRegistry.registerNeuralProvider(new ArmorNeuralProvider());
	}
}
