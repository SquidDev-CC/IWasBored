package org.squiddev.iwasbored.lib.registry;

import net.minecraftforge.fml.common.Loader;
import org.squiddev.iwasbored.lib.registry.Module;

public abstract class IntegrationModule extends Module {
	public final String modName;

	public IntegrationModule(String modName) {
		this.modName = modName;
	}

	@Override
	public boolean canLoad() {
		return Loader.isModLoaded(this.modName);
	}
}
