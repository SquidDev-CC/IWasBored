package org.squiddev.iwasbored.neural;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.squiddev.iwasbored.api.IWasBoredAPI;
import org.squiddev.iwasbored.api.neural.INeuralReference;
import org.squiddev.iwasbored.api.provider.IProvider;
import org.squiddev.iwasbored.inventory.SingleItem;
import org.squiddev.iwasbored.registry.Module;
import org.squiddev.iwasbored.registry.Registry;

/**
 * Basic provider for armor items
 */
public class ArmorProvider extends Module implements IProvider<EntityLivingBase, INeuralReference> {
	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public INeuralReference get(EntityLivingBase entityLivingBase) {
		if (entityLivingBase instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entityLivingBase;
			ItemStack stack = player.getCurrentArmor(3);
			if (stack != null && stack.getItem() == Registry.itemNeuralInterface) {
				return new NeuralInterfaceReference(new SingleItem(player.inventory, 36 + 3, player));
			}
		}

		return null;
	}

	@Override
	public void postInit() {
		IWasBoredAPI.instance().neuralRegistry().registerNeuralProvider(this);
	}
}
