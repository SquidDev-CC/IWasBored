package org.squiddev.iwasbored.core.integration.vanilla;

import dan200.computercraft.api.lua.ILuaObject;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.squiddev.iwasbored.core.api.IIWasBoredCoreAPI;
import org.squiddev.iwasbored.core.api.IWasBoredCoreAPI;
import org.squiddev.iwasbored.core.api.provider.AbstractProvider;
import org.squiddev.iwasbored.core.api.reference.IInventorySlot;
import org.squiddev.iwasbored.core.api.reference.IReference;
import org.squiddev.iwasbored.lib.registry.Module;

public class VanillaIntegration extends Module {
	@Override
	public void init() {
		IIWasBoredCoreAPI registry = IWasBoredCoreAPI.instance();

		// Item providers
		registry.registerMethodProvider(new ItemProvider(), IInventorySlot.class);
		registry.registerMethodProvider(new ItemProviderConsumable(), IInventorySlot.class);

		// Item metadata providers
		registry.registerMetadataProvider(new ItemMetaProviderBasic(), ItemStack.class);
		registry.registerMetadataProvider(new ItemMetaProviderConsumable(), ItemStack.class);
		registry.registerMetadataProvider(new ItemMetaProviderMaterial(), ItemStack.class);
		registry.registerMetadataProvider(new ItemMetaProviderHarvestLevel(), ItemStack.class);
		registry.registerMetadataProvider(new ItemMetaProviderEnchantment(), ItemStack.class);
		registry.registerMetadataProvider(new ItemMetaProviderOreDict(), ItemStack.class);
		registry.registerMetadataProvider(new ItemMetaProviderFluidContainer(), ItemStack.class);
		registry.registerMetadataProvider(new ItemMetaProviderArmor(), ItemStack.class);

		// Fluid stack
		registry.registerMetadataProvider(new FluidMetaProviderBasic(), FluidStack.class);

		// Inventory providers
		registry.registerMethodProvider(new AbstractProvider<IReference<IInventory>, ILuaObject>() {
			@Override
			public ILuaObject get(IReference<IInventory> inventory) {
				return new LuaInventory(inventory);
			}
		}, IInventory.class);
	}
}
