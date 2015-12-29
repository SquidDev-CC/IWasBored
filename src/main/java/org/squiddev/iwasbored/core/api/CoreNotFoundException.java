package org.squiddev.iwasbored.core.api;

/**
 * Thrown when we cannot load an API
 */
public class CoreNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 4589980568297257635L;

	public CoreNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public static Object load(String name, String className) {
		try {
			Class<?> registryClass = Class.forName(className);
			return registryClass.newInstance();
		} catch (ClassNotFoundException e) {
			throw new CoreNotFoundException("Cannot load " + name + " API as " + className + " cannot be found", e);
		} catch (InstantiationException e) {
			throw new CoreNotFoundException("Cannot load " + name + " API as " + className + " cannot be created", e);
		} catch (IllegalAccessException e) {
			throw new CoreNotFoundException("Cannot load " + name + " API as " + className + " cannot be accessed", e);
		}
	}
}
