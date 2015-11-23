package org.squiddev.iwasbored;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import dan200.computercraft.ComputerCraft;
import net.minecraft.creativetab.CreativeTabs;
import org.squiddev.iwasbored.neural.NeuralManager;

@Mod(modid = IWasBored.ID, name = IWasBored.NAME, version = IWasBored.VERSION, dependencies = IWasBored.DEPENDENCIES)
public class IWasBored {
	public static final String ID = "IWasBored";
	public static final String NAME = ID;
	public static final String VERSION = "@mod_version@";
	public static final String RESOURCE_DOMAIN = "iwasbored";
	public static final String DEPENDENCIES = "required-after:ComputerCraft@[1.74,);after:CCTurtle;after:CCTweaks";

	public static final String PREFIX_MOD = RESOURCE_DOMAIN + ":";
	public static final String PREFIX_MODELS = PREFIX_MOD + "textures/model/";

	@Mod.Instance
	public static IWasBored instance;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		FMLCommonHandler.instance().bus().register(new FmlEvents());
		Registry.preInit();
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		Registry.init();

		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		Registry.postInit();
	}

	public static CreativeTabs getCreativeTab() {
		return ComputerCraft.mainCreativeTab;
	}

	@Mod.EventHandler
	public void serverStarting(FMLServerAboutToStartEvent event) {
		NeuralManager.setup();
	}

	@Mod.EventHandler
	public void serverStopping(FMLServerStoppingEvent event) {
		NeuralManager.tearDown();
	}

	public class FmlEvents {
		@SubscribeEvent
		public void onServerTick(TickEvent.ServerTickEvent event) {
			if (event.phase == TickEvent.Phase.START) {
				NeuralManager.update();
			}
		}
	}

}
