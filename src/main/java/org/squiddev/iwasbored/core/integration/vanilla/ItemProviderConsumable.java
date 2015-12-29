package org.squiddev.iwasbored.core.integration.vanilla;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.ILuaObject;
import dan200.computercraft.api.lua.LuaException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import org.squiddev.iwasbored.core.api.provider.AbstractProvider;
import org.squiddev.iwasbored.core.api.reference.IInventorySlot;
import org.squiddev.iwasbored.core.api.reference.IReference;

/**
 * A provider for consumables: food and potions.
 *
 * This enables consuming it: Eating/Drinking it
 */
public class ItemProviderConsumable extends AbstractProvider<IReference<IInventorySlot>, ILuaObject> {
	@Override
	public ILuaObject get(IReference<IInventorySlot> reference) {
		ItemStack stack = reference.get().stack();
		if (stack != null && reference.owner() instanceof EntityPlayer) {
			EnumAction action = stack.getItemUseAction();
			if (action == EnumAction.EAT || action == EnumAction.DRINK) {
				return new ConsumableObject(reference);
			}
		}

		return null;
	}

	public static class ConsumableObject implements ILuaObject {
		private final IReference<IInventorySlot> reference;
		private final EntityPlayer player;

		public ConsumableObject(IReference<IInventorySlot> reference) {
			this.reference = reference;
			this.player = (EntityPlayer) reference.owner();
		}

		@Override
		public String[] getMethodNames() {
			return new String[]{
				"consume",
			};
		}

		@Override
		public Object[] callMethod(ILuaContext context, int method, Object[] objects) throws LuaException, InterruptedException {
			switch (method) {
				case 0:
					reference.get().replace(reference.get().stack().onItemUseFinish(player.worldObj, player));
					return null;
			}

			return null;
		}
	}
}
