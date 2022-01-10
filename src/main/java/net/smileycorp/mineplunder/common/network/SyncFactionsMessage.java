package net.smileycorp.mineplunder.common.network;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.PacketListener;
import net.smileycorp.atlas.api.network.SimpleAbstractMessage;
import net.smileycorp.mineplunder.api.Faction;

public class SyncFactionsMessage extends SimpleAbstractMessage {

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

}
