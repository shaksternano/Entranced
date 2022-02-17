package io.github.shaksternano.entranced.commonside.registry;

import io.github.shaksternano.entranced.commonside.eventhook.enchantingtablefilter.EnchantingScreenEventHooks;
import io.github.shaksternano.entranced.commonside.eventhook.enchantment.AutoswingEventHooks;
import io.github.shaksternano.entranced.commonside.eventhook.enchantment.EnchantmentEventHooks;
import io.github.shaksternano.entranced.commonside.eventhook.enchantment.ImperishableEventHooks;
import io.github.shaksternano.entranced.commonside.eventhook.enchantment.MlgEventHooks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class EntrancedEventHooks {

    /**
     * Registers logical server side event hooks.
     */
    public static void registerServerEventHooks() {
        EnchantmentEventHooks.registerServerEventHooks();
        ImperishableEventHooks.registerServerEventHooks();
        MlgEventHooks.registerServerEventHooks();
    }

    /**
     * Registers client side event hooks.
     */
    @Environment(EnvType.CLIENT)
    public static void registerClientEventHooks() {
        AutoswingEventHooks.registerClientEventHooks();
        ImperishableEventHooks.registerClientEventHooks();
        EnchantingScreenEventHooks.registerClientEventHooks();
    }
}
