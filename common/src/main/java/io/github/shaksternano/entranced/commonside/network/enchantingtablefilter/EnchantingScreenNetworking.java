package io.github.shaksternano.entranced.commonside.network.enchantingtablefilter;

import dev.architectury.networking.NetworkManager;
import io.github.shaksternano.entranced.commonside.Entranced;
import io.github.shaksternano.entranced.commonside.access.enchantingtablefilter.EnchantmentScreenHandlerAccess;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.EnchantmentScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.Identifier;

public class EnchantingScreenNetworking {

    public static final Identifier APPLY_ENCHANTING_CATALYST = new Identifier(Entranced.MOD_ID, "apply_enchanting_catalyst");

    /**
     * Registers logical server receivers related to the enchanting catalyst.
     */
    public static void registerServerReceivers() {
        NetworkManager.registerReceiver(NetworkManager.clientToServer(), APPLY_ENCHANTING_CATALYST, (buf, context) -> {
            PlayerEntity player = context.getPlayer();

            context.queue(() -> {
                if (Entranced.getConfig().isEnchantingCatalystEnabled()) {
                    ScreenHandler screenHandler = player.currentScreenHandler;

                    if (screenHandler instanceof EnchantmentScreenHandler) {
                        ((EnchantmentScreenHandlerAccess) screenHandler).entranced$applyEnchantingCatalyst();
                    } else {
                        Entranced.LOGGER.warn("Received an apply enchanting catalyst packet when not using an enchanting table, this shouldn't happen!");
                    }
                }
            });
        });
    }
}
