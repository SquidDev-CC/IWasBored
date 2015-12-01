package org.squiddev.iwasbored.integration.openperipheral;

import cpw.mods.fml.common.Optional;
import net.minecraft.item.ItemStack;
import openperipheral.api.ApiAccess;
import openperipheral.api.meta.IItemStackMetaBuilder;
import org.squiddev.iwasbored.DebugLogger;
import org.squiddev.iwasbored.api.IWasBoredAPI;
import org.squiddev.iwasbored.api.provider.IProvider;
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
			if (ApiAccess.isApiPresent(IItemStackMetaBuilder.class)) {
				final IItemStackMetaBuilder builder = ApiAccess.getApi(IItemStackMetaBuilder.class);
				IWasBoredAPI.instance().coreRegistry().registerItemMetadata(new IProvider<ItemStack, Map<String, Object>>() {
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
		} catch (IllegalStateException e) {
			DebugLogger.error("Cannot register with OpenPeripheral", e);
		}
	}
}
