package org.squiddev.iwasbored.core.impl.provider;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.ILuaObject;
import dan200.computercraft.api.lua.LuaException;
import net.minecraftforge.fml.common.Optional;
import org.squiddev.cctweaks.api.lua.IArguments;
import org.squiddev.iwasbored.core.api.IIWasBoredCoreAPI;
import org.squiddev.iwasbored.core.api.IWasBoredCoreAPI;
import org.squiddev.iwasbored.core.api.reference.IReference;

public class LuaReference<T> extends LuaObjectCollection {
	private static final IIWasBoredCoreAPI registry = IWasBoredCoreAPI.instance();

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

	public LuaReference(IReference<T> reference, Class<T> target) {
		this(registry.getObjectMethods(reference, target), reference);
	}

	public LuaReference(IReference<T> reference, Class<T> target, String message) {
		this(registry.getObjectMethods(reference, target), reference, message);
	}

	@Override
	public Object[] callMethod(ILuaContext context, int method, Object[] objects) throws LuaException, InterruptedException {
		if (!reference.isValid()) throw new LuaException(movedMessage);
		return super.callMethod(context, method, objects);
	}

	@Override
	@Optional.Method(modid = "CCTweaks")
	public Object[] callMethod(ILuaContext context, int method, IArguments arguments) throws LuaException, InterruptedException {
		if (!reference.isValid()) throw new LuaException(movedMessage);
		return super.callMethod(context, method, arguments);
	}
}
