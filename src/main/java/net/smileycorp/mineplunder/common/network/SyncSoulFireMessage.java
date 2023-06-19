package net.smileycorp.mineplunder.common.network;

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

	private int entity, fireTicks;

	public SyncSoulFireMessage() {}

	public SyncSoulFireMessage(Entity entity, int fireTicks) {
		this.entity = entity.getId();
		this.fireTicks = fireTicks;
	}

	@Override
	public void read(FriendlyByteBuf buf){
		entity = buf.readInt();
		fireTicks = buf.readInt();
	}

	@Override
	public void write(FriendlyByteBuf buf){
		buf.writeInt(entity);
		buf.writeInt(fireTicks);
	}

	public Entity getEntity(Level level) {
		return level.getEntity(entity);
	}

	public int getFireTicks() {
		return fireTicks;
	}

	@Override
	public void handle(PacketListener listener) {}

	@Override
	public void process(NetworkEvent.Context ctx) {
		ctx.enqueueWork(() ->  DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> () -> ClientHandler.syncSoulFire(this)));
		ctx.setPacketHandled(true);
	}

}
