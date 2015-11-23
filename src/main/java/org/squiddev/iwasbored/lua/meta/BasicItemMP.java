package org.squiddev.iwasbored.lua.meta;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.ILuaObject;
import dan200.computercraft.api.lua.LuaException;
import net.minecraft.item.ItemStack;
import org.squiddev.iwasbored.api.IWasBoredAPI;
import org.squiddev.iwasbored.api.meta.IItemMetaProvider;
import org.squiddev.iwasbored.api.meta.ItemReference;
import org.squiddev.iwasbored.registry.Module;

import java.util.Map;

public class BasicItemMP extends Module implements IItemMetaProvider {
	@Override
	public void getMetadata(ItemStack stack, Map<String, Object> data) {
	}

	@Override
	public ILuaObject getObject(ItemReference stack) {
		return new BasicObject(stack);
	}

	private static final class BasicObject implements ILuaObject {
		private final ItemReference item;

		private BasicObject(ItemReference item) {
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
					return new Object[]{IWasBoredAPI.instance().metaRegistry().getItemMetadata(item.getStack())};
			}
			return null;
		}
	}

	@Override
	public void preInit() {
		IWasBoredAPI.instance().metaRegistry().registerItemProvider(this);
	}
}
