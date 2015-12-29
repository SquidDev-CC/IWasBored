package org.squiddev.iwasbored.gameplay.integration.vanilla;

import org.squiddev.iwasbored.gameplay.IWasBoredGameplay;
import org.squiddev.iwasbored.gameplay.impl.neural.NeuralInterfaceReference;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import org.squiddev.iwasbored.core.api.provider.AbstractProvider;
import org.squiddev.iwasbored.core.impl.reference.EntityReference;
import org.squiddev.iwasbored.core.impl.reference.SlotReference;
import org.squiddev.iwasbored.gameplay.api.neural.INeuralReference;

/**
 * Basic provider for armor items
 */
public class ArmorNeuralProvider extends AbstractProvider<EntityLivingBase, INeuralReference> {
	@Override
	public INeuralReference get(EntityLivingBase entityLivingBase) {
		if (entityLivingBase instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entityLivingBase;
			ItemStack stack = player.getCurrentArmor(3);
			if (stack != null && stack.getItem() == IWasBoredGameplay.Items.itemNeuralInterface) {
				return new NeuralInterfaceReference(new SlotReference(new EntityReference<IInventory>(player.inventory, player), 36 + 3));
			}
		}

		return null;
	}
}
