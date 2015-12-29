package org.squiddev.iwasbored.core.integration.vanilla;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.squiddev.iwasbored.core.api.provider.AbstractProvider;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Lists enchantments on items
 */
public class ItemMetaProviderEnchantment extends AbstractProvider<ItemStack, Map<String, Object>> {
	@Override
	public Map<String, Object> get(ItemStack stack) {
		NBTTagList enchants = null;
		if (stack.isItemEnchanted()) {
			enchants = stack.getEnchantmentTagList();
		} else if (stack.getItem() instanceof ItemEnchantedBook) {
			enchants = ((ItemEnchantedBook) stack.getItem()).getEnchantments(stack);
		}

		if (enchants != null && enchants.tagCount() > 0) {
			Map<String, Object> result = new HashMap<String, Object>();
			Map<Integer, Object> items = new HashMap<Integer, Object>();
			result.put("enchantments", items);

			for (int i = 0; i < enchants.tagCount(); i++) {
				NBTTagCompound tag = enchants.getCompoundTagAt(i);
				Enchantment enchantement = Enchantment.getEnchantmentById(tag.getShort("id"));
				if (enchantement != null) {
					HashMap<String, Object> enchant = new HashMap<String, Object>();
					enchant.put("name", enchantement.getName());

					short level = tag.getShort("lvl");
					enchant.put("level", level);
					enchant.put("fullName", enchantement.getTranslatedName(level));

					items.put(i + 1, enchant);
				}
			}

			return result;
		} else {
			return Collections.emptyMap();
		}
	}
}
