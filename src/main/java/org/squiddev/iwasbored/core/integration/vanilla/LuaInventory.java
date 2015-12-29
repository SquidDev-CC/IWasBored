package org.squiddev.iwasbored.core.integration.vanilla;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.ILuaObject;
import dan200.computercraft.api.lua.LuaException;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import org.squiddev.iwasbored.core.api.IWasBoredCoreAPI;
import org.squiddev.iwasbored.core.api.reference.IInventorySlot;
import org.squiddev.iwasbored.core.api.reference.IReference;
import org.squiddev.iwasbored.core.impl.provider.LuaReference;
import org.squiddev.iwasbored.core.impl.reference.EntityReference;
import org.squiddev.iwasbored.core.impl.reference.SlotReference;
import org.squiddev.iwasbored.core.impl.reference.TileEntityReference;
import org.squiddev.iwasbored.lib.inventory.InventoryUtils;

import java.util.HashMap;

public class LuaInventory implements ILuaObject {
	private final IReference<IInventory> inventory;
	private final int size;
	private final int offset;

	public LuaInventory(IInventory inventory, EntityLivingBase entity) {
		this(new EntityReference<IInventory>(inventory, entity));
	}

	public LuaInventory(IInventory inventory, TileEntity te) {
		this(new TileEntityReference<IInventory>(inventory, te));
	}

	public LuaInventory(IReference<IInventory> inventory) {
		this(inventory, 0, inventory.get().getSizeInventory());
	}

	public LuaInventory(IReference<IInventory> inventory, int offset, int size) {
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
				IInventory inventory = this.inventory.get();
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

				SlotReference item = new SlotReference(inventory, slot - 1);
				return new Object[]{
					new LuaReference<IInventorySlot>(IWasBoredCoreAPI.instance().getObjectMethods(item, IInventorySlot.class), item, "Item is no longer there")
				};
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
