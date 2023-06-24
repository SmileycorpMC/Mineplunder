package net.smileycorp.mineplunder.mixin;

import net.minecraft.client.resources.ClientPackSource;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.BuiltInPackSource;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.resource.PathPackResources;
import net.smileycorp.mineplunder.Mineplunder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Mixin(BuiltInPackSource.class)
public class MixinBuiltInPackSource {

	@Inject(at = @At("TAIL"), method = "loadPacks", cancellable = true)
	private void loadPacks(Consumer<Pack> packConsumer, CallbackInfo callback) {
		Path pack = FMLPaths.GAMEDIR.get().resolve("config").resolve("mineplunder");
		PathPackResources resources = new PathPackResources("mineplunder-config", true, pack);
		packConsumer.accept(Pack.readMetaAndCreate("mineplunder-config", Component.literal("Mineplunder Config"), false,
				(str)->resources, (Object)this instanceof ClientPackSource ? PackType.CLIENT_RESOURCES : PackType.SERVER_DATA, Pack.Position.TOP, PackSource.BUILT_IN));
	}

}