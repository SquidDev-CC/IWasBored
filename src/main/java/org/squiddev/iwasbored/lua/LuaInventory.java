package org.squiddev.iwasbored.lua;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.ILuaObject;
import dan200.computercraft.api.lua.LuaException;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import org.squiddev.iwasbored.api.IWasBoredAPI;
import org.squiddev.iwasbored.api.reference.ItemReference;
import org.squiddev.iwasbored.inventory.InventoryUtils;
import org.squiddev.iwasbored.inventory.SingleItem;

import java.util.HashMap;

public class LuaInventory implements ILuaObject {
	private final EntityLivingBase entity;
	private final IInventory inventory;
	private final int size;
	private final int offset;

	public LuaInventory(EntityLivingBase entity, IInventory inventory) {
		this(entity, inventory, 0, inventory.getSizeInventory());
	}

	public LuaInventory(EntityLivingBase entity, IInventory inventory, int offset, int size) {
		this.entity = entity;
		this.inventory = inventory;
		this.size = size;
		this.offset = offset;
	}

	@Override
	public String[] getMethodNames() {
		return new String[]{
			"list",
			"getItem",
			"getSize"
		};
	}

	@Override
	public Object[] callMethod(ILuaContext context, int method, Object[] args) throws LuaException, InterruptedException {
		switch (method) {
			case 0: { // list
				HashMap<Integer, Object> items = new HashMap<Integer, Object>();
				for (int i = 0; i < size; i++) {
					ItemStack stack = inventory.getStackInSlot(i + offset);
					if (stack != null) {
						items.put(i + 1, InventoryUtils.getBasicProperties(stack));
					}
				}

				return new Object[]{items};
			}
			case 1: {
				if (args.length < 1 || !(args[0] instanceof Number)) throw new LuaException("Expected number");
				int slot = ((Number) args[0]).intValue();
				if (slot < 1 || slot > size) throw new LuaException("Slot out of range");

				SingleItem item = new SingleItem(inventory, slot - 1, entity);


				if (item.isValid()) {
					return new Object[]{
						new LuaReference<ItemStack>(IWasBoredAPI.instance().coreRegistry().getObjectMethods(item, ItemReference.class), item, "Item is no longer there")
					};
				}

				return null;
			}

			case 2: // getSize
				return new Object[]{size};
			case 3: { // moveToSlot
				// (fromSlot, targetSlot, [Count])
				break;
			}
			case 4: { // moveToInventory
				// (fromSlot, targetSlot, targetInventory, [Count])
				break;
			}
		}

		return null;
	}
}
