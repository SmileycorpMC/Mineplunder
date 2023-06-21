package net.smileycorp.mineplunder.mixin;

import com.google.common.collect.Maps;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.smileycorp.atlas.api.util.TextUtils;
import net.smileycorp.mineplunder.init.MineplunderEnchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;

@Mixin(WitherSkeleton.class)
public abstract class MixinWitherSkeleton extends AbstractSkeleton {

    protected MixinWitherSkeleton(EntityType<? extends AbstractSkeleton> p_32133_, Level p_32134_) {
        super(p_32133_, p_32134_);
    }

    @Inject(at = @At("HEAD"), method = "populateDefaultEquipmentSlots", cancellable = true)
    protected void populateDefaultEquipmentSlots(RandomSource rand, DifficultyInstance difficulty, CallbackInfo callback) {
        callback.cancel();
        ItemStack sword = new ItemStack(Items.STONE_SWORD);
        HashMap<Enchantment, Integer> enchs = Maps.newHashMap();
        enchs.put(MineplunderEnchantments.DECAY.get(), 1);
        EnchantmentHelper.setEnchantments(enchs, sword);
        sword.setHoverName(TextUtils.translatableComponent("item.mineplunder.witherblade.name", "Witherblade"));
        setItemSlot(EquipmentSlot.MAINHAND, sword);
    }

}
