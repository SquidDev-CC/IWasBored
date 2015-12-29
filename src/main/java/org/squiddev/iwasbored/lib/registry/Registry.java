package org.squiddev.iwasbored.lib.registry;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashSet;
import java.util.Set;

/**
 * The proxy class
 */
public final class Registry {
	private final Set<IModule> modules = new HashSet<IModule>();

	private boolean preInit = false;
	private boolean init = false;
	private boolean postInit = false;

	public void addModule(IModule module) {
		if (module instanceof IClientModule) {
			module = new RegisterWrapperClient((IClientModule) module);
		}

		modules.add(module);

		if (preInit && module.canLoad()) {
			module.preInit();
			if (init) {
				module.init();
				if (postInit) module.postInit();
			}
		}
	}

	public void preInit() {
		if (preInit) throw new IllegalStateException("Attempting to preInit twice");
		preInit = true;
		for (IModule module : modules) {
			if (module.canLoad()) module.preInit();
		}
	}

	public void init() {
		if (!preInit) throw new IllegalStateException("Cannot init before preInit");
		if (init) throw new IllegalStateException("Attempting to init twice");

		init = true;
		for (IModule module : modules) {
			if (module.canLoad()) module.init();
		}
	}

	public void postInit() {
		if (!preInit) throw new IllegalStateException("Cannot init before preInit");
		if (!init) throw new IllegalStateException("Cannot postInit before init");
		if (postInit) throw new IllegalStateException("Attempting to postInit twice");

		postInit = true;
		for (IModule module : modules) {
			if (module.canLoad()) module.postInit();
		}
	}

	/**
	 * Magic classes to allow calling client only methods
	 */
	private static class RegisterWrapper implements IModule {
		protected final IClientModule base;

		private RegisterWrapper(IClientModule base) {
			this.base = base;
		}

		@Override
		public boolean canLoad() {
			return base.canLoad();
		}

		@Override
		public void preInit() {
			base.preInit();
		}

		@Override
		public void init() {
			base.init();
		}

		@Override
		public void postInit() {
			base.postInit();
		}
	}

	/**
	 * Magic classes to allow calling client only methods
	 */
	private static class RegisterWrapperClient extends RegisterWrapper {
		private RegisterWrapperClient(IClientModule base) {
			super(base);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void init() {
			super.init();
			base.clientInit();
		}
	}
}

