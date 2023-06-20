package net.smileycorp.mineplunder.capability;

import net.minecraft.nbt.ByteTag;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.PacketDistributor;
import net.smileycorp.mineplunder.api.capability.SoulFire;
import net.smileycorp.mineplunder.network.PacketHandler;
import net.smileycorp.mineplunder.network.SyncSoulFireMessage;

public class SoulFireImpl implements SoulFire {

    private boolean isSoulFire;

    @Override
    public boolean isAblaze() {
        return isSoulFire;
    }

    @Override
    public void setSoulFire(boolean isSoulFire, Entity entity) {
        if (!entity.level().isClientSide && this.isSoulFire != isSoulFire) PacketHandler.NETWORK_INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(
                ()->entity.level().getChunkAt(entity.getOnPos())), new SyncSoulFireMessage(entity, isSoulFire));
        this.isSoulFire = isSoulFire;
    }

    @Override
    public ByteTag save() {
        return ByteTag.valueOf(isSoulFire);
    }

    @Override
    public void load(ByteTag tag) {
        this.isSoulFire = tag.getAsByte() > 0;
    }
}
