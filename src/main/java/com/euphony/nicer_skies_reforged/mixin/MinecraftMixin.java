package com.euphony.nicer_skies_reforged.mixin;

import com.euphony.nicer_skies_reforged.NicerSkiesReforged;
import com.euphony.nicer_skies_reforged.core.NebulaSeedManager;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Inject(at = @At("HEAD"), method = "setLevel")
    private void onWorldLoad(CallbackInfo ci) {
        NicerSkiesReforged.getInstance().getSkyManager().generateSky(NebulaSeedManager.getSeed());
    }
}
