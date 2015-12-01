package org.squiddev.iwasbored.lua;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.ILuaObject;
import dan200.computercraft.api.lua.LuaException;
import org.squiddev.iwasbored.api.reference.IReference;

public class LuaReference<T> extends LuaObjectCollection {
	private final IReference<T> reference;
	private final String movedMessage;

	public LuaReference(Iterable<ILuaObject> objects, IReference<T> reference, String movedMessage) {
		super(objects);
		this.reference = reference;
		this.movedMessage = movedMessage;
	}

	public LuaReference(Iterable<ILuaObject> objects, IReference<T> reference) {
		this(objects, reference, "Object is no longer valid");
	}

	@Override
	public Object[] callMethod(ILuaContext context, int method, Object[] objects) throws LuaException, InterruptedException {
		if (!reference.isValid()) throw new LuaException(movedMessage);
		return super.callMethod(context, method, objects);
	}
}
