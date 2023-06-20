package net.smileycorp.mineplunder.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.util.thread.SidedThreadGroups;
import net.smileycorp.mineplunder.api.Faction;
import net.smileycorp.mineplunder.api.ReputationHandler;
import net.smileycorp.mineplunder.api.capability.Reputation;
import net.smileycorp.mineplunder.network.SyncReputationMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ReputationImpl implements Reputation {

	private Map<Faction, Integer> FACTIONS = new HashMap<Faction, Integer>();

	@Override
	public int getReputation(Faction faction) {
		if (!FACTIONS.containsKey(faction)) FACTIONS.put(faction, faction.getDefaultRep());
		return FACTIONS.get(faction);
	}

	@Override
	public void setReputation(Player player, Faction faction, int reputation) {
		FACTIONS.put(faction, reputation);
		if (Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER && player instanceof ServerPlayer) {
			((ServerPlayer)player).connection.send(new SyncReputationMessage(faction, reputation));
		}
	}

	@Override
	public void changeReputation(Player player, Faction faction, int reputation) {
		int base = FACTIONS.containsKey(faction) ? FACTIONS.get(faction) : faction.getDefaultRep();
		FACTIONS.put(faction, base + reputation);
		if (Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER && player instanceof ServerPlayer) {
			((ServerPlayer)player).connection.send(new SyncReputationMessage(faction, reputation));
		}
	}

	@Override
	public CompoundTag writeNBT(CompoundTag nbt) {
		CompoundTag reputation = new CompoundTag();
		for (Entry<Faction, Integer> entry : FACTIONS.entrySet()) {
			reputation.putInt(entry.getKey().getName().toString(), entry.getValue());
		}
		nbt.put("reputation", reputation);
		return nbt;
	}

	@Override
	public void readNBT(CompoundTag nbt) {
		if (nbt.contains("reputation")) {
			CompoundTag reputation = nbt.getCompound("reputation");
			for (String key : reputation.getAllKeys()) {
				Faction faction = ReputationHandler.getFaction(new ResourceLocation(key));
				if (faction != null) FACTIONS.put(faction, nbt.getInt(key));
			}
		}
	}

}