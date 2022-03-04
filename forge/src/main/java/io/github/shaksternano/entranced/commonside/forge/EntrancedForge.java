package io.github.shaksternano.entranced.commonside.forge;

import dev.architectury.platform.forge.EventBuses;
import io.github.shaksternano.entranced.commonside.Entranced;
import io.github.shaksternano.entranced.commonside.config.EntrancedConfig;
import io.github.shaksternano.entranced.commonside.registry.forge.EntrancedEventHooksForge;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ConfigGuiHandler;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(Entranced.MOD_ID)
public class EntrancedForge {

    public EntrancedForge() {
        init();

        if (FMLEnvironment.dist.isClient()) {
            clientInit();
        }
    }

    private void init() {
        EventBuses.registerModEventBus(Entranced.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        EntrancedEventHooksForge.registerServerEventHooks();
        Entranced.init();
    }

    @OnlyIn(Dist.CLIENT)
    private void clientInit() {
        Entranced.clientInit();
        registerConfigScreen();
    }

    /**
     * Adds a config screen in the mod menu.
     */
    @OnlyIn(Dist.CLIENT)
    private void registerConfigScreen() {
        ModLoadingContext.get().registerExtensionPoint(
                ConfigGuiHandler.ConfigGuiFactory.class,
                () -> new ConfigGuiHandler.ConfigGuiFactory(
                        (client, parent) -> AutoConfig.getConfigScreen(EntrancedConfig.class, parent).get()
                )
        );
    }
}
