package net.smileycorp.mineplunder.init;

import net.minecraft.Util;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.smileycorp.mineplunder.Constants;
import net.smileycorp.mineplunder.entities.SmallSoulFireball;
import net.smileycorp.mineplunder.items.SoulChargeItem;

@Mod.EventBusSubscriber(modid = Constants.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MineplunderItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MODID);

    public static final RegistryObject<Item> SOUL_POWDER = ITEMS.register("soul_powder", ()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> SOUL_ROD = ITEMS.register("soul_rod", ()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> SOUL_CHARGE = ITEMS.register("soul_charge", SoulChargeItem::new);

    @SubscribeEvent
    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == BuiltInRegistries.CREATIVE_MODE_TAB.get(CreativeModeTabs.SEARCH))
            for (RegistryObject<Item> item : ITEMS.getEntries()) event.accept(item.get());
        if (event.getTab() == BuiltInRegistries.CREATIVE_MODE_TAB.get(CreativeModeTabs.INGREDIENTS)) {
            event.accept(SOUL_POWDER);
            event.accept(SOUL_ROD);
        }
        if (event.getTab() == BuiltInRegistries.CREATIVE_MODE_TAB.get(CreativeModeTabs.TOOLS_AND_UTILITIES)) {
            event.accept(SOUL_CHARGE);
        }
    }


    public static void registerDispenserBehaviour() {
        DispenserBlock.registerBehavior(SOUL_CHARGE.get(), new DefaultDispenseItemBehavior() {
            public ItemStack execute(BlockSource p_123556_, ItemStack p_123557_) {
                Direction direction = p_123556_.getBlockState().getValue(DispenserBlock.FACING);
                Position position = DispenserBlock.getDispensePosition(p_123556_);
                double d0 = position.x() + (double)((float)direction.getStepX() * 0.3F);
                double d1 = position.y() + (double)((float)direction.getStepY() * 0.3F);
                double d2 = position.z() + (double)((float)direction.getStepZ() * 0.3F);
                Level level = p_123556_.getLevel();
                RandomSource randomsource = level.random;
                double d3 = randomsource.triangle((double)direction.getStepX(), 0.11485000000000001D);
                double d4 = randomsource.triangle((double)direction.getStepY(), 0.11485000000000001D);
                double d5 = randomsource.triangle((double)direction.getStepZ(), 0.11485000000000001D);
                SmallSoulFireball smallfireball = new SmallSoulFireball(level, d0, d1, d2, d3, d4, d5);
                level.addFreshEntity(Util.make(smallfireball, (p_123552_) -> {
                    p_123552_.setItem(p_123557_);
                }));
                p_123557_.shrink(1);
                return p_123557_;
            }

            protected void playSound(BlockSource p_123554_) {
                p_123554_.getLevel().levelEvent(1018, p_123554_.getPos(), 0);
            }
        });
    }
}
