package org.squiddev.iwasbored.integration.baubles;

import net.minecraftforge.fml.common.Optional;
import org.squiddev.iwasbored.api.IWasBoredAPI;
import org.squiddev.iwasbored.api.neural.INeuralRegistry;
import org.squiddev.iwasbored.integration.ModIntegration;

public class BaublesIntegration extends ModIntegration {
	public BaublesIntegration() {
		super("Baubles");
	}

	@Override
	@Optional.Method(modid = "Baubles")
	public void postInit() {
		INeuralRegistry registry = IWasBoredAPI.instance().neuralRegistry();

		registry.registerNeuralProvider(new BaublesNeuralProvider());
		registry.registerLuaProvider(new BaublesNeuralLuaProvider());
	}
}
