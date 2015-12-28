package org.squiddev.iwasbored.integration.baubles;

import baubles.common.container.InventoryBaubles;
import baubles.common.lib.PlayerHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import org.squiddev.iwasbored.api.neural.INeuralReference;
import org.squiddev.iwasbored.api.provider.AbstractProvider;
import org.squiddev.iwasbored.api.reference.IReference;
import org.squiddev.iwasbored.impl.neural.NeuralInterfaceReference;
import org.squiddev.iwasbored.impl.reference.EntityReference;
import org.squiddev.iwasbored.impl.reference.SlotReference;
import org.squiddev.iwasbored.registry.Registry;

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
			if (stack != null && stack.getItem() == Registry.itemNeuralInterface) {
				IReference<IInventory> inventory = new EntityReference<IInventory>(baubles, player);
				SlotReference slot = new SlotReference(inventory, 0);
				return new NeuralInterfaceReference(slot);
			}
		}

		return null;
	}
}
