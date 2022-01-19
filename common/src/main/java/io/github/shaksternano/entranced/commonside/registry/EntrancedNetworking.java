package io.github.shaksternano.entranced.commonside.registry;

import io.github.shaksternano.entranced.commonside.network.debug.DebugNetworking;
import io.github.shaksternano.entranced.commonside.network.enchantment.ImperishableNetworking;
import io.github.shaksternano.entranced.commonside.network.enchantment.MlgNetworking;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public final class EntrancedNetworking {

    private EntrancedNetworking() {}

    // Registers logical server side receivers.
    public static void registerServerReceivers() {
        DebugNetworking.registerServerReceivers();

    }

    // Registers client side receivers.
    @Environment(EnvType.CLIENT)
    public static void registerClientReceivers() {
        ImperishableNetworking.registerClientReceivers();
        MlgNetworking.registerClientReceivers();
    }
}
