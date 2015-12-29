package org.squiddev.iwasbored.gameplay.items;

import org.squiddev.iwasbored.gameplay.GuiHandler;
import org.squiddev.iwasbored.gameplay.IWasBoredGameplay;
import org.squiddev.iwasbored.gameplay.utils.Helpers;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.squiddev.iwasbored.gameplay.api.IWasBoredGameplayAPI;
import org.squiddev.iwasbored.gameplay.api.neural.INeuralReference;
import org.squiddev.iwasbored.lib.registry.IClientModule;


public class ItemNeuralConnector extends Item implements IClientModule {
	private static final String NAME = "neuralConnector";

	public ItemNeuralConnector() {
		this.setUnlocalizedName(IWasBoredGameplay.RESOURCE_DOMAIN + "." + NAME);
		this.setCreativeTab(IWasBoredGameplay.getCreativeTab());
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (!world.isRemote) {
			INeuralReference reference = IWasBoredGameplayAPI.instance().neuralRegistry().getNeuralInterface(player);
			if (reference != null) {
				reference.get().turnOn();
				player.openGui(IWasBoredGameplay.instance, GuiHandler.GUI_NEURAL, player.worldObj, 0, 0, 0);
			}
		}

		return stack;
	}

	//region IModule
	@Override
	public boolean canLoad() {
		return true;
	}

	@Override
	public void preInit() {
		GameRegistry.registerItem(this, NAME);
	}

	@Override
	public void init() {
	}

	@Override
	public void postInit() {
	}

	@Override
	public void clientInit() {
		Helpers.setupModel(this, 0, NAME);
	}
	//endregion
}
