package net.smileycorp.mineplunder.api.capability;

import net.minecraft.nbt.IntTag;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.util.LazyOptional;


public interface SoulFire {

    void setBurning(int ticks, Entity entity);

    int getBurningTicks();

    boolean isBurning();

    void tick(Entity entity);

    IntTag save();

    void load(IntTag tag);

    static void setBurning(Entity entity, int ticks) {
        if (entity == null || entity.fireImmune()) return;
        LazyOptional<SoulFire> cap = entity.getCapability(MineplunderCapabilities.SOULFIRE_CAPABILITY);
        if (!isBurning(entity)) ticks += entity.getRemainingFireTicks();
        if (cap.isPresent()) cap.resolve().get().setBurning(ticks, entity);
    }

    static void extinguish(Entity entity) {
       setBurning(entity, 0);
    }

    static int getBurningTicks(Entity entity) {
        if (entity == null || entity.fireImmune()) return 0;
        LazyOptional<SoulFire> cap = entity.getCapability(MineplunderCapabilities.SOULFIRE_CAPABILITY);
        if (cap.isPresent()) return cap.resolve().get().getBurningTicks();
        return 0;
    }

    static boolean isBurning(Entity entity) {
        if (entity == null || entity.fireImmune()) return false;
        LazyOptional<SoulFire> cap = entity.getCapability(MineplunderCapabilities.SOULFIRE_CAPABILITY);
        if (cap.isPresent()) return cap.resolve().get().isBurning();
        return false;
    }

}
