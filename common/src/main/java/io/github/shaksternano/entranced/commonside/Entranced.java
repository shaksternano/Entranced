package io.github.shaksternano.entranced.commonside;

import io.github.shaksternano.entranced.commonside.config.EnchantmentAllowLists;
import io.github.shaksternano.entranced.commonside.config.EntrancedConfig;
import io.github.shaksternano.entranced.commonside.registry.EntrancedEnchantments;
import io.github.shaksternano.entranced.commonside.registry.EntrancedEvents;
import io.github.shaksternano.entranced.commonside.registry.EntrancedNetworking;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.ActionResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Entranced {

    private Entranced() {}

    public static final String MOD_ID = "entranced";
    public static final String MOD_NAME = "Entranced";

    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

    private static EntrancedConfig config;
    
    public static void init() {
        registerConfig();
        EntrancedNetworking.registerServerReceivers();
        EntrancedEvents.registerServerEvents();
        EntrancedEnchantments.registerEnchantments();
    }

    @Environment(EnvType.CLIENT)
    public static void clientInit() {
        EntrancedNetworking.registerClientReceivers();
        EntrancedEvents.registerClientEvents();
    }

    private static void registerConfig() {
        AutoConfig.register(EntrancedConfig.class, JanksonConfigSerializer::new).registerSaveListener((configHolder, modConfig) -> {
            EnchantmentAllowLists.initAllowLists();
            return ActionResult.PASS;
        });
        config = AutoConfig.getConfigHolder(EntrancedConfig.class).getConfig();
    }

    public static EntrancedConfig getConfig() {
        return config;
    }
}
