package org.squiddev.iwasbored.core.integration.vanilla;

import com.google.common.collect.Maps;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.squiddev.iwasbored.core.api.provider.AbstractProvider;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A meta provider for consumables: food and potions.
 *
 * Provides food and saturation for foodstuffs, and potion details for potions.
 */
public class ItemMetaProviderConsumable extends AbstractProvider<ItemStack, Map<String, Object>> {
	@Override
	public Map<String, Object> get(ItemStack stack) {
		Item item = stack.getItem();
		if (item instanceof ItemFood) {
			Map<String, Object> data = new HashMap<String, Object>();
			ItemFood food = (ItemFood) item;
			data.put("heal", food.getHealAmount(stack));
			data.put("saturation", food.getSaturationModifier(stack));

			return data;
		} else if (item instanceof ItemPotion) {
			Map<String, Object> data = new HashMap<String, Object>();
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

			return data;
		} else {
			return Collections.emptyMap();
		}
	}
}
