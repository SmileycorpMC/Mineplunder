package net.smileycorp.mineplunder.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.ForgeRegistries;
import net.smileycorp.mineplunder.Mineplunder;

import java.util.ArrayList;
import java.util.List;

public class Faction {

	private ResourceLocation name;
	private int defaultRep;
	private List<ResourceLocation> enemy_names = new ArrayList<ResourceLocation>();
	private List<Faction> enemies = new ArrayList<Faction>();
	private List<EntityType<?>> members = new ArrayList<EntityType<?>>();

	public Faction(ResourceLocation name, int defaultRep, List<ResourceLocation> enemies, List<EntityType<?>> members) {
		this.name = name;
		this.defaultRep = defaultRep;
		enemy_names.addAll(enemies);
		this.members.addAll(members);
	}

	public ResourceLocation getName() {
		return name == null ? new ResourceLocation("") : name;
	}

	public List<Faction> getEnemies() {
		if (enemies.isEmpty() &! enemy_names.isEmpty()) {
			for (ResourceLocation loc : enemy_names) {
				Faction faction = ReputationHandler.getFaction(loc);
				if (faction != null) enemies.add(faction);
			}
		}
		return enemies;
	}

	public boolean isEnemy(Faction faction) {
		return getEnemies().contains(faction);
	}

	public List<EntityType<?>> getMembers() {
		return members;
	}

	public boolean isMember(LivingEntity entity) {
		return members.contains(entity.getType());
	}

	public int getDefaultRep() {
		return defaultRep;
	}

	public static Faction fromJson(String identifier, String jsonString) {
		try {
			JsonObject json = (JsonObject) JsonParser.parseString(jsonString);
			ResourceLocation name = new ResourceLocation(json.get("name").getAsString());
			int defaultRep = json.get("default_reputation").getAsInt();
			List<EntityType<?>> members = parseMembers(jsonString, json.get("members").getAsJsonArray());
			List<ResourceLocation> enemies = parseResourceArray(jsonString, json.get("enemies").getAsJsonArray());
			return new Faction(name, defaultRep, enemies, members);
		} catch (Exception e) {
			Mineplunder.logError("Failed to add faction " + identifier, e);
			Mineplunder.logInfo(jsonString);
		}
		return null;
	}

	private static List<EntityType<?>> parseMembers(String filename, JsonArray array) {
		List<EntityType<?>> members = new ArrayList<EntityType<?>>();
		for (ResourceLocation loc : parseResourceArray(filename, array)) {
			try {
				EntityType<?> entity = ForgeRegistries.ENTITY_TYPES.getValue(loc);
				members.add(entity);
			} catch (Exception e) {
				Mineplunder.logError("Failed to add entry "+ loc + " to faction, entity does not exist " + filename, e);
				Mineplunder.logInfo(array);
			}
		}
		return members;
	}

	private static List<ResourceLocation> parseResourceArray(String filename, JsonArray array) {
		List<ResourceLocation> resources = new ArrayList<ResourceLocation>();
		for (JsonElement json : array) {
			try {
				ResourceLocation loc = new ResourceLocation(json.getAsString());
				resources.add(loc);
			} catch (Exception e) {
				Mineplunder.logError("Failed to add entry "+ json.toString() + " to faction " + filename, e);
				Mineplunder.logInfo(array);
			}
		}
		return resources;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("{");
		builder.append("\"name\": \"" + name.toString() + "\", ");
		builder.append("\"default_reputation\": " + defaultRep + ", ");
		builder.append("\"members\": [");
		for (int i = 0; i < members.size(); i++) {
			builder.append("\""+ members.get(i).toShortString().toString() + "\"");
			if (i < members.size()-1) builder.append(", ");
		}
		builder.append("], ");
		for (int i = 0; i < enemy_names.size(); i++) {
			builder.append("\""+ enemy_names.get(i).toString() + "\"");
			if (i < enemy_names.size()-1) builder.append(", ");
		}
		builder.append("]");
		builder.append("}");
		return builder.toString();
	}

}
