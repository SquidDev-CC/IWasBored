package org.squiddev.iwasbored.client.gui;

import dan200.computercraft.client.gui.GuiComputer;
import dan200.computercraft.shared.computer.core.ComputerFamily;
import org.squiddev.iwasbored.items.ItemUtils;
import org.squiddev.iwasbored.neural.NeuralManager;
import org.squiddev.iwasbored.inventory.ContainerArmorItem;

/**
 * Neural connector GUI
 */
public class GuiNeuralInterface extends GuiComputer {
	public GuiNeuralInterface(ContainerArmorItem container) {
		super(
			container,
			ComputerFamily.Advanced,
			NeuralManager.getClient(ItemUtils.getTag(container.getStack())),
			51,
			19
		);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {

	}
}
