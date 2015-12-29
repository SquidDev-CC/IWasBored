package org.squiddev.iwasbored.core.integration.computercraft;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Optional;
import org.squiddev.iwasbored.core.api.IIWasBoredCoreAPI;
import org.squiddev.iwasbored.core.api.IWasBoredCoreAPI;
import org.squiddev.iwasbored.lib.registry.IntegrationModule;

public class ComputerCraftIntegration extends IntegrationModule {
	public ComputerCraftIntegration() {
		super("ComputerCraft");
	}

	@Override
	@Optional.Method(modid = "ComputerCraft")
	public void init() {
		IIWasBoredCoreAPI registry = IWasBoredCoreAPI.instance();

		registry.registerMetadataProvider(new ItemMetaProviderComputer(), ItemStack.class);
	}
}
