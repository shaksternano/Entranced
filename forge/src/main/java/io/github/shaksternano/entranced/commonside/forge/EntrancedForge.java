package io.github.shaksternano.entranced.commonside.forge;

import dev.architectury.platform.forge.EventBuses;
import io.github.shaksternano.entranced.commonside.Entranced;
import io.github.shaksternano.entranced.commonside.config.EntrancedConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ConfigGuiHandler;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(Entranced.MOD_ID)
public final class EntrancedForge {

    public EntrancedForge() {
        init();

        if (FMLEnvironment.dist.isClient()) {
            clientInit();
        }
    }

    private static void init() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(Entranced.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        Entranced.init();
    }

    @OnlyIn(Dist.CLIENT)
    private static void clientInit() {
        Entranced.clientInit();
        registerConfigScreen();
    }

    // Register config screen.
    @OnlyIn(Dist.CLIENT)
    private static void registerConfigScreen() {
        ModLoadingContext.get().registerExtensionPoint(
                ConfigGuiHandler.ConfigGuiFactory.class,
                () -> new ConfigGuiHandler.ConfigGuiFactory(
                        (client, parent) -> AutoConfig.getConfigScreen(EntrancedConfig.class, parent).get()
                )
        );
    }
}
