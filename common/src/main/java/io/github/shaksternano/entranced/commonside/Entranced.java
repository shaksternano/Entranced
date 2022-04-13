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
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Entranced {

    public static final String MOD_ID = "entranced";
    public static final String MOD_NAME = "Entranced";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    private static EntrancedConfig config;

    private static boolean createdNewConfigFile = false;

    /**
     * Mod initialization run on both the logical client and logical server.
     */
    public static void init() {
        registerConfig();
        EntrancedNetworking.registerServerReceivers();
        EntrancedEventHooks.registerServerEventHooks();
        EntrancedEnchantments.registerEnchantments();
    }

    /**
     * Mod initialization run on only the logical client.
     */
    @Environment(EnvType.CLIENT)
    public static void clientInit() {
        EntrancedNetworking.registerClientReceivers();
        EntrancedEventHooks.registerClientEventHooks();
    }

    /**
     * Registers the config class.
     */
    private static void registerConfig() {
        ConfigHolder<EntrancedConfig> configHolder = AutoConfig.register(EntrancedConfig.class, JanksonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(EntrancedConfig.class).getConfig();

        if (createdNewConfigFile) {
            config.initDefaultConfigValues(configHolder);
        }

        configHolder.registerSaveListener((holder, config) -> {
            EntrancedConfig.updateConfig();
            return ActionResult.PASS;
        });
    }

    /**
     * Gets the mod's config.
     *
     * @return An instance of the mod's config class.
     */
    public static EntrancedConfig getConfig() {
        return config;
    }

    public static void setCreatedNewConfigFile(boolean createdNewConfigFile) {
        Entranced.createdNewConfigFile = createdNewConfigFile;
    }

    /**
     * Convience method for constructing an {@link Identifier} belonging to this mod.
     * @param path The path of the Identifier.
     * @return An Identifier with this mod's ID as the namespace and the specified path.
     */
    public static Identifier newIdentifier(String path) {
        return new Identifier(MOD_ID, path);
    }
}
