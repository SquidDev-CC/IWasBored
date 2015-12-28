package org.squiddev.iwasbored.items;

import baubles.api.IBauble;
import baubles.common.container.InventoryBaubles;
import baubles.common.lib.PlayerHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.common.Optional;

import java.util.UUID;

public class ItemUtils {
	public static NBTTagCompound getTag(ItemStack stack) {
		NBTTagCompound tag = stack.getTagCompound();
		if (tag == null) stack.setTagCompound(tag = new NBTTagCompound());
		return tag;
	}

	private static final String TAG_HASHCODE = "playerHashcode";
	private static final String TAG_BAUBLE_UUID_MOST = "baubleUUIDMost";
	private static final String TAG_BAUBLE_UUID_LEAST = "baubleUUIDLeast";

	@Optional.Method(modid = "Baubles")
	public static ItemStack replaceBauble(IBauble bauble, ItemStack stack, World world, EntityPlayer player) {
		if (player instanceof FakePlayer || !bauble.canEquip(stack, player)) return stack;

		InventoryBaubles baubles = PlayerHandler.getPlayerBaubles(player);
		for (int i = 0; i < baubles.getSizeInventory(); i++) {
			if (baubles.isItemValidForSlot(i, stack)) {
				ItemStack stackInSlot = baubles.getStackInSlot(i);
				if (stackInSlot == null || ((IBauble) stackInSlot.getItem()).canUnequip(stackInSlot, player)) {
					if (!world.isRemote) {
						baubles.setInventorySlotContents(i, stack.copy());
						if (!player.capabilities.isCreativeMode) {
							player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
						}
					}

					if (stackInSlot != null) {
						((IBauble) stackInSlot.getItem()).onUnequipped(stackInSlot, player);
						return stackInSlot.copy();
					}
					break;
				}
			}
		}

		return stack;
	}

	public static int getLastPlayerHashcode(ItemStack stack) {
		return ItemUtils.getTag(stack).getInteger(TAG_HASHCODE);
	}

	public static void setLastPlayerHashcode(ItemStack stack, int hash) {
		ItemUtils.getTag(stack).setInteger(TAG_HASHCODE, hash);
	}

	public static UUID getBaubleUUID(ItemStack stack) {
		NBTTagCompound tag = ItemUtils.getTag(stack);
		long most = tag.getLong(TAG_BAUBLE_UUID_MOST);
		if (most == 0) {
			UUID uuid = UUID.randomUUID();
			while (uuid.getMostSignificantBits() == 0) {
				uuid = UUID.randomUUID();
			}
			tag.setLong(TAG_BAUBLE_UUID_MOST, uuid.getMostSignificantBits());
			tag.setLong(TAG_BAUBLE_UUID_LEAST, uuid.getLeastSignificantBits());
			return uuid;
		}

		return new UUID(most, tag.getLong(TAG_BAUBLE_UUID_LEAST));
	}
}
