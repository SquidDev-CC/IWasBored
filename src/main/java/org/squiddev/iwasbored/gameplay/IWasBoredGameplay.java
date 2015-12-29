package org.squiddev.iwasbored.gameplay;

import dan200.computercraft.ComputerCraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.squiddev.iwasbored.gameplay.client.render.RenderModelInterface;
import org.squiddev.iwasbored.gameplay.integration.baubles.BaublesIntegration;
import org.squiddev.iwasbored.gameplay.integration.vanilla.VanillaIntegration;
import org.squiddev.iwasbored.gameplay.items.ItemNeuralConnector;
import org.squiddev.iwasbored.gameplay.items.ItemNeuralInterface;
import org.squiddev.iwasbored.gameplay.neural.NeuralManager;
import org.squiddev.iwasbored.lib.registry.Registry;

@Mod(modid = IWasBoredGameplay.ID, name = IWasBoredGameplay.NAME, version = IWasBoredGameplay.VERSION, dependencies = IWasBoredGameplay.DEPENDENCIES)
public class IWasBoredGameplay {
	public static final String ID = "IWasBored|Gameplay";
	public static final String NAME = "IWasBored Gameplay";
	public static final String VERSION = "${mod_version}";
	public static final String RESOURCE_DOMAIN = "iwasbored";
	public static final String DEPENDENCIES = "required-after:ComputerCraft@[1.76,);required-after:IWasBored|Core;after:CCTurtle";

	public static final String PREFIX_MOD = RESOURCE_DOMAIN + ":";
	public static final String PREFIX_TEXTURES = PREFIX_MOD + "textures/";

	@Mod.Instance(ID)
	public static IWasBoredGameplay instance;

	private final Registry registry = new Registry();

	public static class Items {
		public static ItemNeuralInterface itemNeuralInterface;
		public static ItemNeuralConnector itemNeuralConnector;
	}

	public void setupRegistry() {
		registry.addModule(Items.itemNeuralInterface = new ItemNeuralInterface());
		registry.addModule(Items.itemNeuralConnector = new ItemNeuralConnector());

		registry.addModule(new RenderModelInterface());
		registry.addModule(new VanillaIntegration());
		registry.addModule(new BaublesIntegration());
	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		setupRegistry();
		FMLCommonHandler.instance().bus().register(new FmlEvents());
		registry.preInit();
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		registry.init();

		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		registry.postInit();
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
