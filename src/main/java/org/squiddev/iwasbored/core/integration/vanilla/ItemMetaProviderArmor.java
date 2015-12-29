package org.squiddev.iwasbored.core.integration.vanilla;

import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import org.squiddev.iwasbored.core.api.provider.AbstractProvider;

import java.util.HashMap;
import java.util.Map;

/**
 * Meta provider for amour properties. Material is handled in {@link ItemMetaProviderMaterial}.
 */
public class ItemMetaProviderArmor extends AbstractProvider<ItemStack, Map<String, Object>> {
	private static String convertArmorType(int armorType) {
		switch (armorType) {
			case 0:
				return "helmet";
			case 1:
				return "plate";
			case 2:
				return "legs";
			case 3:
				return "boots";
			default:
				return "unknown";
		}
	}

	@Override
	public Map<String, Object> get(ItemStack stack) {
		Item item = stack.getItem();
		if (item instanceof ItemArmor) {
			ItemArmor armor = (ItemArmor) item;
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("armorType", convertArmorType(armor.armorType));

			int color = armor.getColor(stack);
			if (color >= 0) data.put("color", color);

			return data;
		} else {
			return null;
		}
	}
}
