package org.squiddev.iwasbored.api;

import org.squiddev.iwasbored.api.neural.INeuralRegistry;
import org.squiddev.iwasbored.api.provider.IProviderRegistry;

/**
 * Provider for the IWasBored API
 */
public interface IIWasBoredAPI {
	/**
	 * Get the neural interface registry.
	 *
	 * @return The neural registry
	 */
	INeuralRegistry neuralRegistry();

	/**
	 * Get the main provider registry
	 *
	 * @return The main provider registry
	 */
	IProviderRegistry coreRegistry();
}
