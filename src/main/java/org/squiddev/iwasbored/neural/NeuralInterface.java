package org.squiddev.iwasbored.neural;

import com.google.common.base.Preconditions;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.ILuaObject;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.core.apis.ILuaAPI;
import dan200.computercraft.shared.computer.core.ServerComputer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.squiddev.iwasbored.DebugLogger;
import org.squiddev.iwasbored.api.IWasBoredAPI;
import org.squiddev.iwasbored.api.neural.INeuralInterface;
import org.squiddev.iwasbored.api.neural.INeuralRegistry;
import org.squiddev.iwasbored.api.neural.INeuralUpgrade;
import org.squiddev.iwasbored.impl.provider.LuaObjectCollection;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class NeuralInterface extends ServerComputerManager implements INeuralInterface, ILuaAPI {
	public static final String SESSION_ID = "session_id";
	public static final String INSTANCE_ID = "instance_id";

	private final HashMap<EnumFacing, INeuralUpgrade> upgrades = new HashMap<EnumFacing, INeuralUpgrade>();
	private final HashMap<EnumFacing, IPeripheral> upgradeObjects = new HashMap<EnumFacing, IPeripheral>();
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

		apiMethods = new LuaObjectCollection(IWasBoredAPI.instance().neuralRegistry().getLua(this));
	}

	//region INeuralInterface
	@Override
	public EntityPlayer getPlayer() {
		return player;
	}

	@Override
	public Map<EnumFacing, INeuralUpgrade> getUpgrades() {
		return Collections.unmodifiableMap(upgrades);
	}

	@Override
	public INeuralUpgrade getUpgrade(EnumFacing direction) {
		Preconditions.checkNotNull(direction, "direction cannot be null");
		if (direction == EnumFacing.UP) throw new IllegalArgumentException("direction cannot be EnumFacing.UP");
		return upgrades.get(direction);
	}

	@Override
	public boolean addUpgrade(EnumFacing direction, INeuralUpgrade upgrade) {
		Preconditions.checkNotNull(direction, "direction cannot be null");
		Preconditions.checkNotNull(upgrade, "upgrade cannot be null");
		if (direction == EnumFacing.UP) throw new IllegalArgumentException("direction cannot be EnumFacing.UP");

		synchronized (upgrades) {
			if (upgrades.containsKey(direction)) return false;

			upgrades.put(direction, upgrade);
			upgradeObjects.put(direction, upgrade.getLuaObject());
			dirty = true;

			if (getComputer().isOn()) upgrade.attach(this);

		}
		return true;
	}

	@Override
	public boolean removeUpgrade(EnumFacing direction) {
		Preconditions.checkNotNull(direction, "direction cannot be null");
		if (direction == EnumFacing.UP) throw new IllegalArgumentException("direction cannot be EnumFacing.UP");

		synchronized (upgrades) {
			INeuralUpgrade upgrade = upgrades.remove(direction);
			if (upgrade != null) {
				upgradeObjects.remove(direction);
				dirty = true;

				if (getComputer().isOn()) upgrade.detach();

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

		// TODO: Wrap this as peripheral
		for (Map.Entry<EnumFacing, IPeripheral> upgrade : upgradeObjects.entrySet()) {
			computer.setPeripheral(upgrade.getKey().ordinal(), upgrade.getValue());
		}

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
		for (Map.Entry<EnumFacing, INeuralUpgrade> pair : upgrades.entrySet()) {
			NBTTagCompound data = new NBTTagCompound();
			INeuralUpgrade upgrade = pair.getValue();
			data.setString("key", upgrade.getName().toString());
			data.setTag("data", upgrade.toNBT());

			upgradeLookup.setTag(pair.getKey().getName(), data);
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
					String directionName = (String) key;
					EnumFacing direction = EnumFacing.byName(directionName);
					if (direction == null) {
						DebugLogger.error("Unknown direction %s, ignoring", directionName);
						continue;
					} else if (direction == EnumFacing.UP) {
						DebugLogger.error("Cannot load UP, ignoring", directionName);
						continue;
					}

					NBTTagCompound tag = upgrades.getCompoundTag(directionName);
					if (tag == null) continue;

					ResourceLocation name = new ResourceLocation(tag.getString("key"));

					INeuralUpgrade upgrade = registry.create(name, tag.getCompoundTag("data"));
					if (upgrade == null) {
						DebugLogger.error("Cannot load Neural Upgrade with name %s (tag = %s)", name, tag);
					} else {
						addUpgrade(direction, upgrade);
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

}
