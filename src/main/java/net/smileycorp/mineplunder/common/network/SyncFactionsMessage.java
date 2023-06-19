package net.smileycorp.mineplunder.common.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.PacketListener;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.smileycorp.atlas.api.network.AbstractMessage;
import net.smileycorp.mineplunder.api.Faction;
import net.smileycorp.mineplunder.api.ReputationHandler;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class SyncFactionsMessage extends AbstractMessage {

	private String data;

	public SyncFactionsMessage() {}

	public SyncFactionsMessage(Collection<Faction> factions) {
		StringBuilder builder = new StringBuilder();
		for (Faction faction : factions) {
			builder.append(faction.toString());
			builder.append(";");
		}
		data = builder.toString();
	}


	@Override
	public void read(FriendlyByteBuf buf){
		data = buf.readUtf();
	}

	@Override
	public void write(FriendlyByteBuf buf){
		if (data!=null) buf.writeUtf(data);
	}

	public Collection<Faction> getFactionData() {
		Set<Faction> factions = new HashSet<Faction>();
		for (String factionData : data.split(";")) {
			if (!factionData.isBlank()) {
				Faction faction = Faction.fromJson("from server packet " + hashCode(), factionData);
				if (faction != null) factions.add(faction);
			}
		}
		return factions;
	}

	@Override
	public void handle(PacketListener listener) {}

	@Override
	public void process(NetworkEvent.Context ctx) {
		ctx.enqueueWork(() ->  DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> () -> ReputationHandler.readPacketData(getFactionData())));
		ctx.setPacketHandled(true);
	}

}
