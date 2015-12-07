package org.squiddev.iwasbored.neural;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import org.squiddev.iwasbored.api.neural.INeuralInterface;
import org.squiddev.iwasbored.api.neural.INeuralReference;
import org.squiddev.iwasbored.api.reference.IInventorySlot;
import org.squiddev.iwasbored.api.reference.IReference;
import org.squiddev.iwasbored.items.ItemUtils;

public class NeuralInterfaceReference implements INeuralReference {
	private final IReference<IInventorySlot> item;

	public NeuralInterfaceReference(IReference<IInventorySlot> item) {
		this.item = item;
	}

	@Override
	public boolean isValid() {
		return item.isValid() && item.owner() instanceof EntityPlayer;
	}

	@Override
	public INeuralInterface get() {
		if (isValid()) {
			return NeuralManager.get(ItemUtils.getTag(item.get().stack()), (EntityPlayer) item.owner());
		}

		return null;
	}

	@Override
	public Object owner() {
		return item.owner();
	}

	@Override
	public NBTTagCompound getTag() {
		return isValid() ? ItemUtils.getTag(item.get().stack()) : null;
	}
}
