package org.squiddev.iwasbored.integration.vanilla;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.ILuaObject;
import dan200.computercraft.api.lua.LuaException;
import org.squiddev.iwasbored.api.IWasBoredAPI;
import org.squiddev.iwasbored.api.provider.AbstractProvider;
import org.squiddev.iwasbored.api.reference.IInventorySlot;
import org.squiddev.iwasbored.api.reference.IReference;

/**
 * Provides a method to get metadata for an item
 */
public class BasicStackProvider extends AbstractProvider<IReference<IInventorySlot>, ILuaObject> {
	@Override
	public ILuaObject get(IReference<IInventorySlot> reference) {
		return new BasicObject(reference);
	}

	public static final class BasicObject implements ILuaObject {
		private final IReference<IInventorySlot> item;

		private BasicObject(IReference<IInventorySlot> item) {
			this.item = item;
		}

		@Override
		public String[] getMethodNames() {
			return new String[]{
				"getMetadata"
			};
		}

		@Override
		public Object[] callMethod(ILuaContext context, int method, Object[] arguments) throws LuaException, InterruptedException {
			switch (method) {
				case 0:
					return new Object[]{IWasBoredAPI.instance().coreRegistry().getItemMetadata(item.get().stack())};
			}
			return null;
		}
	}
}
