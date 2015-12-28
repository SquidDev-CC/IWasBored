package org.squiddev.iwasbored.integration.vanilla;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import org.squiddev.iwasbored.api.neural.INeuralReference;
import org.squiddev.iwasbored.api.provider.AbstractProvider;
import org.squiddev.iwasbored.impl.neural.NeuralInterfaceReference;
import org.squiddev.iwasbored.impl.reference.EntityReference;
import org.squiddev.iwasbored.impl.reference.SlotReference;
import org.squiddev.iwasbored.registry.Registry;

/**
 * Basic provider for armor items
 */
public class ArmorNeuralProvider extends AbstractProvider<EntityLivingBase, INeuralReference> {
	@Override
	public INeuralReference get(EntityLivingBase entityLivingBase) {
		if (entityLivingBase instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entityLivingBase;
			ItemStack stack = player.getCurrentArmor(3);
			if (stack != null && stack.getItem() == Registry.itemNeuralInterface) {
				return new NeuralInterfaceReference(new SlotReference(new EntityReference<IInventory>(player.inventory, player), 36 + 3));
			}
		}

		return null;
	}
}
