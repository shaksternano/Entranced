package io.github.shaksternano.entranced.commonside.registry;

import io.github.shaksternano.entranced.commonside.eventhook.enchantingtablefilter.EnchantingScreenEvents;
import io.github.shaksternano.entranced.commonside.eventhook.enchantment.AutoswingEvents;
import io.github.shaksternano.entranced.commonside.eventhook.enchantment.EnchantmentEvents;
import io.github.shaksternano.entranced.commonside.eventhook.enchantment.ImperishableEvents;
import io.github.shaksternano.entranced.commonside.eventhook.enchantment.MlgEvents;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public final class EntrancedEventHooks {

    private EntrancedEventHooks() {}

    // Registers logical server side event hooks.
    public static void registerServerEventHooks() {
        EnchantmentEvents.registerServerEventHooks();
        ImperishableEvents.registerServerEventHooks();
        MlgEvents.registerServerEventHooks();
    }

    // Registers client side event hooks.
    @Environment(EnvType.CLIENT)
    public static void registerClientEventHooks() {
        AutoswingEvents.registerClientEventHooks();
        ImperishableEvents.registerClientEventHooks();
        EnchantingScreenEvents.registerClientEventHooks();
    }
}
