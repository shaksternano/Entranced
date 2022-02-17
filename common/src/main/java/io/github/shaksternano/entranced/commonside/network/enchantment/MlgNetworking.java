package io.github.shaksternano.entranced.commonside.network.enchantment;

import dev.architectury.networking.NetworkManager;
import io.github.shaksternano.entranced.commonside.Entranced;
import io.github.shaksternano.entranced.commonside.enchantment.MlgEnchantment;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.Identifier;

public class MlgNetworking {

    public static final Identifier CLIENT_HOTBAR_SWAP = new Identifier(Entranced.MOD_ID, "client_hotbar_swap");

    /**
     * Registers client receivers related to the {@link MlgEnchantment}.
     */
    @Environment(EnvType.CLIENT)
    public static void registerClientReceivers() {
        // Sets the client's hotbar slot.
        NetworkManager.registerReceiver(NetworkManager.serverToClient(), CLIENT_HOTBAR_SWAP, (buf, context) -> {
            int hotbarSlot = buf.readInt();

            context.queue(() -> {
                PlayerEntity player = MinecraftClient.getInstance().player;

                if (player != null) {
                    PlayerInventory inventory = player.getInventory();

                    if (PlayerInventory.isValidHotbarIndex(hotbarSlot)) {
                        inventory.selectedSlot = hotbarSlot;
                    } else {
                        Entranced.LOGGER.warn(hotbarSlot + " is an invalid hotbar slot, this shouldn't happen!");
                    }
                }
            });
        });
    }
}
