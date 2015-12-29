package org.squiddev.iwasbored.gameplay.integration.baubles;

import baubles.common.container.InventoryBaubles;
import baubles.common.lib.PlayerHandler;
import org.squiddev.iwasbored.gameplay.IWasBoredGameplay;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import org.squiddev.iwasbored.gameplay.api.neural.INeuralReference;
import org.squiddev.iwasbored.core.api.provider.AbstractProvider;
import org.squiddev.iwasbored.core.api.reference.IReference;
import org.squiddev.iwasbored.gameplay.impl.neural.NeuralInterfaceReference;
import org.squiddev.iwasbored.core.impl.reference.EntityReference;
import org.squiddev.iwasbored.core.impl.reference.SlotReference;

/**
 * Looks in the baubles slot for neural interfaces
 */
public class BaublesNeuralProvider extends AbstractProvider<EntityLivingBase, INeuralReference> {
	@Override
	public INeuralReference get(EntityLivingBase entityLivingBase) {
		if (entityLivingBase instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entityLivingBase;

			InventoryBaubles baubles = PlayerHandler.getPlayerBaubles(player);

			ItemStack stack = baubles.getStackInSlot(0);
			if (stack != null && stack.getItem() == IWasBoredGameplay.Items.itemNeuralInterface) {
				IReference<IInventory> inventory = new EntityReference<IInventory>(baubles, player);
				SlotReference slot = new SlotReference(inventory, 0);
				return new NeuralInterfaceReference(slot);
			}
		}

		return null;
	}
}
