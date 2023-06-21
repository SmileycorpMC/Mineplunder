package net.smileycorp.mineplunder.init;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.smileycorp.mineplunder.Constants;
import net.smileycorp.mineplunder.items.SoulChargeItem;

public class MineplunderItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MODID);

    public static final RegistryObject<Item> SOUL_POWDER = ITEMS.register("soul_powder", ()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> SOUL_ROD = ITEMS.register("soul_rod", ()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> SOUL_CHARGE = ITEMS.register("soul_charge", SoulChargeItem::new);

}
