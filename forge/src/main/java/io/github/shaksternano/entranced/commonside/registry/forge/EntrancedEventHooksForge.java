package io.github.shaksternano.entranced.commonside.registry.forge;

import io.github.shaksternano.entranced.commonside.eventhook.enchantment.forge.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;

public class EntrancedEventHooksForge {

    /**
     * Registers logical server side event hooks.
     */
    public static void registerServerEventHooks() {
        IEventBus eventBus = MinecraftForge.EVENT_BUS;

        eventBus.register(FrenzyEventHooksForge.class);
        eventBus.register(ImperishableEventHooksForge.class);
    }
}
