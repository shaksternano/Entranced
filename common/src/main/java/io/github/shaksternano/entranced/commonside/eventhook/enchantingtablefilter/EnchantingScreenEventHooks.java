package io.github.shaksternano.entranced.commonside.eventhook.enchantingtablefilter;

import dev.architectury.event.events.client.ClientGuiEvent;
import dev.architectury.networking.NetworkManager;
import io.github.shaksternano.entranced.client.gui.widget.EnchantingCatalystButton;
import io.github.shaksternano.entranced.commonside.Entranced;
import io.github.shaksternano.entranced.commonside.gui.EnchantingCatalystPanel;
import io.github.shaksternano.entranced.commonside.network.enchantingtablefilter.EnchantingScreenNetworking;
import io.github.shaksternano.entranced.commonside.registry.EntrancedNetworking;
import io.github.shaksternano.entranced.mixin.commonloader.client.accessor.HandledScreenAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.EnchantmentScreen;
import net.minecraft.item.Items;
import net.minecraft.text.TranslatableText;

public class EnchantingScreenEventHooks {

    /**
     * Registers client event hooks related to the enchanting catalyst.
     */
    @Environment(EnvType.CLIENT)
    public static void registerClientEventHooks() {
        ClientGuiEvent.INIT_POST.register((screen, access) -> {
            if (Entranced.getConfig().isEnchantingCatalystEnabled()) {
                if (screen instanceof EnchantmentScreen) {
                    HandledScreenAccessor handledScreenAccessor = (HandledScreenAccessor) screen;

                    access.addRenderableWidget(new EnchantingCatalystButton(
                            EnchantingCatalystPanel.INSTANCE.getButtonX(handledScreenAccessor.entranced$getX()),
                            EnchantingCatalystPanel.INSTANCE.getButtonY(handledScreenAccessor.entranced$getY()),
                            -1,
                            -1,
                            null,
                            button -> NetworkManager.sendToServer(
                                    EnchantingScreenNetworking.APPLY_ENCHANTING_CATALYST,
                                    EntrancedNetworking.createPacketByteBuf()
                            ),
                            screen,
                            Items.BLAZE_POWDER,
                            new TranslatableText("gui.screen.enchant.tooltip.entranced.applyCatalyst")
                    ));
                }
            }
        });

        ClientGuiEvent.RENDER_POST.register((screen, matrices, mouseX, mouseY, delta) -> {
            if (Entranced.getConfig().isEnchantingCatalystEnabled()) {
                if (screen instanceof EnchantmentScreen) {
                    HandledScreenAccessor screenAccessor = (HandledScreenAccessor) screen;

                    int backgroundWidth = screenAccessor.entranced$getBackgroundWidth();
                    int backgroundHeight = screenAccessor.entranced$getBackgroundHeight();

                    int textureX = (screen.width - backgroundWidth) / 2;
                    int textureY = (screen.height - backgroundHeight) / 2;
                    //RenderSystem.setShaderTexture(0, ENCHANTING_CATALYST_GUI_TEXTURE);
                    //screen.drawTexture(matrices, textureX - 100, textureY, 0, 0, backgroundWidth, backgroundHeight);
                }
            }
        });
    }
}
