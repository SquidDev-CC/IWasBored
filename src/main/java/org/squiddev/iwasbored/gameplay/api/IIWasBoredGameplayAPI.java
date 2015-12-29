package org.squiddev.iwasbored.gameplay.api;

import org.squiddev.iwasbored.gameplay.api.neural.INeuralRegistry;

/**
 * Provider for the IWasBored API
 */
public interface IIWasBoredGameplayAPI {
	/**
	 * Get the neural interface registry.
	 *
	 * @return The neural registry
	 */
	INeuralRegistry neuralRegistry();
}
