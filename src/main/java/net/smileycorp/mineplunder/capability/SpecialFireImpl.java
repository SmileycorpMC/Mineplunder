package net.smileycorp.mineplunder.capability;

import net.minecraft.nbt.ByteTag;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.PacketDistributor;
import net.smileycorp.mineplunder.api.capability.SpecialFire;
import net.smileycorp.mineplunder.network.PacketHandler;
import net.smileycorp.mineplunder.network.SyncSoulFireMessage;

public class SpecialFireImpl implements SpecialFire {

    private FireType type;

    @Override
    public FireType getType() { return type; }

    @Override
    public void setFireType(FireType type, Entity entity) {
        if (!entity.level().isClientSide && this.type != type) PacketHandler.NETWORK_INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(
                ()->entity.level().getChunkAt(entity.getOnPos())), new SyncSoulFireMessage(entity, type));
        this.type = type;
    }

    @Override
    public ByteTag save() {
        return ByteTag.valueOf(type == null ? 0 : type.getID());
    }

    @Override
    public void load(ByteTag tag) {
        this.type = FireType.get(tag.getAsByte());
    }
}
