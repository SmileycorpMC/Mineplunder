package net.smileycorp.mineplunder.common;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.smileycorp.mineplunder.api.capability.Reputation;
import net.smileycorp.mineplunder.api.capability.SoulFire;
import net.smileycorp.mineplunder.common.capability.ReputationProvider;
import net.smileycorp.mineplunder.common.capability.SoulFireProvider;
import net.smileycorp.mineplunder.common.network.PacketHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
	public static void clientSetup(FMLClientSetupEvent event){
	}

	@SubscribeEvent
	public static void setup(FMLCommonSetupEvent event){
		//FactionParser.readFactionsFromConfig();
		PacketHandler.initPackets();
	}

	@SubscribeEvent
	public void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.register(Reputation.class);
		event.register(SoulFire.class);
	}

	@SubscribeEvent
	public void attachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
		Entity entity = event.getObject();
		event.addCapability(Constants.loc("soulfire"), new SoulFireProvider());
		if (entity instanceof Player &!(entity instanceof FakePlayer)) {
			event.addCapability(Constants.loc("reputation"), new ReputationProvider());
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
