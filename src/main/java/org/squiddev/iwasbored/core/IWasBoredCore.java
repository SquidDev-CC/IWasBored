package org.squiddev.iwasbored.core;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.squiddev.iwasbored.core.integration.vanilla.VanillaIntegration;
import org.squiddev.iwasbored.lib.registry.Registry;

@Mod(modid = IWasBoredCore.ID, name = IWasBoredCore.NAME, version = IWasBoredCore.VERSION, dependencies = IWasBoredCore.DEPENDENCIES)
public class IWasBoredCore {
	public static final String ID = "IWasBored|Core";
	public static final String NAME = "IWasBored Core";
	public static final String VERSION = "${mod_version}";
	public static final String RESOURCE_DOMAIN = "iwasbored";
	public static final String DEPENDENCIES = "required-after:ComputerCraft@[1.76,);after:CCTurtle;after:CCTweaks";

	private final Registry registry = new Registry();

	public IWasBoredCore() {
		// Core
		registry.addModule(new VanillaIntegration());
	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		registry.preInit();
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		registry.init();
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		registry.postInit();
	}
}
