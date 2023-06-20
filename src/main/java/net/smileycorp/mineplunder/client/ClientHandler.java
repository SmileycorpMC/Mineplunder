package net.smileycorp.mineplunder.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;
import net.smileycorp.mineplunder.api.capability.MineplunderCapabilities;
import net.smileycorp.mineplunder.api.capability.Reputation;
import net.smileycorp.mineplunder.api.capability.SoulFire;
import net.smileycorp.mineplunder.network.SyncReputationMessage;
import net.smileycorp.mineplunder.network.SyncSoulFireMessage;

public class ClientHandler {

	public static void readReputationMessage(SyncReputationMessage message) {
		Minecraft mc = Minecraft.getInstance();
		Player player = mc.player;
		LazyOptional<Reputation> optional = player.getCapability(MineplunderCapabilities.REPUTATION_CAPABILITY);
		if (optional.isPresent() && message.getFaction() != null) {
			Reputation cap = optional.resolve().get();
			cap.setReputation(player, message.getFaction(), message.getReputation());
		}
	}

    public static void syncSoulFire(SyncSoulFireMessage message) {
		ClientLevel level = Minecraft.getInstance().level;
		if (level == null) return;
		SoulFire.setSoulFire(message.getEntity(level), message.isSoulFire());
	}
}
