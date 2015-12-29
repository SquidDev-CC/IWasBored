package org.squiddev.iwasbored.gameplay.integration.vanilla;

import org.squiddev.iwasbored.gameplay.api.IWasBoredGameplayAPI;
import org.squiddev.iwasbored.gameplay.api.neural.INeuralRegistry;
import org.squiddev.iwasbored.lib.registry.Module;

public class VanillaIntegration extends Module {
	@Override
	public void preInit() {
		INeuralRegistry registry = IWasBoredGameplayAPI.instance().neuralRegistry();

		// Neural interface
		registry.registerLuaProvider(new BasicNeuralLuaProvider());
		registry.registerNeuralProvider(new ArmorNeuralProvider());
	}
}
