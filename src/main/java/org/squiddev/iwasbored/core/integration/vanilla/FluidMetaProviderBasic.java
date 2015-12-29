package org.squiddev.iwasbored.core.integration.vanilla;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.squiddev.iwasbored.core.api.provider.AbstractProvider;

import java.util.HashMap;
import java.util.Map;

/**
 * Basic properties for fluid stacks
 */
public class FluidMetaProviderBasic extends AbstractProvider<FluidStack, Map<String, Object>> {
	@Override
	public Map<String, Object> get(FluidStack fluidStack) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("amount", fluidStack.amount);

		Fluid fluid = fluidStack.getFluid();
		if (fluid != null) {
			data.put("name", fluid.getName());
			data.put("rawName", fluid.getUnlocalizedName(fluidStack));
			data.put("displayName", fluid.getLocalizedName(fluidStack));
		}

		return data;
	}
}
