package com.euphony.nicer_skies_reforged.sky;

import com.euphony.nicer_skies_reforged.NicerSkiesReforged;
import com.euphony.nicer_skies_reforged.config.Config;
import com.euphony.nicer_skies_reforged.core.Gradient;
import com.euphony.nicer_skies_reforged.mixin.LevelRendererAccessor;
import com.euphony.nicer_skies_reforged.mixin.LevelRendererInvoker;
import com.euphony.nicer_skies_reforged.sky.nebula.NebulaSkyboxPainter;
import com.euphony.nicer_skies_reforged.sky.nebula.Skybox;
import com.euphony.nicer_skies_reforged.sky.star.Starbox;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.synth.PerlinNoise;

import java.util.stream.IntStream;

@Getter
public class SkyManager {
    private Starbox starbox;
    private Skybox skybox;

    private final Gradient starGradient = new Gradient() {{
        add(0.0f, 255, 179, 97);
        add(0.2f, 255, 249, 253);
        add(1.0f, 175, 199, 255);
    }};

    private final Gradient nebulaGradient = new Gradient() {{
        add(0.2f, 41, 83, 146);
        add(0.5f, 120, 47, 93);
        add(0.7f, 209, 58, 103);
        add(0.8f, 255, 160, 123);
        add(1.0f, 253, 194, 220);
    }};

    public void generateSky(long seed) {
        Config cm = NicerSkiesReforged.getInstance().getConfig();

        RandomSource randomSource = RandomSource.create(seed);

        if (cm.areNebulasEnabled()) {
            PerlinNoise perlinNoise = PerlinNoise.create(randomSource.fork(), IntStream.of(1, 2, 3, 4, 5, 6, 7));
            NebulaSkyboxPainter painter = new NebulaSkyboxPainter(perlinNoise, nebulaGradient, cm.getNebulaNoiseScale(), cm.getNebulaNoiseAmount(), cm.getNebulaBaseColourAmount());
            this.skybox = new Skybox(painter);
        }

        if (cm.areTwinlkingStarsEnabled()) {
            LevelRendererAccessor levelRenderer = (LevelRendererAccessor) Minecraft.getInstance().levelRenderer;
            starbox = new Starbox(randomSource, starGradient, levelRenderer.nicerSkies_getStarBuffer());
            tick(levelRenderer.nicerSkies_getTicks());
        } else {
            ((LevelRendererInvoker) Minecraft.getInstance().levelRenderer).nicerSkies_generateSky();
        }
    }

    public void tick(int ticks) {
        if (starbox != null) {
            this.starbox.updateStars(ticks);
        }
    }
}
