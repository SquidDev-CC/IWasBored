package org.squiddev.iwasbored.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.squiddev.iwasbored.GuiHandler;
import org.squiddev.iwasbored.IWasBored;
import org.squiddev.iwasbored.api.IWasBoredAPI;
import org.squiddev.iwasbored.api.neural.INeuralReference;
import org.squiddev.iwasbored.registry.IClientModule;
import org.squiddev.iwasbored.utils.Helpers;


public class ItemNeuralConnector extends Item implements IClientModule {
	private static final String NAME = "neuralConnector";

	public ItemNeuralConnector() {
		this.setUnlocalizedName(IWasBored.RESOURCE_DOMAIN + "." + NAME);
		this.setCreativeTab(IWasBored.getCreativeTab());
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (!world.isRemote) {
			INeuralReference reference = IWasBoredAPI.instance().neuralRegistry().getNeuralInterface(player);
			if (reference != null) {
				reference.get().turnOn();
				player.openGui(IWasBored.instance, GuiHandler.GUI_NEURAL, player.worldObj, 0, 0, 0);
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
