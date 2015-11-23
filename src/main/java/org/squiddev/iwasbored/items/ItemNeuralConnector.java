package org.squiddev.iwasbored.items;

import baubles.common.container.InventoryBaubles;
import baubles.common.lib.PlayerHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.squiddev.iwasbored.GuiHandler;
import org.squiddev.iwasbored.IWasBored;
import org.squiddev.iwasbored.inventory.ArmorItem;
import org.squiddev.iwasbored.neural.NeuralManager;
import org.squiddev.iwasbored.registry.IModule;
import org.squiddev.iwasbored.registry.Registry;


public class ItemNeuralConnector extends Item implements IModule {
	private static final String NAME = "neuralConnector";

	public ItemNeuralConnector() {
		this.setUnlocalizedName(IWasBored.RESOURCE_DOMAIN + "." + NAME);
		this.setTextureName(IWasBored.RESOURCE_DOMAIN + ":" + NAME);
		this.setCreativeTab(IWasBored.getCreativeTab());
	}

	public static ArmorItem findNeuralInterface(EntityPlayer player) {
		InventoryBaubles baubles = PlayerHandler.getPlayerBaubles(player);

		ItemStack stack = baubles.getStackInSlot(0);
		if (stack != null && stack.getItem() == Registry.itemNeuralInterface) return new ArmorItem(true, stack, 0);

		stack = player.getCurrentArmor(3);
		if (stack != null && stack.getItem() == Registry.itemNeuralInterface) {
			return new ArmorItem(false, stack, 36 + 3);
		}

		return null;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (!world.isRemote) {
			ArmorItem neuralStack = findNeuralInterface(player);
			if (neuralStack != null) {
				NeuralManager.get(ItemUtils.getTag(neuralStack.stack), player).turnOn();
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
	//endregion
}
