package org.squiddev.iwasbored.gameplay.api;

import org.squiddev.iwasbored.core.api.CoreNotFoundException;

public final class IWasBoredGameplayAPI {
	private static final IIWasBoredGameplayAPI API;

	/**
	 * Get the main API entry point
	 *
	 * @return Main API entry point
	 */
	public static IIWasBoredGameplayAPI instance() {
		return API;
	}

	static {
		API = (IIWasBoredGameplayAPI) CoreNotFoundException.load("IWasBored-Gameplay", "org.squiddev.iwasbored.gameplay.GameplayAPI");
	}
}
