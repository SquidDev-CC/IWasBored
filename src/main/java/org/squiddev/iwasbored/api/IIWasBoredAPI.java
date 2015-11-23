package org.squiddev.iwasbored.api;

import org.squiddev.iwasbored.api.meta.IMetaRegistry;
import org.squiddev.iwasbored.api.neural.INeuralUpgradeRegistry;

/**
 * Provider for the IWasBored API
 */
public interface IIWasBoredAPI {
	/**
	 * Get the neural upgrade registry.
	 *
	 * @return The neural registry
	 */
	INeuralUpgradeRegistry neuralRegistry();

	/**
	 * Get the meta provider registry
	 *
	 * @return The meta provider registry
	 */
	IMetaRegistry metaRegistry();
}
