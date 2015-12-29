package org.squiddev.iwasbored.core.integration.vanilla;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;
import org.squiddev.iwasbored.core.api.IWasBoredCoreAPI;
import org.squiddev.iwasbored.core.api.provider.NamespacedMetaProvider;

import java.util.HashMap;
import java.util.Map;

/**
 * Displays fluids contained inside a container
 */
public class ItemMetaProviderFluidContainer extends NamespacedMetaProvider<ItemStack> {
	@Override
	public String getNamespace() {
		return "fluidContainer";
	}

	@Override
	public Object getMeta(ItemStack stack) {
		FluidStack fluidStack = FluidContainerRegistry.getFluidForFilledItem(stack);
		int capacity = 0;

		if (fluidStack == null) {
			Item item = stack.getItem();
			if (item instanceof IFluidContainerItem) {
				IFluidContainerItem container = (IFluidContainerItem) item;
				fluidStack = container.getFluid(stack);
				capacity = container.getCapacity(stack);
			}
		} else {
			capacity = FluidContainerRegistry.getContainerCapacity(fluidStack, stack);
		}

		if (fluidStack != null) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("capacity", capacity);
			data.put("fluid", IWasBoredCoreAPI.instance().getMetadata(fluidStack, FluidStack.class));
			return data;
		} else {
			return null;
		}
	}
}
