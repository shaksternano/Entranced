package io.github.shaksternano.entranced.commonside.registry;

import io.github.shaksternano.entranced.commonside.event.enchantment.AutoswingEvents;
import io.github.shaksternano.entranced.commonside.event.enchantment.EnchantmentEvents;
import io.github.shaksternano.entranced.commonside.event.enchantment.ImperishableEvents;
import io.github.shaksternano.entranced.commonside.event.enchantment.MlgEvents;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public final class EntrancedEvents {

    private EntrancedEvents() {}

    // Registers logical server side events.
    public static void registerServerEvents() {
        EnchantmentEvents.registerServerEvents();
        ImperishableEvents.registerServerEvents();
    }

    // Registers client side events.
    @Environment(EnvType.CLIENT)
    public static void registerClientEvents() {
        AutoswingEvents.registerClientEvents();
        ImperishableEvents.registerClientEvents();
        MlgEvents.registerClientEvents();
    }
}
