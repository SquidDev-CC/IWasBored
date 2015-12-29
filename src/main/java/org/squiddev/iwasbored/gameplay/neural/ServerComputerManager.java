package org.squiddev.iwasbored.gameplay.neural;

import dan200.computercraft.ComputerCraft;
import dan200.computercraft.shared.computer.core.ComputerFamily;
import dan200.computercraft.shared.computer.core.ServerComputer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public abstract class ServerComputerManager {
	private int instanceId = -1;
	private int computerId = -1;

	private String label = null;
	protected boolean dirty = false;

	public void fromNBT(NBTTagCompound compound) {
		if (compound.hasKey("id")) computerId = compound.getInteger("id");
		label = compound.getString("label");
		dirty = true;
	}

	public void toNBT(NBTTagCompound compound) {
		compound.setInteger("id", computerId);
		if (label == null) {
			compound.removeTag("label");
		} else {
			compound.setString("label", label);
		}

		dirty = false;
	}

	public void unload() {
		if (instanceId >= 0) {
			ComputerCraft.serverComputerRegistry.remove(instanceId);
			instanceId = -1;
			dirty = true;
		}
	}

	public void turnOn() {
		getComputer().turnOn();
	}

	public ServerComputer getComputer() {
		if (instanceId < 0) {
			instanceId = ComputerCraft.serverComputerRegistry.getUnusedInstanceID();
			dirty = true;
		}

		if (computerId < 0) {
			computerId = ComputerCraft.createUniqueNumberedSaveDir(getWorld(), "computer");
			dirty = true;
		}

		if (!ComputerCraft.serverComputerRegistry.contains(instanceId)) {
			ServerComputer computer = createComputer(computerId, instanceId, label);
			computer.turnOn();
			ComputerCraft.serverComputerRegistry.add(instanceId, computer);
		}

		return ComputerCraft.serverComputerRegistry.get(instanceId);
	}

	public ServerComputer getComputerLazy() {
		return ComputerCraft.serverComputerRegistry.get(instanceId);
	}

	public void update() {
		ServerComputer computer = getComputer();

		computer.keepAlive();

		int id = computer.getID();
		if (id != computerId) {
			computerId = id;
			dirty = true;
		}


		String label = computer.getLabel();
		if (!label.equals(this.label)) {
			this.label = label;
			dirty = true;
		}
	}

	protected ServerComputer createComputer(int computerId, int instanceId, String label) {
		return new ServerComputer(getWorld(), computerId, label, instanceId, ComputerFamily.Advanced, 51, 19);
	}

	protected abstract World getWorld();

	public boolean isDirty() {
		return dirty;
	}
}
