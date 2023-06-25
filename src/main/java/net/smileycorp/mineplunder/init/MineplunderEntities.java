package net.smileycorp.mineplunder.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.smileycorp.mineplunder.Constants;
import net.smileycorp.mineplunder.entities.InfernalSoul;
import net.smileycorp.mineplunder.entities.Skelliger;
import net.smileycorp.mineplunder.entities.SmallSoulFireball;
import net.smileycorp.mineplunder.entities.Witherwight;

@Mod.EventBusSubscriber(modid = Constants.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MineplunderEntities {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MODID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Constants.MODID);

    public static final RegistryObject<EntityType<InfernalSoul>> INFERNAL_SOUL = register("infernal_soul", 0x014074, 0x74F1F5,
            EntityType.Builder.<InfernalSoul>of(InfernalSoul::new, MobCategory.MONSTER).sized(0.72F, 2F).fireImmune()
                    .clientTrackingRange(8));

    public static final RegistryObject<EntityType<Witherwight>> WITHERWIGHT = register("witherwight", 0x0D2A4C, 0x00131D,
            EntityType.Builder.<Witherwight>of(Witherwight::new, MobCategory.MONSTER).sized(0.6F, 1.99F).fireImmune()
                    .clientTrackingRange(8));

    public static final RegistryObject<EntityType<Skelliger>> SKELLIGER = register("skelliger", 0x3FD300, 0xA5A5A5,
            EntityType.Builder.<Skelliger>of(Skelliger::new, MobCategory.MONSTER).sized(0.6F, 1.99F)
                    .clientTrackingRange(8));

    public static final RegistryObject<EntityType<SmallSoulFireball>> SMALL_SOUL_FIREBALL = ENTITIES.register("blue_soul_fireball",
            ()->EntityType.Builder.<SmallSoulFireball>of(SmallSoulFireball::new, MobCategory.MISC).sized(0.3125F, 0.3125F)
                    .clientTrackingRange(4).updateInterval(10).build("blue_soul_fireball"));

    public static <T extends Mob> RegistryObject<EntityType<T>> register(String name, int foreground, int background, EntityType.Builder<T> builder) {
        RegistryObject<EntityType<T>> type = ENTITIES.register(name, ()->builder.build(name));
        ITEMS.register(name+"_spawn_egg", () -> new ForgeSpawnEggItem(type, background, foreground, new Item.Properties()));
        return type;
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(INFERNAL_SOUL.get(), InfernalSoul.createAttributes().build());
        event.put(WITHERWIGHT.get(), AbstractSkeleton.createAttributes().build());
        event.put(SKELLIGER.get(), AbstractSkeleton.createAttributes().build());
    }

    @SubscribeEvent
    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == BuiltInRegistries.CREATIVE_MODE_TAB.get(CreativeModeTabs.SPAWN_EGGS) ||
            event.getTab() == BuiltInRegistries.CREATIVE_MODE_TAB.get(CreativeModeTabs.SEARCH))
            for (RegistryObject<Item> item : ITEMS.getEntries()) event.accept(item.get());
    }

}
