package org.squiddev.iwasbored;

import dan200.computercraft.ComputerCraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.squiddev.iwasbored.neural.NeuralManager;
import org.squiddev.iwasbored.registry.Registry;

@Mod(modid = IWasBored.ID, name = IWasBored.NAME, version = IWasBored.VERSION, dependencies = IWasBored.DEPENDENCIES)
public class IWasBored {
	public static final String ID = "IWasBored";
	public static final String NAME = ID;
	public static final String VERSION = "@mod_version@";
	public static final String RESOURCE_DOMAIN = "iwasbored";
	public static final String DEPENDENCIES = "required-after:ComputerCraft@[1.76,);after:CCTurtle;after:CCTweaks";

	public static final String PREFIX_MOD = RESOURCE_DOMAIN + ":";
	public static final String PREFIX_TEXTURES = PREFIX_MOD + "textures/";

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
