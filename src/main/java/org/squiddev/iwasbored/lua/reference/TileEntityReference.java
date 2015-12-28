package org.squiddev.iwasbored.lua.reference;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import org.squiddev.iwasbored.api.reference.AbstractReference;

import java.lang.ref.WeakReference;

public class TileEntityReference<T> extends AbstractReference<T> {
	private final WeakReference<TileEntity> te;

	public TileEntityReference(T object, TileEntity te) {
		super(object);
		this.te = new WeakReference<TileEntity>(te);
	}

	@Override
	public boolean isValid() {
		TileEntity entity = te.get();
		if (entity == null || entity.isInvalid()) return false;

		World world = entity.getWorld();
		return world != null && entity.equals(world.getTileEntity(entity.getPos()));
	}

	@Override
	public Object owner() {
		return te.get();
	}
}
