package org.squiddev.iwasbored;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import org.squiddev.iwasbored.client.gui.GuiNeuralInterface;
import org.squiddev.iwasbored.items.ItemNeuralConnector;
import org.squiddev.iwasbored.inventory.ContainerArmorItem;
import org.squiddev.iwasbored.inventory.ArmorItem;

public class GuiHandler implements IGuiHandler {
	public static final int GUI_NEURAL = 101;

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		switch (id) {
			case GUI_NEURAL: {
				ArmorItem neuralInterface = ItemNeuralConnector.findNeuralInterface(player);
				return neuralInterface == null ? null : new GuiNeuralInterface(new ContainerArmorItem(neuralInterface));
			}
		}

		return null;
	}

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		switch (id) {
			case GUI_NEURAL: {
				ArmorItem neuralInterface = ItemNeuralConnector.findNeuralInterface(player);
				return neuralInterface == null ? null : new ContainerArmorItem(neuralInterface);
			}
		}

		return null;
	}
}
