package net.smileycorp.mineplunder.common;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.smileycorp.mineplunder.api.capability.IReputation;
import net.smileycorp.mineplunder.common.capability.ReputationProvider;
import net.smileycorp.mineplunder.common.network.PacketHandler;
import net.smileycorp.mineplunder.config.FactionParser;

@Mod(value = ModDefinitions.MODID)
@Mod.EventBusSubscriber(modid = ModDefinitions.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Mineplunder {

	public static ScheduledExecutorService DELAYED_THREAD_EXECUTOR = Executors.newSingleThreadScheduledExecutor();
	private static Logger logger = LogManager.getLogger(ModDefinitions.NAME);

	public Mineplunder() {

	}

	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event){

	}

	@SubscribeEvent
	public static void setup(FMLCommonSetupEvent event){
		FactionParser.readFactionsFromConfig();
		PacketHandler.initPackets();
	}

	@SubscribeEvent
	public void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.register(IReputation.class);
	}

	@SubscribeEvent
	public void attachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
		Entity entity = event.getObject();
		if (entity instanceof Player &!(entity instanceof FakePlayer)) {
			event.addCapability(ModDefinitions.getResource("reputation"), new ReputationProvider());
		}
	}

	public static void logInfo(Object message) {
		logger.info(message);
	}

	public static void logError(Object message, Exception e) {
		logger.error(message);
		e.printStackTrace();
	}
}
