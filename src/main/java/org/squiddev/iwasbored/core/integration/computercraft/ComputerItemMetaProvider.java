package org.squiddev.iwasbored.core.integration.computercraft;

import com.google.common.base.Strings;
import dan200.computercraft.shared.computer.items.IComputerItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.squiddev.iwasbored.core.api.provider.NamespacedMetaProvider;

import java.util.HashMap;
import java.util.Map;

/**
 * Provider for computer items;
 */
public class ComputerItemMetaProvider extends NamespacedMetaProvider<ItemStack> {
	@Override
	public String getNamespace() {
		return "computer";
	}

	@Override
	public Object getMeta(ItemStack stack) {
		Item item = stack.getItem();
		if (item instanceof IComputerItem) {
			Map<String, Object> data = new HashMap<String, Object>();
			IComputerItem cItem = (IComputerItem) item;

			int id = cItem.getComputerID(stack);
			if (id > 0) data.put("id", id);

			String label = cItem.getLabel(stack);
			if (!Strings.isNullOrEmpty(label)) data.put("label", label);
			data.put("family", cItem.getFamily(stack).toString());

			return data;
		} else {
			return null;
		}
	}
}
