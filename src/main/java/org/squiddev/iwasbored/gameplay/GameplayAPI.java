package org.squiddev.iwasbored.gameplay;

import org.squiddev.iwasbored.gameplay.neural.NeuralRegistry;
import org.squiddev.iwasbored.gameplay.api.IIWasBoredGameplayAPI;
import org.squiddev.iwasbored.gameplay.api.neural.INeuralRegistry;

public class GameplayAPI implements IIWasBoredGameplayAPI {
	private final INeuralRegistry neuralRegistry = new NeuralRegistry();

	@Override
	public INeuralRegistry neuralRegistry() {
		return neuralRegistry;
	}
}
