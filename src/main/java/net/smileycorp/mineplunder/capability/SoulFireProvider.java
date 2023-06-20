package net.smileycorp.mineplunder.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.ByteTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.smileycorp.mineplunder.api.capability.MineplunderCapabilities;
import net.smileycorp.mineplunder.api.capability.SoulFire;

public class SoulFireProvider implements ICapabilitySerializable<ByteTag> {

    private final SoulFire impl = new SoulFireImpl();

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return cap == MineplunderCapabilities.SOULFIRE_CAPABILITY ? LazyOptional.of(() -> impl).cast() : LazyOptional.empty();
    }

    @Override
    public ByteTag serializeNBT() {
        return impl.save();
    }

    @Override
    public void deserializeNBT(ByteTag nbt) {
        impl.load(nbt);
    }


}
