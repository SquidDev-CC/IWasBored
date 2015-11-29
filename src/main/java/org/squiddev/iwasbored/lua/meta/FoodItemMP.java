package org.squiddev.iwasbored.lua.meta;

import com.google.common.collect.Maps;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.ILuaObject;
import dan200.computercraft.api.lua.LuaException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.squiddev.iwasbored.api.IWasBoredAPI;
import org.squiddev.iwasbored.api.ItemReference;
import org.squiddev.iwasbored.api.meta.IItemMetaProvider;
import org.squiddev.iwasbored.registry.Module;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodItemMP extends Module implements IItemMetaProvider {
	@Override
	public void getMetadata(ItemStack stack, Map<String, Object> data) {
		Item item = stack.getItem();
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
	}

	@Override
	public ILuaObject getObject(ItemReference reference) {
		ItemStack stack = reference.get();
		if (stack != null && reference.getPlayer() != null) {
			EnumAction action = stack.getItemUseAction();
			if (action == EnumAction.eat || action == EnumAction.drink) {
				return new ConsumableMeta(reference);
			}
		}

		return null;
	}

	@Override
	public int getPriority() {
		return 0;
	}

	public static class ConsumableMeta implements ILuaObject {
		private final ItemReference reference;

		public ConsumableMeta(ItemReference reference) {
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
					EntityPlayer player = reference.getPlayer();
					reference.replace(reference.get().onFoodEaten(player.getEntityWorld(), player));
					return null;
			}

			return null;
		}
	}

	@Override
	public void preInit() {
		IWasBoredAPI.instance().metaRegistry().registerItemProvider(this);
	}
}
