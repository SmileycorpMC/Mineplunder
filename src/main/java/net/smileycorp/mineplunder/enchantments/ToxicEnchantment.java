package net.smileycorp.mineplunder.enchantments;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.smileycorp.mineplunder.init.MineplunderEnchantments;

public class ToxicEnchantment extends Enchantment {
    public ToxicEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    public int getMinCost(int level) {
        return 10 + 15 * (level - 1);
    }

    public int getMaxCost(int p_45002_) {
        return super.getMinCost(p_45002_) + 50;
    }

    public int getMaxLevel() {
        return 3;
    };

    public boolean checkCompatibility(Enchantment enchantment) {
        return super.checkCompatibility(enchantment) &! MineplunderEnchantments.isDotEnchantment(enchantment);
    }

    public void doPostAttack(LivingEntity user, Entity target, int level) {
        if (!(target instanceof LivingEntity)) return;
        ((LivingEntity) target).addEffect(new MobEffectInstance(MobEffects.POISON, (level * 4 - 1) * 20, level));
    }

}
