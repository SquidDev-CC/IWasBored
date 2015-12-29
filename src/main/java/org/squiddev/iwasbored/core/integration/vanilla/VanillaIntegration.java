package org.squiddev.iwasbored.core.integration.vanilla;

import dan200.computercraft.api.lua.ILuaObject;
import net.minecraft.inventory.IInventory;
import org.squiddev.iwasbored.core.api.IIWasBoredCoreAPI;
import org.squiddev.iwasbored.core.api.IWasBoredCoreAPI;
import org.squiddev.iwasbored.core.api.provider.AbstractProvider;
import org.squiddev.iwasbored.core.api.reference.IInventorySlot;
import org.squiddev.iwasbored.core.api.reference.IReference;
import org.squiddev.iwasbored.lib.registry.Module;

public class VanillaIntegration extends Module {
	@Override
	public void preInit() {
		IIWasBoredCoreAPI registry = IWasBoredCoreAPI.instance();

		// Item providers
		registry.registerMethodProvider(new ItemProvider(), IInventorySlot.class);
		registry.registerMethodProvider(new ItemProviderConsumable(), IInventorySlot.class);

		registry.registerItemMetadata(new ItemMetaProviderBasic());
		registry.registerItemMetadata(new ItemMetaProviderConsumable());
		registry.registerItemMetadata(new ItemMetaProviderMaterial());
		registry.registerItemMetadata(new ItemMetaProviderHarvestLevel());
		registry.registerItemMetadata(new ItemMetaProviderEnchantment());
		registry.registerItemMetadata(new ItemMetaProviderOreDict());

		// Inventory providers
		registry.registerMethodProvider(new AbstractProvider<IReference<IInventory>, ILuaObject>() {
			@Override
			public ILuaObject get(IReference<IInventory> inventory) {
				return new LuaInventory(inventory);
			}
		}, IInventory.class);
	}
}
