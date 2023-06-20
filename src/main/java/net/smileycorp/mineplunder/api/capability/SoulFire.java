package net.smileycorp.mineplunder.api.capability;

import net.minecraft.nbt.ByteTag;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.util.LazyOptional;


public interface SoulFire {

    boolean isAblaze();

    void setSoulFire(boolean soulFire, Entity entity);

    ByteTag save();

    void load(ByteTag tag);

    static void setSoulFire(Entity entity, boolean soulFire) {
        if (entity == null || entity.fireImmune()) return;
        LazyOptional<SoulFire> cap = entity.getCapability(MineplunderCapabilities.SOULFIRE_CAPABILITY);
        if (!cap.isPresent()) return;
        cap.resolve().get().setSoulFire(soulFire, entity);
    }

    static void setBurning(Entity entity, int ticks) {
       setSoulFire(entity, true);
       entity.setRemainingFireTicks(ticks);
    }

    static boolean isAblaze(Entity entity) {
        if (entity == null || entity.fireImmune()) return false;
        LazyOptional<SoulFire> cap = entity.getCapability(MineplunderCapabilities.SOULFIRE_CAPABILITY);
        if (cap.isPresent()) return cap.resolve().get().isAblaze();
        return false;
    }

}
