package net.smileycorp.mineplunder.common.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.smileycorp.mineplunder.api.ReputationHandler;
import net.smileycorp.mineplunder.api.capability.IReputation;

public class ReputationProvider implements ICapabilitySerializable<CompoundTag> {

	private final IReputation impl;

	public ReputationProvider() {
		impl = new Reputation();
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		return cap == ReputationHandler.REPUTATION_CAPABILITY ? LazyOptional.of(() -> impl).cast() : LazyOptional.empty();
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