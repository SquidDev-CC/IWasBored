package org.squiddev.iwasbored.gameplay.integration.baubles;

import net.minecraftforge.fml.common.Optional;
import org.squiddev.iwasbored.gameplay.api.IWasBoredGameplayAPI;
import org.squiddev.iwasbored.gameplay.api.neural.INeuralRegistry;
import org.squiddev.iwasbored.lib.registry.IntegrationModule;

public class BaublesIntegration extends IntegrationModule {
	public BaublesIntegration() {
		super("Baubles");
	}

	@Override
	@Optional.Method(modid = "Baubles")
	public void postInit() {
		INeuralRegistry registry = IWasBoredGameplayAPI.instance().neuralRegistry();

		registry.registerNeuralProvider(new BaublesNeuralProvider());
		registry.registerLuaProvider(new BaublesNeuralLuaProvider());
	}
}
