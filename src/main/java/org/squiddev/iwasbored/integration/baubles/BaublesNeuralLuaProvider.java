package org.squiddev.iwasbored.integration.baubles;

import baubles.common.lib.PlayerHandler;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.ILuaObject;
import dan200.computercraft.api.lua.LuaException;
import net.minecraft.entity.player.EntityPlayer;
import org.squiddev.iwasbored.api.neural.INeuralInterface;
import org.squiddev.iwasbored.api.provider.AbstractProvider;
import org.squiddev.iwasbored.inventory.LuaInventory;

/**
 * Adds inventory access for baubles slots
 */
public class BaublesNeuralLuaProvider extends AbstractProvider<INeuralInterface, ILuaObject> {
	@Override
	public ILuaObject get(final INeuralInterface iFace) {
		return new ILuaObject() {
			@Override
			public String[] getMethodNames() {
				return new String[]{"getBaublesInventory"};
			}

			@Override
			public Object[] callMethod(ILuaContext context, int method, Object[] args) throws LuaException, InterruptedException {
				EntityPlayer player = iFace.getPlayer();
				return new Object[]{new LuaInventory(PlayerHandler.getPlayerBaubles(player), player)};
			}
		};
	}
}
