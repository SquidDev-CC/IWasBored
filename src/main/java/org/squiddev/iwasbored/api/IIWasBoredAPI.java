package org.squiddev.iwasbored.api;

import org.squiddev.iwasbored.api.meta.IMetaRegistry;
import org.squiddev.iwasbored.api.neural.INeuralRegistry;

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
	 * Get the meta provider registry
	 *
	 * @return The meta provider registry
	 */
	IMetaRegistry metaRegistry();
}
