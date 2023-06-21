package net.smileycorp.mineplunder.init;

import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.smileycorp.mineplunder.Constants;
import net.smileycorp.mineplunder.effects.MPEffect;

public class MineplunderEffects {

    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Constants.MODID);

    public static final RegistryObject<MobEffect> FROSTBITE = EFFECTS.register("frostbite", () -> new MPEffect(0x80E5EF));

}
