package com.euphony.nicer_skies_reforged.mixin;

import com.euphony.nicer_skies_reforged.NicerSkiesReforged;
import com.llamalad7.mixinextras.sugar.Local;
import lombok.SneakyThrows;
import net.minecraft.client.renderer.LightTexture;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LightTexture.class)
public abstract class LightTextureMixin {
    @Inject(
            method = "updateLightTexture",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LightTexture;clampColor(Lorg/joml/Vector3f;)V", shift = At.Shift.BEFORE, ordinal = 2)
    )
    @SneakyThrows
    private void injectWarmLight(float partialTicks, CallbackInfo ci, @Local(ordinal = 0) Vector3f vector3f, @Local(ordinal = 1) Vector3f vector3f2, @Local(ordinal = 0) int n, @Local(ordinal = 1) int o) {
        if (!NicerSkiesReforged.getInstance().getConfig().getLightmapTweaked()) return;
        Vector3f warmTint = new Vector3f(0.36F, 0.13F, -0.15F);

        float warmness = o / 15f * // increase w/ blocklight
                (1f - vector3f.x() * (1 - n / 15f)) * // decrease in skylight w/ dayness
                Math.min((15 - o) / 9f, 1f); // decrease for the 3 highest block light levels

        warmTint.mul(warmness);
        warmTint.add(1f, 1f, 1f);

        Vector3f dramaticFactor = (Vector3f) vector3f2.clone();
        dramaticFactor.mul(0.20f);
        dramaticFactor.add(0.80f, 0.80f, 0.81f);

        vector3f2.mul(dramaticFactor.x(), dramaticFactor.y(), dramaticFactor.z());
        vector3f2.mul(warmTint.x(), warmTint.y(), warmTint.z());
    }
}
