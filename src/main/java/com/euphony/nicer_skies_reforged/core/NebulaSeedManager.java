package com.euphony.nicer_skies_reforged.core;

import com.euphony.nicer_skies_reforged.ClientLevelAccessor;
import net.minecraft.client.Minecraft;

public class NebulaSeedManager {
    public static long getSeed() {
        if (Minecraft.getInstance().level != null) {
            return ((ClientLevelAccessor) Minecraft.getInstance().level).nicerSkies_getHashedSeed();
        }
        return 321L; // handpicked decent default ;)
    }

    public static boolean canGenerateSky() {
        return Minecraft.getInstance().hasSingleplayerServer() || Minecraft.getInstance().getCurrentServer() != null;
    }
}
