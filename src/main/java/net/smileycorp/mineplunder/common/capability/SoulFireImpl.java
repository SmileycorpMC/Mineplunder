package net.smileycorp.mineplunder.common.capability;

import net.minecraft.nbt.IntTag;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.PacketDistributor;
import net.smileycorp.mineplunder.api.capability.SoulFire;
import net.smileycorp.mineplunder.common.DamageSources;
import net.smileycorp.mineplunder.common.network.PacketHandler;
import net.smileycorp.mineplunder.common.network.SyncSoulFireMessage;

public class SoulFireImpl implements SoulFire {

    private int fireTicks = 0;

    @Override
    public void setBurning(int ticks, Entity entity) {
        fireTicks = ticks;
        if (!entity.level().isClientSide) PacketHandler.NETWORK_INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(
                ()->entity.level().getChunkAt(entity.getOnPos())), new SyncSoulFireMessage(entity, fireTicks));
    }

    @Override
    public int getBurningTicks() {
        return fireTicks;
    }

    @Override
    public boolean isBurning() {
        return fireTicks > 0;
    }

    @Override
    public void tick(Entity entity) {
        if (entity.fireImmune()) {
            setBurning(fireTicks - 4, entity);
            if (fireTicks < 0) setBurning(0, entity);
        } else {
            if (fireTicks % 20 == 0) {
                entity.hurt(DamageSources.soulFire(entity), 2.0F);
            }
            setBurning(fireTicks-1, entity);
        }
    }

    @Override
    public IntTag save() {
        return IntTag.valueOf(fireTicks);
    }

    @Override
    public void load(IntTag tag) {
        fireTicks = tag.getAsInt();
    }
}
