package org.squiddev.iwasbored.api;

/**
 * Thrown when we cannot load an API
 */
public class CoreNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 4589980568297257635L;

	public CoreNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
