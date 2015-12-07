package org.squiddev.iwasbored.lua.reference;

import com.google.common.base.Preconditions;
import net.minecraft.entity.EntityLivingBase;
import org.squiddev.iwasbored.api.reference.AbstractReference;

import java.lang.ref.WeakReference;

public class EntityReference<T> extends AbstractReference<T> {
	private final WeakReference<EntityLivingBase> entity;

	public EntityReference(T object, EntityLivingBase entity) {
		super(object);
		Preconditions.checkNotNull(entity, "entity cannot be null");
		this.entity = new WeakReference<EntityLivingBase>(entity);
	}

	@Override
	public boolean isValid() {
		EntityLivingBase entity = this.entity.get();
		return entity != null && entity.isEntityAlive();
	}

	@Override
	public Object owner() {
		return entity.get();
	}
}
