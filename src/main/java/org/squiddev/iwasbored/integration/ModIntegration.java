package org.squiddev.iwasbored.integration;

import cpw.mods.fml.common.Loader;
import org.squiddev.iwasbored.registry.Module;

public abstract class ModIntegration extends Module {
	public final String modName;

	public ModIntegration(String modName) {
		this.modName = modName;
	}

	@Override
	public boolean canLoad() {
		return Loader.isModLoaded(this.modName);
	}
}
