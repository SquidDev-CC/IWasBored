package org.squiddev.iwasbored.gameplay.items;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.squiddev.iwasbored.gameplay.IWasBoredGameplay;
import org.squiddev.iwasbored.gameplay.client.model.ModelInterface;
import org.squiddev.iwasbored.gameplay.neural.NeuralInterface;
import org.squiddev.iwasbored.gameplay.neural.NeuralManager;
import org.squiddev.iwasbored.gameplay.utils.Helpers;
import org.squiddev.iwasbored.lib.DebugLogger;
import org.squiddev.iwasbored.lib.registry.IClientModule;

@Optional.Interface(iface = "baubles.api.IBauble", modid = "Baubles")
public class ItemNeuralInterface extends ItemArmor implements IBauble, ISpecialArmor, IClientModule {
	private static final ArmorMaterial FAKE_ARMOUR = EnumHelper.addArmorMaterial("FAKE_ARMOUR", "iwasbored_fake", 33, new int[]{0, 0, 0, 0}, 0);
	private static final ArmorProperties FAKE_PROPERTIES = new ArmorProperties(0, 0, 0);
	private static final String NAME = "neuralInterface";

	public static final String RESOURCE_PATH = IWasBoredGameplay.PREFIX_TEXTURES + "model/neuralInterface.png";
	public static final ResourceLocation RESOURCE_LOCATION = new ResourceLocation(IWasBoredGameplay.RESOURCE_DOMAIN, "textures/model/neuralInterface.png");

	public ItemNeuralInterface() {
		super(FAKE_ARMOUR, 0, 0);
		setUnlocalizedName(IWasBoredGameplay.RESOURCE_DOMAIN + "." + NAME);
		setCreativeTab(IWasBoredGameplay.getCreativeTab());
	}

	//region IBauble
	@Override
	@Optional.Method(modid = "Baubles")
	public BaubleType getBaubleType(ItemStack itemstack) {
		return BaubleType.AMULET;
	}

	@Override
	@Optional.Method(modid = "Baubles")
	public void onWornTick(ItemStack stack, EntityLivingBase player) {
		if (ItemUtils.getLastPlayerHashcode(stack) != player.hashCode()) {
			onEquippedOrLoadedIntoWorld(stack, player);
			ItemUtils.setLastPlayerHashcode(stack, player.hashCode());
		}

		if (player instanceof EntityPlayer) {
			onUpdate(stack, (EntityPlayer) player, true);
		}
	}

	@Optional.Method(modid = "Baubles")
	public void onEquippedOrLoadedIntoWorld(ItemStack stack, EntityLivingBase player) {
		if (player instanceof EntityPlayer) {
			onUpdate(stack, (EntityPlayer) player, true);
		}
	}

	@Override
	@Optional.Method(modid = "Baubles")
	public void onEquipped(ItemStack stack, EntityLivingBase player) {
		onEquippedOrLoadedIntoWorld(stack, player);
		ItemUtils.setLastPlayerHashcode(stack, player.hashCode());
	}

	@Override
	@Optional.Method(modid = "Baubles")
	public void onUnequipped(ItemStack stack, EntityLivingBase entity) {
	}

	@Override
	@Optional.Method(modid = "Baubles")
	public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}

	@Override
	@Optional.Method(modid = "Baubles")
	public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}
	//endregion

	//region ISpecialArmor
	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
		return FAKE_PROPERTIES;
	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		return 0;
	}

	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {

	}
	//endregion

	@Override
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
		DebugLogger.debug("Slot is %s", armorSlot);
		return ModelInterface.get();
	}


	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		return RESOURCE_PATH;
	}

	@Override
	@Optional.Method(modid = "Baubles")
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		return ItemUtils.replaceBauble(this, stack, world, player);
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		onUpdate(itemStack, player, true);
	}

	public void onUpdate(ItemStack stack, EntityPlayer player, boolean active) {
		if (player.worldObj.isRemote) {
			NeuralManager.getClient(ItemUtils.getTag(stack));
		} else {
			NBTTagCompound tag = ItemUtils.getTag(stack);
			NeuralInterface neuralInterface;
			if (active) {
				neuralInterface = NeuralManager.get(tag, player);
				neuralInterface.turnOn();
				neuralInterface.update();
			} else {
				neuralInterface = NeuralManager.tryGet(tag, player);
				if (neuralInterface == null) return;
			}

			if (neuralInterface.isDirty()) {
				neuralInterface.toNBT(tag);
				String label = tag.getString("label");
				if (label == null || label.isEmpty()) {
					stack.clearCustomName();
				} else {
					stack.setStackDisplayName(label);
				}

				player.inventory.markDirty();
			}
		}
	}

	//region IModule
	@Override
	public boolean canLoad() {
		return true;
	}

	@Override
	public void preInit() {
		GameRegistry.registerItem(this, NAME);
	}

	@Override
	public void init() {
	}

	@Override
	public void postInit() {
	}

	@Override
	public void clientInit() {
		Helpers.setupModel(this, 0, NAME);
	}
	//endregion
}
