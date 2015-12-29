package org.squiddev.iwasbored.core.impl.provider;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.ILuaObject;
import dan200.computercraft.api.lua.LuaException;

import java.util.ArrayList;
import java.util.List;

/**
 * Collection of Lua objects
 */
public class LuaObjectCollection implements ILuaObject {
	private final class ObjectWrapper {
		private final ILuaObject object;
		private final int index;

		private ObjectWrapper(ILuaObject object, int index) {
			this.object = object;
			this.index = index;
		}

		public Object[] callMethod(ILuaContext context, Object[] args) throws LuaException, InterruptedException {
			return object.callMethod(context, index, args);
		}
	}

	private final String[] methods;
	private final ObjectWrapper[] wrappers;

	public LuaObjectCollection(Iterable<ILuaObject> objects) {
		List<String> methods = new ArrayList<String>();
		List<ObjectWrapper> wrappers = new ArrayList<ObjectWrapper>();

		for (ILuaObject object : objects) {
			String[] names = object.getMethodNames();
			for (int i = 0; i < names.length; i++) {
				methods.add(names[i]);
				wrappers.add(new ObjectWrapper(object, i));
			}
		}

		int size = methods.size();
		this.methods = methods.toArray(new String[size]);
		this.wrappers = wrappers.toArray(new ObjectWrapper[size]);
	}

	@Override
	public String[] getMethodNames() {
		return methods;
	}

	@Override
	public Object[] callMethod(ILuaContext context, int method, Object[] objects) throws LuaException, InterruptedException {
		return method < wrappers.length ? wrappers[method].callMethod(context, objects) : null;
	}
}
