package io.github.shaksternano.entranced.commonside.eventhook.enchantingtablefilter;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.architectury.event.events.client.ClientGuiEvent;
import dev.architectury.networking.NetworkManager;
import io.github.shaksternano.entranced.commonside.Entranced;
import io.github.shaksternano.entranced.commonside.network.enchantingtablefilter.EnchantingScreenNetworking;
import io.github.shaksternano.entranced.commonside.registry.EntrancedNetworking;
import io.github.shaksternano.entranced.mixin.commonloader.client.accessor.HandledScreenAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.EnchantmentScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class EnchantingScreenEventHooks {

    private static final Identifier ENCHANTING_CATALYST_GUI_TEXTURE = new Identifier(Entranced.MOD_ID, "textures/gui/container/test.png");

    /**
     * Registers client event hooks related to the enchanting catalyst.
     */
    @Environment(EnvType.CLIENT)
    public static void registerClientEventHooks() {
        ClientGuiEvent.INIT_POST.register((screen, access) -> {
            if (Entranced.getConfig().isEnchantingCatalystEnabled()) {
                if (screen instanceof EnchantmentScreen) {
                    access.addRenderableWidget(new ButtonWidget(
                            10,
                            50,
                            150,
                            20,
                            new TranslatableText("container.enchant.entranced.applyCatalyst"),
                            button -> NetworkManager.sendToServer(
                                    EnchantingScreenNetworking.APPLY_ENCHANTING_CATALYST,
                                    EntrancedNetworking.createPacketByteBuf()
                            )
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
