package io.github.shaksternano.entranced.commonside.registry;

import io.github.shaksternano.entranced.commonside.network.debug.DebugNetworking;
import io.github.shaksternano.entranced.commonside.network.enchantment.ImperishableNetworking;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public final class EntrancedNetworking {

    private EntrancedNetworking() {}

    public static void registerServerReceivers() {
        DebugNetworking.registerServerReceivers();

    }

    @Environment(EnvType.CLIENT)
    public static void registerClientReceivers() {
        ImperishableNetworking.registerClientReceivers();
    }
}
