package org.squiddev.iwasbored.gameplay.neural;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import org.squiddev.iwasbored.gameplay.api.neural.INeuralReference;

public class NeuralContainer extends Container {
	private final INeuralReference reference;

	public NeuralContainer(INeuralReference reference) {
		this.reference = reference;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return player != null && player.isEntityAlive() && reference.isValid();
	}
}
