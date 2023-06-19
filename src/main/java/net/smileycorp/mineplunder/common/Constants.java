package net.smileycorp.mineplunder.common;

import net.minecraft.resources.ResourceLocation;

public class Constants {

	public static final String MODID = "mineplunder";

	public static final String NAME = "Mineplunder";

	public static String name(String name) {
		return MODID + "." + name.replace("_", "");
	}

	public static ResourceLocation loc(String name) {
		return new ResourceLocation(MODID, name.toLowerCase());
	}
}
