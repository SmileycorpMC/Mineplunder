package net.smileycorp.mineplunder.common.network;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.smileycorp.atlas.api.network.SimpleMessageDecoder;
import net.smileycorp.atlas.api.network.SimpleMessageEncoder;
import net.smileycorp.mineplunder.api.ReputationHandler;
import net.smileycorp.mineplunder.client.ClientHandler;
import net.smileycorp.mineplunder.common.ModDefinitions;

public class PacketHandler {

	public static SimpleChannel NETWORK_INSTANCE;

	public static void initPackets() {
		NETWORK_INSTANCE = NetworkRegistry.newSimpleChannel(ModDefinitions.getResource("main"), ()-> "1", "1"::equals, "1"::equals);
		NETWORK_INSTANCE.registerMessage(0, SyncFactionsMessage.class, new SimpleMessageEncoder<SyncFactionsMessage>(),
				new SimpleMessageDecoder<SyncFactionsMessage>(SyncFactionsMessage.class), (T, K)-> processSyncFactionsMessage(T, K.get()));
		NETWORK_INSTANCE.registerMessage(0, SyncReputationMessage.class, new SimpleMessageEncoder<SyncReputationMessage>(),
				new SimpleMessageDecoder<SyncReputationMessage>(SyncReputationMessage.class), (T, K)-> processSyncReputationMessage(T, K.get()));
	}

	public static void processSyncFactionsMessage(SyncFactionsMessage message, Context ctx) {
		ctx.enqueueWork(() ->  DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> () -> ReputationHandler.readPacketData(message.getFactionData())));
		ctx.setPacketHandled(true);
	}

	public static void processSyncReputationMessage(SyncReputationMessage message, Context ctx) {
		ctx.enqueueWork(() ->  DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> () -> ClientHandler.readReputationMessage(message)));
		ctx.setPacketHandled(true);
	}

}
