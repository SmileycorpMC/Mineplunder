package net.smileycorp.mineplunder.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;
import net.smileycorp.mineplunder.api.ReputationHandler;
import net.smileycorp.mineplunder.api.capability.IReputation;
import net.smileycorp.mineplunder.common.network.SyncReputationMessage;

public class ClientHandler {

	public static void readReputationMessage(SyncReputationMessage message) {
		Minecraft mc = Minecraft.getInstance();
		Player player = mc.player;
		LazyOptional<IReputation> optional = player.getCapability(ReputationHandler.REPUTATION_CAPABILITY);
		if (optional.isPresent() && message.getFaction() != null) {
			IReputation cap = optional.resolve().get();
			cap.setReputation(player, message.getFaction(), message.getReputation());
		}
	}

}
