package org.squiddev.iwasbored.neural;

import dan200.computercraft.ComputerCraft;
import dan200.computercraft.shared.computer.core.ClientComputer;
import dan200.computercraft.shared.computer.core.ServerComputer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Manager for neural interface
 */
public class NeuralManager {
	private static final HashMap<Integer, NeuralInterface> interfaces = new HashMap<Integer, NeuralInterface>();
	private static int sessionId = 0;
	private static int instanceId = 0;

	public static void setup() {
		sessionId++;
		instanceId = 0;
		interfaces.clear();
	}

	public static void tearDown() {
		for (NeuralInterface neural : interfaces.values()) {
			neural.unload();
		}

		instanceId = 0;
		interfaces.clear();
	}

	public static NeuralInterface get(NBTTagCompound tag, EntityPlayer player) {
		if (player.worldObj.isRemote) throw new RuntimeException("Cannot get NeuralInterface on client thread");

		NeuralInterface neuralInterface;

		if (tag.getInteger(NeuralInterface.SESSION_ID) != sessionId) {
			// Running on an old session. Update it
			neuralInterface = new NeuralInterface(player, instanceId++, sessionId, tag);
			synchronized (interfaces) {
				interfaces.put(neuralInterface.instanceId, neuralInterface);
			}
		} else {
			synchronized (interfaces) {
				neuralInterface = interfaces.get(tag.getInteger(NeuralInterface.INSTANCE_ID));

				if (neuralInterface == null) {
					// Doesn't exist. Create it
					neuralInterface = new NeuralInterface(player, instanceId++, sessionId, tag);
					interfaces.put(neuralInterface.instanceId, neuralInterface);
				} else if (neuralInterface.player != player) {
					// Wrong player. Destroy and create
					neuralInterface.unload();

					neuralInterface = new NeuralInterface(player, instanceId++, sessionId, tag);
					interfaces.put(neuralInterface.instanceId, neuralInterface);
				}
			}
		}

		return neuralInterface;
	}

	public static NeuralInterface tryGet(NBTTagCompound tag, EntityPlayer player) {
		if (player.worldObj.isRemote) throw new RuntimeException("Cannot get NeuralInterface on client thread");
		if (tag.getInteger(NeuralInterface.SESSION_ID) == sessionId) {
			synchronized (interfaces) {
				return interfaces.get(tag.getInteger(NeuralInterface.INSTANCE_ID));
			}
		}

		return null;
	}

	public static ClientComputer getClient(NBTTagCompound tag) {
		int instanceId = tag.getInteger(NeuralInterface.INSTANCE_ID);
		if (instanceId < 0) return null;

		if (!ComputerCraft.clientComputerRegistry.contains(instanceId)) {
			ComputerCraft.clientComputerRegistry.add(instanceId, new ClientComputer(instanceId));
		}

		return ComputerCraft.clientComputerRegistry.get(instanceId);
	}

	public static void update() {
		synchronized (interfaces) {
			Iterator<Map.Entry<Integer, NeuralInterface>> iterator = interfaces.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<Integer, NeuralInterface> neural = iterator.next();
				ServerComputer computer = neural.getValue().getComputerLazy();

				if (computer == null) {
					// We can do this as the computer should be shutdown/cleaned up anyway
					iterator.remove();
				}
			}
		}
	}
}
