package org.squiddev.iwasbored.core.api;

public final class IWasBoredCoreAPI {
	private static final IIWasBoredCoreAPI API;

	/**
	 * Get the main API entry point
	 *
	 * @return Main API entry point
	 */
	public static IIWasBoredCoreAPI instance() {
		return API;
	}

	static {
		API = (IIWasBoredCoreAPI) CoreNotFoundException.load("IWasBored-Core", "org.squiddev.iwasbored.core.CoreAPI");
	}
}
