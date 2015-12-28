package org.squiddev.iwasbored.integration.openperipheral;

import cpw.mods.fml.common.Optional;
import dan200.computercraft.api.lua.ILuaObject;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import openperipheral.api.ApiAccess;
import openperipheral.api.architecture.cc.IComputerCraftObjectsFactory;
import openperipheral.api.meta.IItemStackMetaBuilder;
import org.squiddev.iwasbored.DebugLogger;
import org.squiddev.iwasbored.api.IWasBoredAPI;
import org.squiddev.iwasbored.api.provider.AbstractProvider;
import org.squiddev.iwasbored.api.provider.IProvider;
import org.squiddev.iwasbored.api.provider.IProviderRegistry;
import org.squiddev.iwasbored.api.reference.IReference;
import org.squiddev.iwasbored.integration.ModIntegration;

import java.util.Map;

public class OpenPeripheralIntegration extends ModIntegration {
	public OpenPeripheralIntegration() {
		super("OpenPeripheral");
	}

	@Override
	@Optional.Method(modid = "OpenPeripheral")
	public void postInit() {
		try {
			IProviderRegistry registry = IWasBoredAPI.instance().coreRegistry();
			if (ApiAccess.isApiPresent(IItemStackMetaBuilder.class)) {
				final IItemStackMetaBuilder builder = ApiAccess.getApi(IItemStackMetaBuilder.class);
				registry.registerItemMetadata(new IProvider<ItemStack, Map<String, Object>>() {
					@Override
					public Map<String, Object> get(ItemStack stack) {
						return builder.getItemStackMetadata(stack);
					}

					@Override
					public int getPriority() {
						return -1;
					}
				});
			} else {
				DebugLogger.error("Cannot use OpenPeripheral's meta providers: IItemStackMetaBuilder is not present");
			}

			if (ApiAccess.isApiPresent(IComputerCraftObjectsFactory.class)) {
				final IComputerCraftObjectsFactory factory = ApiAccess.getApi(IComputerCraftObjectsFactory.class);
				registry.registerMethodProvider(new AbstractProvider<IReference<IInventory>, ILuaObject>() {
					@Override
					public ILuaObject get(IReference<IInventory> reference) {
						return factory.wrapObject(reference.get());
					}
				}, IInventory.class);
			} else {
				DebugLogger.error("Cannot use OpenPeripheral's inventory providers: IComputerCraftObjectsFactory is not present");
			}
		} catch (IllegalStateException e) {
			DebugLogger.error("Cannot register with OpenPeripheral", e);
		}
	}
}
