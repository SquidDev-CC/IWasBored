package org.squiddev.iwasbored.integration;

import baubles.common.container.InventoryBaubles;
import baubles.common.lib.PlayerHandler;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.ILuaObject;
import dan200.computercraft.api.lua.LuaException;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Optional;
import org.squiddev.iwasbored.api.IWasBoredAPI;
import org.squiddev.iwasbored.api.neural.INeuralInterface;
import org.squiddev.iwasbored.api.neural.INeuralReference;
import org.squiddev.iwasbored.api.neural.INeuralRegistry;
import org.squiddev.iwasbored.api.provider.IProvider;
import org.squiddev.iwasbored.api.reference.IReference;
import org.squiddev.iwasbored.lua.LuaInventory;
import org.squiddev.iwasbored.lua.reference.EntityReference;
import org.squiddev.iwasbored.lua.reference.SlotReference;
import org.squiddev.iwasbored.neural.NeuralInterfaceReference;
import org.squiddev.iwasbored.registry.Registry;

public class BaublesIntegration extends ModIntegration {
	public BaublesIntegration() {
		super("Baubles");
	}

	@Override
	@Optional.Method(modid = "Baubles")
	public void postInit() {
		INeuralRegistry registry = IWasBoredAPI.instance().neuralRegistry();

		registry.registerNeuralProvider(new IProvider<EntityLivingBase, INeuralReference>() {
			@Override
			public int getPriority() {
				return 0;
			}

			@Override
			public INeuralReference get(EntityLivingBase entityLivingBase) {
				if (entityLivingBase instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer) entityLivingBase;

					InventoryBaubles baubles = PlayerHandler.getPlayerBaubles(player);

					ItemStack stack = baubles.getStackInSlot(0);
					if (stack != null && stack.getItem() == Registry.itemNeuralInterface) {
						IReference<IInventory> inventory = new EntityReference<IInventory>(baubles, player);
						SlotReference slot = new SlotReference(inventory, 0);
						return new NeuralInterfaceReference(slot);
					}
				}

				return null;
			}
		});

		registry.registerLuaProvider(new IProvider<INeuralInterface, ILuaObject>() {
			@Override
			public int getPriority() {
				return 0;
			}

			@Override
			public ILuaObject get(final INeuralInterface iFace) {
				return new ILuaObject() {
					@Override
					public String[] getMethodNames() {
						return new String[]{"getBaublesInventory"};
					}

					@Override
					public Object[] callMethod(ILuaContext context, int method, Object[] args) throws LuaException, InterruptedException {
						EntityPlayer player = iFace.getPlayer();
						return new Object[]{new LuaInventory(PlayerHandler.getPlayerBaubles(player), player)};
					}
				};
			}
		});
	}
}
