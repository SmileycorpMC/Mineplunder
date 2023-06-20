package net.smileycorp.mineplunder;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.smileycorp.mineplunder.enchantments.SoulblazeEnchantment;

public class MineplunderEnchantments {

    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Constants.MODID);

    public static final RegistryObject<Enchantment> SOULBLAZE = ENCHANTMENTS.register("soulblaze", SoulblazeEnchantment::new );

}
