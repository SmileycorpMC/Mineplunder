package net.smileycorp.mineplunder.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.smileycorp.mineplunder.api.capability.MineplunderCapabilities;
import net.smileycorp.mineplunder.api.capability.Reputation;

public class ReputationProvider implements ICapabilitySerializable<CompoundTag> {

	private final Reputation impl;

	public ReputationProvider() {
		impl = new ReputationImpl();
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		return cap == MineplunderCapabilities.REPUTATION_CAPABILITY ? LazyOptional.of(() -> impl).cast() : LazyOptional.empty();
	}

	@Override
	public CompoundTag serializeNBT() {
		return impl.writeNBT(new CompoundTag());
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		impl.readNBT(nbt);
	}

}