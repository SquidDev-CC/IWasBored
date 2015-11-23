package org.squiddev.iwasbored.api;

public final class IWasBoredAPI {
	private static final IIWasBoredAPI API;

	/**
	 * Get the main API entry point
	 *
	 * @return Main API entry point
	 */
	public static IIWasBoredAPI instance() {
		return API;
	}

	static {
		IIWasBoredAPI api;
		String name = "org.squiddev.iwasbored.API";
		try {
			Class<?> registryClass = Class.forName(name);
			api = (IIWasBoredAPI) registryClass.newInstance();
		} catch (ClassNotFoundException e) {
			throw new CoreNotFoundException("Cannot load IWasBored API as " + name + " cannot be found", e);
		} catch (InstantiationException e) {
			throw new CoreNotFoundException("Cannot load IWasBored API as " + name + " cannot be created", e);
		} catch (IllegalAccessException e) {
			throw new CoreNotFoundException("Cannot load IWasBored API as " + name + " cannot be accessed", e);
		}
		API = api;
	}
}
