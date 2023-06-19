package net.smileycorp.mineplunder.api.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class MineplunderCapabilities {

    public static Capability<Reputation> REPUTATION_CAPABILITY = CapabilityManager.get(new CapabilityToken<Reputation>(){});

    public static Capability<SoulFire> SOULFIRE_CAPABILITY = CapabilityManager.get(new CapabilityToken<SoulFire>(){});

}
