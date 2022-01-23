package io.github.shaksternano.entranced.commonside.eventhook.enchantingtablefilter;

import dev.architectury.event.events.client.ClientGuiEvent;
import dev.architectury.networking.NetworkManager;
import io.github.shaksternano.entranced.commonside.network.enchantingtablefilter.EnchantingScreenNetworking;
import io.github.shaksternano.entranced.commonside.registry.EntrancedNetworking;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.EnchantmentScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.TranslatableText;

public final class EnchantingScreenEvents {

    private EnchantingScreenEvents() {}

    @Environment(EnvType.CLIENT)
    public static void registerClientEventHooks() {
        ClientGuiEvent.INIT_POST.register((screen, access) -> {
            if (screen instanceof EnchantmentScreen) {
                access.addRenderableWidget(new ButtonWidget(
                        10,
                        50,
                        150,
                        20,
                        new TranslatableText("container.enchant.entranced.applyCatalyst"),
                        button -> NetworkManager.sendToServer(EnchantingScreenNetworking.APPLY_ENCHANTING_CATALYST, EntrancedNetworking.createPacketByteBuf())
                ));
            }
        });
    }
}
