package org.squiddev.iwasbored.core.impl.provider;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.ILuaObject;
import dan200.computercraft.api.lua.LuaException;
import net.minecraftforge.fml.common.Optional;
import org.squiddev.cctweaks.api.lua.ArgumentDelegator;
import org.squiddev.cctweaks.api.lua.IArguments;
import org.squiddev.cctweaks.api.lua.ILuaObjectWithArguments;
import org.squiddev.iwasbored.lib.DebugLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * Collection of Lua objects
 */
@Optional.Interface(iface = "org.squiddev.cctweaks.api.lua.ILuaObjectWithArguments", modid = "CCTweaks")
public class LuaObjectCollection implements ILuaObject, ILuaObjectWithArguments {
	private final class ObjectWrapper {
		public final ILuaObject object;
		public final int index;

		public ObjectWrapper(ILuaObject object, int index) {
			this.object = object;
			this.index = index;
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
		if (method >= wrappers.length) return null;

		ObjectWrapper wrapper = wrappers[method];
		try {
			return wrapper.object.callMethod(context, wrapper.index, objects);
		} catch (RuntimeException e) {
			DebugLogger.debug("Error calling method", e);
			throw e;
		}
	}

	@Override
	@Optional.Method(modid = "CCTweaks")
	public Object[] callMethod(ILuaContext context, int method, IArguments arguments) throws LuaException, InterruptedException {
		if (method >= wrappers.length) return null;

		ObjectWrapper wrapper = wrappers[method];
		try {
			return ArgumentDelegator.delegateLuaObject(wrapper.object, context, wrapper.index, arguments);
		} catch (RuntimeException e) {
			DebugLogger.debug("Error calling method", e);
			throw e;
		}
	}
}
