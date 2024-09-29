package net.smileycorp.mineplunder.entities;

import com.google.common.collect.Maps;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BannerPatterns;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.Tags;
import net.smileycorp.atlas.api.util.TextUtils;
import net.smileycorp.mineplunder.init.MineplunderEntities;

import javax.annotation.Nullable;
import java.util.Map;

public class Marauder extends AbstractIllager {

    public Marauder(EntityType<? extends Marauder> type, Level level) {
        super(type, level);
    }

    public Marauder(Level level) {
        this(MineplunderEntities.MARAUDER.get(), level);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new AbstractIllager.RaiderOpenDoorGoal(this));
        this.goalSelector.addGoal(3, new Raider.HoldGroundAttackGoal(this, 10.0F));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 0.3f, true));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, Raider.class)).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6D));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
    }

    protected void customServerAiStep() {
        super.customServerAiStep();
        if (getTarget() != null && getItemInHand(InteractionHand.OFF_HAND).is(Tags.Items.TOOLS_SHIELDS))
            startUsingItem(InteractionHand.OFF_HAND);
        if (getTarget() == null && isUsingItem()) stopUsingItem();
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_33282_, DifficultyInstance p_33283_, MobSpawnType p_33284_, @Nullable SpawnGroupData p_33285_, @Nullable CompoundTag p_33286_) {
        RandomSource randomsource = p_33282_.getRandom();
        this.populateDefaultEquipmentSlots(randomsource, p_33283_);
        this.populateDefaultEquipmentEnchantments(randomsource, p_33283_);
        return super.finalizeSpawn(p_33282_, p_33283_, p_33284_, p_33285_, p_33286_);
    }

    protected void populateDefaultEquipmentSlots(RandomSource p_219059_, DifficultyInstance p_219060_) {
        setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
        setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.CHAINMAIL_CHESTPLATE));
        setItemSlot(EquipmentSlot.LEGS, new ItemStack(Items.CHAINMAIL_LEGGINGS));
        setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
        ItemStack shield = new ItemStack(Items.SHIELD);
        CompoundTag tag = new CompoundTag();
        ListTag listtag = (new BannerPattern.Builder()).addPattern(BannerPatterns.RHOMBUS_MIDDLE, DyeColor.CYAN)
                .addPattern(BannerPatterns.STRIPE_BOTTOM, DyeColor.LIGHT_GRAY)
                .addPattern(BannerPatterns.STRIPE_CENTER, DyeColor.GRAY)
                .addPattern(BannerPatterns.BORDER, DyeColor.LIGHT_GRAY)
                .addPattern(BannerPatterns.STRIPE_MIDDLE, DyeColor.BLACK)
                .addPattern(BannerPatterns.HALF_HORIZONTAL, DyeColor.LIGHT_GRAY)
                .addPattern(BannerPatterns.CIRCLE_MIDDLE, DyeColor.LIGHT_GRAY)
                .addPattern(BannerPatterns.BORDER, DyeColor.BLACK).toListTag();
        tag.put("Patterns", listtag);
        BlockItem.setBlockEntityData(shield, BlockEntityType.BANNER, tag);
        shield.setHoverName(TextUtils.translatableComponent("item.mineplunder.marauder_shield", "Marauder Shield")
                .withStyle(ChatFormatting.GOLD));
        setItemSlot(EquipmentSlot.OFFHAND, shield);
    }

    @Override
    public void applyRaidBuffs(int p_34079_, boolean p_34080_) {
        ItemStack itemstack = new ItemStack(Items.IRON_SWORD);
        Raid raid = this.getCurrentRaid();
        int i = 1;
        if (p_34079_ > raid.getNumGroups(Difficulty.NORMAL)) {
            i = 2;
        }
        boolean flag = this.random.nextFloat() <= raid.getEnchantOdds();
        if (flag) {
            Map<Enchantment, Integer> map = Maps.newHashMap();
            map.put(Enchantments.SHARPNESS, i);
            EnchantmentHelper.setEnchantments(map, itemstack);
        }
        setItemSlot(EquipmentSlot.MAINHAND, itemstack);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, 30.0D)
                .add(Attributes.MAX_HEALTH, 30.0D).add(Attributes.ATTACK_DAMAGE, 3.0D);
    }

    @Override
    public SoundEvent getCelebrateSound() {
        return SoundEvents.VINDICATOR_CELEBRATE;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.VINDICATOR_AMBIENT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.VINDICATOR_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource p_32654_) {
        return SoundEvents.VINDICATOR_HURT;
    }

    @Override
    public AbstractIllager.IllagerArmPose getArmPose() {
        if (getTarget() != null) return IllagerArmPose.BOW_AND_ARROW;
        return isCelebrating() ? AbstractIllager.IllagerArmPose.CELEBRATING : AbstractIllager.IllagerArmPose.NEUTRAL;
    }

}
