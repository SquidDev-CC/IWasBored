package org.squiddev.iwasbored.lua;

import com.google.common.collect.Maps;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.ILuaObject;
import dan200.computercraft.api.lua.LuaException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.squiddev.iwasbored.api.IWasBoredAPI;
import org.squiddev.iwasbored.api.provider.AbstractProvider;
import org.squiddev.iwasbored.api.provider.IProviderRegistry;
import org.squiddev.iwasbored.api.reference.IInventorySlot;
import org.squiddev.iwasbored.api.reference.IReference;
import org.squiddev.iwasbored.registry.Module;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Providers extends Module {
	@Override
	public void preInit() {
		IProviderRegistry registry = IWasBoredAPI.instance().coreRegistry();

		registry.registerMethodProvider(new AbstractProvider<IReference<IInventorySlot>, ILuaObject>() {
			@Override
			public ILuaObject get(IReference<IInventorySlot> reference) {
				return new BasicObject(reference);
			}
		}, IInventorySlot.class);

		// Potion/food providers
		registry.registerItemMetadata(new ConsumableMeta());
		registry.registerMethodProvider(new AbstractProvider<IReference<IInventorySlot>, ILuaObject>() {
			@Override
			public ILuaObject get(IReference<IInventorySlot> reference) {
				ItemStack stack = reference.get().stack();
				if (stack != null && reference.owner() != null) {
					EnumAction action = stack.getItemUseAction();
					if (action == EnumAction.eat || action == EnumAction.drink) {
						return new ConsumableObject(reference);
					}
				}

				return null;
			}
		}, IInventorySlot.class);

		registry.registerMethodProvider(new AbstractProvider<IReference<IInventory>, ILuaObject>() {
			@Override
			public ILuaObject get(IReference<IInventory> inventory) {
				return new LuaInventory(inventory);
			}
		}, IInventory.class);
	}

	private static class ConsumableObject implements ILuaObject {
		private final IReference<IInventorySlot> reference;

		public ConsumableObject(IReference<IInventorySlot> reference) {
			this.reference = reference;
		}

		@Override
		public String[] getMethodNames() {
			return new String[]{
				"consume",
			};
		}

		@Override
		public Object[] callMethod(ILuaContext context, int method, Object[] objects) throws LuaException, InterruptedException {
			switch (method) {
				case 0:
					Object entity = reference.owner();
					if (entity instanceof EntityPlayer) {
						EntityPlayer player = (EntityPlayer) entity;
						reference.get().replace(reference.get().stack().onFoodEaten(player.worldObj, player));
						return null;
					} else {
						throw new LuaException("Not a player");
					}
			}

			return null;
		}
	}

	private static class ConsumableMeta extends AbstractProvider<ItemStack, Map<String, Object>> {
		@Override
		public Map<String, Object> get(ItemStack stack) {
			Item item = stack.getItem();
			Map<String, Object> data = new HashMap<String, Object>();

			if (item instanceof ItemFood) {
				ItemFood food = (ItemFood) item;
				data.put("heal", food.func_150905_g(stack));
				data.put("saturation", food.func_150906_h(stack));
			} else if (item instanceof ItemPotion) {
				ItemPotion itemPotion = (ItemPotion) item;

				data.put("splash", ItemPotion.isSplash(stack.getItemDamage()));

				Map<Integer, Map<String, Object>> effectsInfo = new HashMap<Integer, Map<String, Object>>();

				@SuppressWarnings("unchecked")
				List<PotionEffect> effects = itemPotion.getEffects(stack);

				int i = 0;
				for (PotionEffect effect : effects) {
					Map<String, Object> entry = Maps.newHashMap();

					entry.put("duration", effect.getDuration() / 20); // ticks!
					entry.put("amplifier", effect.getAmplifier());
					int potionId = effect.getPotionID();
					if (potionId >= 0 && potionId < Potion.potionTypes.length) {

						Potion potion = Potion.potionTypes[potionId];
						data.put("name", potion.getName());
						data.put("instant", potion.isInstant());
						data.put("color", potion.getLiquidColor());
					}

					effectsInfo.put(i, entry);
					i++;
				}

				data.put("effects", effectsInfo);
			}

			return data;
		}
	}

	private static final class BasicObject implements ILuaObject {
		private final IReference<IInventorySlot> item;

		private BasicObject(IReference<IInventorySlot> item) {
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
					return new Object[]{IWasBoredAPI.instance().coreRegistry().getItemMetadata(item.get().stack())};
			}
			return null;
		}
	}

}
