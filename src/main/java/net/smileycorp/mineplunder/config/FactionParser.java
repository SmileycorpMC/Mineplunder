package net.smileycorp.mineplunder.config;

import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.loading.moddiscovery.ModFile;
import net.smileycorp.mineplunder.api.Faction;
import net.smileycorp.mineplunder.api.ReputationHandler;
import net.smileycorp.mineplunder.common.Constants;
import net.smileycorp.mineplunder.common.Mineplunder;
import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class FactionParser {

	public static void readFactionsFromConfig() {
		File directory = FMLPaths.GAMEDIR.get().resolve("config").resolve("piper").toFile();
		if (!(directory.exists() || directory.isDirectory())) {
			try {
				createDefaultFiles(directory);
			} catch (Exception e) {
				Mineplunder.logError("failed to write file", e);
			}
		}
		for (File file : directory.toPath().resolve("factions").toFile().listFiles()) {
			if (file.getName().endsWith(".json")) {
				try {
					String name = file.getName().replace(".json", "");
					StringBuilder builder = new StringBuilder();
					BufferedReader reader = new BufferedReader(new FileReader(file));
					reader.lines().forEach((s)->builder.append(s));
					reader.close();
					Faction faction = Faction.fromJson(name, builder.toString());
					ReputationHandler.registerFaction(faction);
				} catch (Exception e) {
					Mineplunder.logError("failed to load file " + file.getName(), e);
				}
			}
		}
	}

	private static void createDefaultFiles(File directory) throws Exception {
		ModFile mod = FMLLoader.getLoadingModList().getModFileById(Constants.MODID).getFile();
		copyFile(mod, directory, "factions/illager.json");
		copyFile(mod, directory, "factions/nether.json");
		copyFile(mod, directory, "factions/piglin.json");
		copyFile(mod, directory, "factions/skeleton.json");
		copyFile(mod, directory, "factions/villager.json");
		copyFile(mod, directory, "resources/pack.mcmeta");
		copyFile(mod, directory, "resources/assets/piper/lang/en_us.json");
	}

	private static void copyFile(ModFile mod, File directory, String path) throws Exception {
		File output = new File(directory, path);
		File dir = output.getParentFile();
		if (dir!=null)dir.mkdirs();
		FileUtils.copyInputStreamToFile(Files.newInputStream(mod.findResource("config-defaults/"+path), StandardOpenOption.READ), new File(directory, path));
	}

}
