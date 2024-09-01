package net.smileycorp.mineplunder;

import net.minecraft.client.resources.ClientPackSource;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.resource.PathPackResources;
import net.smileycorp.mineplunder.api.capability.Reputation;
import net.smileycorp.mineplunder.api.capability.SpecialFire;
import net.smileycorp.mineplunder.capability.ReputationProvider;
import net.smileycorp.mineplunder.capability.SpecialFireProvider;
import net.smileycorp.mineplunder.init.*;
import net.smileycorp.mineplunder.network.PacketHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.file.Path;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Mod(value = Constants.MODID)
@Mod.EventBusSubscriber(modid = Constants.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Mineplunder {

	public static ScheduledExecutorService DELAYED_THREAD_EXECUTOR = Executors.newSingleThreadScheduledExecutor();
	private static Logger logger = LogManager.getLogger(Constants.NAME);

	public static Mineplunder INSTANCE;

	public Mineplunder() {
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public static void constructMod(FMLConstructModEvent event) {
		MineplunderBlocks.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
		MineplunderBlocks.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
		MineplunderEffects.EFFECTS.register(FMLJavaModLoadingContext.get().getModEventBus());
		MineplunderEnchantments.ENCHANTMENTS.register(FMLJavaModLoadingContext.get().getModEventBus());
		MineplunderEntities.ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
		MineplunderEntities.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
		MineplunderItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
		MineplunderParticles.PARTICLES.register(FMLJavaModLoadingContext.get().getModEventBus());
	}

	@SubscribeEvent
	public static void setup(FMLCommonSetupEvent event){
		//FactionParser.readFactionsFromConfig();
		PacketHandler.initPackets();
	}

	@SubscribeEvent
	public static void loadComplete(FMLLoadCompleteEvent event) {
		MineplunderItems.registerDispenserBehaviour();
		MineplunderEntities.addRaidMobs();
	}

	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event){
	}

	@SubscribeEvent
	public void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.register(Reputation.class);
		event.register(SpecialFire.class);
	}

	@SubscribeEvent
	public void attachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
		Entity entity = event.getObject();
		event.addCapability(Constants.loc("special_fire"), new SpecialFireProvider());
		if (entity instanceof Player &!(entity instanceof FakePlayer)) {
			event.addCapability(Constants.loc("reputation"), new ReputationProvider());
		}
	}
	
	//@SubscribeEvent
	public static void addPacks(AddPackFindersEvent event) {
			Path pack = FMLPaths.GAMEDIR.get().resolve("config").resolve("mineplunder");
			PathPackResources resources = new PathPackResources("mineplunder-config", true, pack);
			event.addRepositorySource(consumer -> consumer.accept(Pack.readMetaAndCreate("mineplunder-config", Component.literal("Mineplunder Config"), true,
					(str)->resources, event.getPackType(), Pack.Position.TOP, PackSource.BUILT_IN)));
	}

	public static void logInfo(Object message) {
		logger.info(message);
	}

	public static void logError(Object message, Exception e) {
		logger.error(message);
		e.printStackTrace();
	}
}
