package org.squiddev.iwasbored.neural;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import org.squiddev.iwasbored.api.neural.INeuralInterface;
import org.squiddev.iwasbored.api.neural.INeuralReference;
import org.squiddev.iwasbored.inventory.SingleItem;
import org.squiddev.iwasbored.items.ItemUtils;

public class NeuralInterfaceReference implements INeuralReference {
	private final SingleItem item;

	public NeuralInterfaceReference(SingleItem item) {
		this.item = item;
	}

	@Override
	public boolean isValid() {
		return item.isValid();
	}

	@Override
	public INeuralInterface get() {
		if (item.isValid() && item.getEntity() instanceof EntityPlayer) {
			return NeuralManager.get(ItemUtils.getTag(item.get()), (EntityPlayer) item.getEntity());
		} else {
			return null;
		}
	}

	@Override
	public NBTTagCompound getTag() {
		return item.isValid() ? ItemUtils.getTag(item.get()) : null;
	}
}
