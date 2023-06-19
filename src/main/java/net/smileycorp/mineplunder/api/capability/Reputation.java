package net.smileycorp.mineplunder.api.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.smileycorp.mineplunder.api.Faction;

public interface Reputation {

	public int getReputation(Faction faction);

	public void setReputation(Player player, Faction faction, int reputation);

	public void changeReputation(Player player, Faction faction, int reputation);

	public CompoundTag writeNBT(CompoundTag nbt);

	public void readNBT(CompoundTag nbt);

}
