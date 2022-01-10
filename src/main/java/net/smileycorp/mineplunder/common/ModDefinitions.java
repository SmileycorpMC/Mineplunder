package net.smileycorp.mineplunder.common;

import net.minecraft.resources.ResourceLocation;

public class ModDefinitions {

	public static final String MODID = "mineplunder";

	public static final String NAME = "Mineplunder";

	public static String getName(String name) {
		return MODID + "." + name.replace("_", "");
	}

	public static ResourceLocation getResource(String name) {
		return new ResourceLocation(MODID, name.toLowerCase());
	}
}
