package net.smileycorp.mineplunder.init;

import com.google.common.collect.Lists;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.smileycorp.mineplunder.Constants;
import net.smileycorp.mineplunder.enchantments.DecayEnchantment;
import net.smileycorp.mineplunder.enchantments.FrostburnEnchantment;
import net.smileycorp.mineplunder.enchantments.ToxicEnchantment;

import java.util.List;
import java.util.function.Supplier;

public class MineplunderEnchantments {

    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Constants.MODID);

    public static final RegistryObject<Enchantment> SOULBLAZE = ENCHANTMENTS.register("soulblaze", DecayEnchantment::new );
    public static final RegistryObject<Enchantment> DECAY = ENCHANTMENTS.register("decay", DecayEnchantment::new );
    public static final RegistryObject<Enchantment> TOXIC = ENCHANTMENTS.register("toxic", ToxicEnchantment::new );

    public static final RegistryObject<Enchantment> FROSTBURN = ENCHANTMENTS.register("frostburn", FrostburnEnchantment::new );


    public static final List<Supplier<Enchantment>> DOT_ENCHANTMENTS = Lists.newArrayList(
            ()->Enchantments.FIRE_ASPECT, SOULBLAZE, DECAY, TOXIC, FROSTBURN);
    public static boolean isDotEnchantment(Enchantment enchantment) {
        for (Supplier<Enchantment> test : DOT_ENCHANTMENTS) if (test.get() == enchantment) return true;
        return false;
    }
}
