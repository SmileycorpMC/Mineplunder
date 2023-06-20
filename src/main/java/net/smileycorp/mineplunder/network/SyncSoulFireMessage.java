package net.smileycorp.mineplunder.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.PacketListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.smileycorp.atlas.api.network.AbstractMessage;
import net.smileycorp.mineplunder.client.ClientHandler;

public class SyncSoulFireMessage extends AbstractMessage {

	private int entity;
	private boolean soulFire;

	public SyncSoulFireMessage() {}

	public SyncSoulFireMessage(Entity entity, boolean soulFire) {
		this.entity = entity.getId();
		this.soulFire = soulFire;
	}

	@Override
	public void read(FriendlyByteBuf buf){
		entity = buf.readInt();
		soulFire = buf.readBoolean();
	}

	@Override
	public void write(FriendlyByteBuf buf){
		buf.writeInt(entity);
		buf.writeBoolean(soulFire);
	}

	public Entity getEntity(Level level) {
		return level.getEntity(entity);
	}

	public boolean isSoulFire() {
		return soulFire;
	}

	@Override
	public void handle(PacketListener listener) {}

	@Override
	public void process(NetworkEvent.Context ctx) {
		ctx.enqueueWork(() ->  DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> () -> ClientHandler.syncSoulFire(this)));
		ctx.setPacketHandled(true);
	}

}
