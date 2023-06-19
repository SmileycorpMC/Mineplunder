package net.smileycorp.mineplunder.common.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.PacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.smileycorp.atlas.api.network.AbstractMessage;
import net.smileycorp.mineplunder.api.Faction;
import net.smileycorp.mineplunder.api.ReputationHandler;
import net.smileycorp.mineplunder.client.ClientHandler;

public class SyncReputationMessage extends AbstractMessage {

	private ResourceLocation faction;
	private int reputation;

	public SyncReputationMessage() {}

	public SyncReputationMessage(Faction faction, int reputation) {
		this.faction = faction.getName();
		this.reputation = reputation;
	}

	@Override
	public void read(FriendlyByteBuf buf){
		faction = new ResourceLocation(buf.readUtf());
		reputation = buf.readInt();
	}

	@Override
	public void write(FriendlyByteBuf buf){
		if (faction!=null) buf.writeUtf(faction.toString());
		buf.writeInt(reputation);
	}

	public Faction getFaction() {
		return ReputationHandler.getFaction(faction);
	}

	public int getReputation() {
		return reputation;
	}

	@Override
	public void handle(PacketListener listener) {}

	@Override
	public void process(NetworkEvent.Context ctx) {
		ctx.enqueueWork(() ->  DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> () -> ClientHandler.readReputationMessage(this)));
		ctx.setPacketHandled(true);
	}

}
