package com.euphony.nicer_skies_reforged;

import com.euphony.nicer_skies_reforged.config.Config;
import com.euphony.nicer_skies_reforged.gui.ConfigScreen;
import com.euphony.nicer_skies_reforged.sky.SkyManager;
import com.mojang.logging.LogUtils;
import lombok.Getter;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.slf4j.Logger;

import java.io.File;

@Getter
@Mod(NicerSkiesReforged.MODID)
public class NicerSkiesReforged {
    public static final String MODID = "nicer_skies_reforged";
    public static final Logger LOGGER = LogUtils.getLogger();

    private static NicerSkiesReforged INSTANCE;

    private final Config config;
    private final SkyManager skyManager;

    public NicerSkiesReforged(IEventBus modEventBus, ModContainer modContainer) {
        INSTANCE = this;

        this.config = Config.fromFile(new File(FMLPaths.CONFIGDIR.get().toFile(), "nicerskies.json"));

        this.skyManager = new SkyManager();

        modContainer.registerExtensionPoint(IConfigScreenFactory.class, (mc, parent) -> new ConfigScreen(parent));
    }

    public static NicerSkiesReforged getInstance() {
        return INSTANCE;
    }
}
