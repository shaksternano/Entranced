package io.github.shaksternano.entranced.commonside.registry;

import io.github.shaksternano.entranced.commonside.network.debug.DebugNetworking;
import io.github.shaksternano.entranced.commonside.network.enchantingtablefilter.EnchantingScreenNetworking;
import io.github.shaksternano.entranced.commonside.network.enchantment.ImperishableNetworking;
import io.github.shaksternano.entranced.commonside.network.enchantment.MlgNetworking;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.PacketByteBuf;

public class EntrancedNetworking {

    /**
     * Registers logical server side receivers.
     */
    public static void registerServerReceivers() {
        DebugNetworking.registerServerReceivers();
        EnchantingScreenNetworking.registerServerReceivers();
    }

    /**
     * Registers client side receivers.
     */
    @Environment(EnvType.CLIENT)
    public static void registerClientReceivers() {
        ImperishableNetworking.registerClientReceivers();
        MlgNetworking.registerClientReceivers();
    }

    /**
     * Convenience method for creating a new {@link PacketByteBuf}.
     *
     * @return A new {@link PacketByteBuf} instance.
     */
    public static PacketByteBuf createPacketByteBuf() {
        return new PacketByteBuf(Unpooled.buffer());
    }
}
