package org.squiddev.iwasbored.gameplay.integration.vanilla;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.ILuaObject;
import dan200.computercraft.api.lua.LuaException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import org.squiddev.iwasbored.gameplay.api.neural.INeuralInterface;
import org.squiddev.iwasbored.core.api.provider.AbstractProvider;
import org.squiddev.iwasbored.core.impl.provider.LuaReference;
import org.squiddev.iwasbored.core.impl.reference.EntityReference;
import org.squiddev.iwasbored.lib.inventory.InventoryProxy;

/**
 * Adds methods to player's inventories
 */
public class BasicNeuralLuaProvider extends AbstractProvider<INeuralInterface, ILuaObject> {
	@Override
	public ILuaObject get(final INeuralInterface iFace) {
		return new ILuaObject() {
			@Override
			public String[] getMethodNames() {
				return new String[]{
					"getInventory",
					"getArmor",
				};
			}

			@Override
			public Object[] callMethod(ILuaContext iLuaContext, int method, Object[] objects) throws LuaException, InterruptedException {
				EntityPlayer player = iFace.getPlayer();
				switch (method) {
					case 0:
						return new Object[]{new LuaReference<IInventory>(new EntityReference<IInventory>(new InventoryProxy(player.inventory, 0, 9 * 4), player), IInventory.class)};
					case 1:
						return new Object[]{new LuaReference<IInventory>(new EntityReference<IInventory>(new InventoryProxy(player.inventory, 9 * 4, 4), player), IInventory.class)};
				}

				return null;
			}
		};
	}
}
