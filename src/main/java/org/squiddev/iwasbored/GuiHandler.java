package org.squiddev.iwasbored;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import org.squiddev.iwasbored.api.IWasBoredAPI;
import org.squiddev.iwasbored.api.neural.INeuralReference;
import org.squiddev.iwasbored.client.gui.GuiNeuralInterface;
import org.squiddev.iwasbored.neural.NeuralContainer;

public class GuiHandler implements IGuiHandler {
	public static final int GUI_NEURAL = 101;

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		switch (id) {
			case GUI_NEURAL: {
				INeuralReference reference = IWasBoredAPI.instance().neuralRegistry().getNeuralInterface(player);
				return reference == null ? null : new GuiNeuralInterface(reference);
			}
		}

		return null;
	}

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		switch (id) {
			case GUI_NEURAL: {
				INeuralReference reference = IWasBoredAPI.instance().neuralRegistry().getNeuralInterface(player);
				return reference == null ? null : new NeuralContainer(reference);
			}
		}

		return null;
	}
}
