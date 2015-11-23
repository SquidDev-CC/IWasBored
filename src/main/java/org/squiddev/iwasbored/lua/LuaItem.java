package org.squiddev.iwasbored.lua;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import org.squiddev.iwasbored.api.IWasBoredAPI;
import org.squiddev.iwasbored.api.meta.ItemReference;

public class LuaItem extends LuaObjectCollection {
	private final ItemReference item;

	public LuaItem(ItemReference item) {
		super(IWasBoredAPI.instance().metaRegistry().getItemMethods(item));
		this.item = item;
	}

	@Override
	public Object[] callMethod(ILuaContext context, int method, Object[] objects) throws LuaException, InterruptedException {
		if (!item.isValid()) throw new LuaException("Item is no longer there");
		return super.callMethod(context, method, objects);
	}
}
