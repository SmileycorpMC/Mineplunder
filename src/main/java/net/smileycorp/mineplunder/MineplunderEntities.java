package net.smileycorp.mineplunder;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.smileycorp.mineplunder.entities.InfernalSoul;
import net.smileycorp.mineplunder.entities.SmallSoulFireball;

@Mod.EventBusSubscriber(modid = Constants.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MineplunderEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Constants.MODID);

    public static final RegistryObject<EntityType<InfernalSoul>> INFERNAL_SOUL = ENTITIES.register("infernal_soul", () ->
            EntityType.Builder.<InfernalSoul>of(InfernalSoul::new, MobCategory.MONSTER).sized(0.66F, 1.98F)
                    .clientTrackingRange(8).build("infernal_soul"));

    public static final RegistryObject<EntityType<SmallSoulFireball>> SMALL_SOUL_FIREBALL = ENTITIES.register("blue_soul_fire", () ->
            EntityType.Builder.<SmallSoulFireball>of(SmallSoulFireball::new, MobCategory.MISC).sized(0.3125F, 0.3125F)
                    .clientTrackingRange(4).updateInterval(10).build("blue_soul_fire"));

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(INFERNAL_SOUL.get(), InfernalSoul.createAttributes().build());
    }

}
