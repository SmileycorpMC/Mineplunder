package net.smileycorp.mineplunder.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.smileycorp.mineplunder.api.capability.SpecialFire;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ScreenEffectRenderer.class)
public class MixinScreenEffectRenderer {

    @Redirect(method = "renderFire", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/model/Material;sprite()Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;"))
    private static TextureAtlasSprite renderFire(Material material) {
        Minecraft mc = Minecraft.getInstance();
        SpecialFire.FireType type = SpecialFire.getFireType(mc.player);
        return type == null ? material.sprite() : Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS)
                .apply(type.getTexture(1));
    }

}
