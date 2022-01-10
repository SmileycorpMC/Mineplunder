package net.smileycorp.mineplunder.common.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.network.NetworkDirection;
import net.smileycorp.mineplunder.api.Faction;
import net.smileycorp.mineplunder.api.ReputationHandler;
import net.smileycorp.mineplunder.common.ModDefinitions;
import net.smileycorp.mineplunder.common.network.PacketHandler;
import net.smileycorp.mineplunder.common.network.SyncFactionsMessage;

@EventBusSubscriber(modid = ModDefinitions.MODID)
public class ReputationEvents {

	//activate when a player joins a server
	@SubscribeEvent
	public static void onPlayerJoin(PlayerLoggedInEvent event) {
		Player player = event.getPlayer();
		if (!player.level.isClientSide) {
			PacketHandler.NETWORK_INSTANCE.sendTo(new SyncFactionsMessage(ReputationHandler.getFactions()),
					((ServerPlayer)player).connection.connection, NetworkDirection.PLAY_TO_CLIENT);
		}
	}

	@SubscribeEvent
	public static void onDeath(LivingDeathEvent event) {
		LivingEntity entity = event.getEntityLiving();
		Level level = entity.level;
		if(!level.isClientSide) {
			DamageSource source = event.getSource();
			Player player = null;
			if (source.getEntity() instanceof Player) player = (Player) source.getEntity();
			else if(source.getDirectEntity() instanceof Player) player = (Player) source.getDirectEntity();
			if (player != null) {
				for(Faction faction : ReputationHandler.getEntityFactions(entity)) {
					ReputationHandler.changeReputation(player, faction, 0);
				}
			}
		}
	}

}
