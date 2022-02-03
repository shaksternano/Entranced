package io.github.shaksternano.entranced.commonside;

import io.github.shaksternano.entranced.commonside.config.EntrancedConfig;
import io.github.shaksternano.entranced.commonside.registry.EntrancedEnchantments;
import io.github.shaksternano.entranced.commonside.registry.EntrancedEventHooks;
import io.github.shaksternano.entranced.commonside.registry.EntrancedNetworking;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.ActionResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum Entranced {

    INSTANCE;

    public static final String MOD_ID = "entranced";

    public static final Logger LOGGER = LoggerFactory.getLogger(StringUtils.capitalize(MOD_ID));

    private EntrancedConfig config;

    private boolean createdNewConfigFile = false;

    /**
     * Mod initialization run on both the client and the logical server.
     */
    public void init() {
        registerConfig();
        EntrancedNetworking.registerServerReceivers();
        EntrancedEventHooks.registerServerEventHooks();
        EntrancedEnchantments.registerEnchantments();
    }

    /**
     * Mod initialization run on only the client.
     */
    @Environment(EnvType.CLIENT)
    public void clientInit() {
        EntrancedNetworking.registerClientReceivers();
        EntrancedEventHooks.registerClientEventHooks();
    }

    /**
     * Registers the config class.
     */
    private void registerConfig() {
        ConfigHolder<EntrancedConfig> configHolder = AutoConfig.register(EntrancedConfig.class, JanksonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(EntrancedConfig.class).getConfig();

        if (createdNewConfigFile) {
            config.initCollectionDefaultValues(configHolder);
        }

        configHolder.registerSaveListener((holder, config) -> {
            EntrancedConfig.updateConfigCollections();
            return ActionResult.PASS;
        });
    }

    /**
     * @return An instance of the mod's config class.
     */
    public EntrancedConfig getConfig() {
        return config;
    }

    public void setCreatedNewConfigFile(boolean createdNewConfigFile) {
        this.createdNewConfigFile = createdNewConfigFile;
    }
}
