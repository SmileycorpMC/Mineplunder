package net.smileycorp.mineplunder.common.network;

import net.minecraftforge.network.simple.SimpleChannel;
import net.smileycorp.atlas.api.network.NetworkUtils;
import net.smileycorp.mineplunder.common.Constants;

public class PacketHandler {

	public static SimpleChannel NETWORK_INSTANCE;

	public static void initPackets() {
		NETWORK_INSTANCE = NetworkUtils.createChannel(Constants.loc("main"));
		//NetworkUtils.registerMessage(NETWORK_INSTANCE, 0, SyncFactionsMessage.class);
		NetworkUtils.registerMessage(NETWORK_INSTANCE, 1, SyncReputationMessage.class);
		NetworkUtils.registerMessage(NETWORK_INSTANCE, 2, SyncSoulFireMessage.class);
	}

}
