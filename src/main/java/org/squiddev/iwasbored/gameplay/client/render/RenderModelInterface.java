package org.squiddev.iwasbored.gameplay.client.render;

import baubles.common.container.InventoryBaubles;
import baubles.common.lib.PlayerHandler;
import org.squiddev.iwasbored.gameplay.IWasBoredGameplay;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import org.squiddev.iwasbored.gameplay.client.model.ModelInterface;
import org.squiddev.iwasbored.gameplay.items.ItemNeuralInterface;
import org.squiddev.iwasbored.lib.registry.IClientModule;
import org.squiddev.iwasbored.lib.registry.Module;

public class RenderModelInterface extends Module implements IClientModule {
	@SubscribeEvent
	public void onPlayerRender(RenderPlayerEvent.Post event) {
		if (event.entityLiving.getActivePotionEffect(Potion.invisibility) != null) return;

		EntityPlayer player = event.entityPlayer;
		InventoryBaubles inv = PlayerHandler.getPlayerBaubles(player);

		ItemStack stack = inv.getStackInSlot(0);
		if (stack != null && stack.getItem() == IWasBoredGameplay.Items.itemNeuralInterface) {
			float yawHead = player.prevRotationYawHead + (player.rotationYawHead - player.prevRotationYawHead) * event.partialRenderTick;
			float yawOffset = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * event.partialRenderTick;
			float pitch = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * event.partialRenderTick;

			GL11.glPushMatrix();
			GL11.glRotatef(yawOffset, 0, -1, 0);
			GL11.glRotatef(yawHead - 270, 0, 1, 0);
			GL11.glRotatef(pitch, 0, 0, 1);

			GL11.glRotatef(-90f, 0f, 1f, 0f);

			GL11.glTranslatef(0, 0.125f, player.getCurrentArmor(3) == null ? 0 : -0.05f);
			// GL11.glTranslatef(0, (player != Minecraft.getMinecraft().thePlayer ? 1.62f : 0F) - player.getDefaultEyeHeight() + (player.isSneaking() ? 0.0625f : 0), 0);

			Minecraft.getMinecraft().renderEngine.bindTexture(ItemNeuralInterface.RESOURCE_LOCATION);
			ModelInterface.get().render(player, 0, 0, 0, 0, 0, 0.0625f);

			GL11.glPopMatrix();
		}
	}

	@Override
	public void clientInit() {
		MinecraftForge.EVENT_BUS.register(this);
	}
}
