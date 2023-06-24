package net.smileycorp.mineplunder.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.smileycorp.mineplunder.Constants;
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


}
