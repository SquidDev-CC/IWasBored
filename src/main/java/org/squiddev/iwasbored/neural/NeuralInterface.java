package org.squiddev.iwasbored.neural;

import baubles.common.lib.PlayerHandler;
import com.google.common.collect.Iterables;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.ILuaObject;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.core.apis.ILuaAPI;
import dan200.computercraft.shared.computer.core.ServerComputer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import org.squiddev.iwasbored.DebugLogger;
import org.squiddev.iwasbored.api.IWasBoredAPI;
import org.squiddev.iwasbored.api.neural.INeuralInterface;
import org.squiddev.iwasbored.api.neural.INeuralRegistry;
import org.squiddev.iwasbored.api.neural.INeuralUpgrade;
import org.squiddev.iwasbored.computer.ServerComputerManager;
import org.squiddev.iwasbored.inventory.InventoryProxy;
import org.squiddev.iwasbored.lua.LuaObjectCollection;
import org.squiddev.iwasbored.lua.LuaReference;
import org.squiddev.iwasbored.lua.reference.EntityReference;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class NeuralInterface extends ServerComputerManager implements INeuralInterface, ILuaAPI {
	public static final String SESSION_ID = "session_id";
	public static final String INSTANCE_ID = "instance_id";

	private final HashMap<String, INeuralUpgrade> upgrades = new HashMap<String, INeuralUpgrade>();
	private final HashMap<String, ILuaObject> upgradeObjects = new HashMap<String, ILuaObject>();
	private final ILuaObject apiMethods;

	public final EntityPlayer player;
	public final int instanceId;
	public final int sessionId;

	public NeuralInterface(EntityPlayer player, int instanceId, int sessionId, NBTTagCompound tagCompound) {
		this.player = player;
		this.instanceId = instanceId;
		this.sessionId = sessionId;

		fromNBT(tagCompound);
		turnOn();

		apiMethods = new LuaObjectCollection(Iterables.concat(
			Collections.singleton(new CoreObject()),
			IWasBoredAPI.instance().neuralRegistry().getLua(this)
		));
	}

	//region INeuralInterface
	@Override
	public EntityPlayer getPlayer() {
		return player;
	}

	@Override
	public Map<String, INeuralUpgrade> getUpgrades() {
		return Collections.unmodifiableMap(upgrades);
	}

	@Override
	public boolean addUpgrade(INeuralUpgrade upgrade) {
		String name = upgrade.getName();
		synchronized (upgrades) {
			if (upgrades.containsKey(name)) return false;

			upgrades.put(name, upgrade);
			upgradeObjects.put(name, upgrade.getLuaObject());
			dirty = true;

			if (getComputer().isOn()) upgrade.attach(this);

		}
		return true;
	}

	@Override
	public boolean removeUpgrade(INeuralUpgrade upgrade) {
		String name = upgrade.getName();
		synchronized (upgrades) {
			if (upgrades.get(name) == upgrade) {
				upgrades.remove(name);
				upgradeObjects.remove(name);
				dirty = true;

				if (getComputer().isOn()) upgrade.attach(this);

				return true;
			}
		}

		return false;
	}

	@Override
	public void markDirty() {
		dirty = true;
	}
	//endregion

	//region ServerComputerManager
	@Override
	protected ServerComputer createComputer(int instanceId, int computerId, String label) {
		ServerComputer computer = super.createComputer(instanceId, computerId, label);
		computer.addAPI(this);

		return computer;
	}

	@Override
	protected World getWorld() {
		return player.worldObj;
	}

	@Override
	public void toNBT(NBTTagCompound tag) {
		super.toNBT(tag);
		tag.setInteger(INSTANCE_ID, instanceId);
		tag.setInteger(SESSION_ID, sessionId);

		NBTTagCompound upgradeLookup = new NBTTagCompound();
		for (Map.Entry<String, INeuralUpgrade> upgrade : upgrades.entrySet()) {
			upgradeLookup.setTag(upgrade.getKey(), upgrade.getValue().toNBT());
		}
		tag.setTag("upgrades", upgradeLookup);
	}

	@Override
	public void fromNBT(NBTTagCompound compound) {
		super.fromNBT(compound);

		// Don't read session or instance id.

		NBTTagCompound upgrades = compound.getCompoundTag("upgrades");
		if (upgrades != null) {
			INeuralRegistry registry = IWasBoredAPI.instance().neuralRegistry();
			for (Object key : upgrades.getKeySet()) {
				if (key instanceof String) {
					String name = (String) key;
					NBTTagCompound tag = upgrades.getCompoundTag(name);
					if (tag == null) continue;

					INeuralUpgrade upgrade = registry.create(name, tag);
					if (upgrade == null) {
						DebugLogger.error("Cannot load Neural Upgrade with name %s (tag = %s)", name, tag);
					} else {
						addUpgrade(upgrade);
					}
				}
			}
		}
	}

	//endregion

	// region ILuaAPI
	@Override
	public String[] getNames() {
		return new String[]{"interface"};
	}

	@Override
	public void startup() {
		for (INeuralUpgrade upgrade : upgrades.values()) {
			upgrade.attach(this);
		}
	}

	@Override
	public void advance(double dt) {
		for (INeuralUpgrade upgrade : upgrades.values()) {
			upgrade.update();
		}
	}

	@Override
	public void shutdown() {
		for (INeuralUpgrade upgrade : upgrades.values()) {
			upgrade.detach();
		}
	}

	@Override
	public String[] getMethodNames() {
		return apiMethods.getMethodNames();
	}

	@Override
	public Object[] callMethod(ILuaContext context, int method, Object[] args) throws LuaException, InterruptedException {
		return apiMethods.callMethod(context, method, args);
	}
	//endregion

	private class CoreObject implements ILuaObject {
		@Override
		public String[] getMethodNames() {
			return new String[]{
				"getInventory",
				"getArmor",
				"getUpgradeNames",
				"getUpgrade",
			};
		}

		@Override
		public Object[] callMethod(ILuaContext context, int method, Object[] args) throws LuaException, InterruptedException {
			switch (method) {
				case 0:
					// TODO: Implements bounds on these
					return new Object[]{new LuaReference<IInventory>(new EntityReference<IInventory>(new InventoryProxy(player.inventory, 0, 9 * 4), player), IInventory.class)}
						;
				case 1:
					return new Object[]{new LuaReference<IInventory>(new EntityReference<IInventory>(new InventoryProxy(player.inventory, 9 * 4, 4), player), IInventory.class)};
				case 2:
					return new Object[]{new LuaReference<IInventory>(new EntityReference<IInventory>(PlayerHandler.getPlayerBaubles(player), player), IInventory.class)};
				case 3: {
					HashMap<Integer, String> results = new HashMap<Integer, String>();
					int i = 0;
					for (String name : upgradeObjects.keySet()) {
						results.put(i, name);
					}

					return new Object[]{results};
				}
				case 4: {
					if (args.length == 0 || !(args[0] instanceof String)) throw new LuaException("Expected string");
					ILuaObject object = upgradeObjects.get((String) args[0]);

					if (object == null) throw new LuaException("No such upgrade");

					return new Object[]{object};
				}
			}

			return null;
		}
	}
}
