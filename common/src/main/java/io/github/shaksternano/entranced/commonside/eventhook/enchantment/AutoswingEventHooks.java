package io.github.shaksternano.entranced.commonside.eventhook.enchantment;

import dev.architectury.event.events.client.ClientTickEvent;
import io.github.shaksternano.entranced.commonside.enchantment.AutoswingEnchantment;
import io.github.shaksternano.entranced.commonside.registry.EntrancedEnchantments;
import io.github.shaksternano.entranced.commonside.util.EnchantmentUtil;
import io.github.shaksternano.entranced.mixin.commonloader.client.invoker.MinecraftClientInvoker;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.HitResult;

public class AutoswingEventHooks {

    /**
     * Registers client event hooks related to the {@link AutoswingEnchantment}.
     */
    @Environment(EnvType.CLIENT)
    public static void registerClientEventHooks() {
        /*
        The player will automatically attack when the next fully charged attack is
        available if the item in their main hand has the Autoswing enchantment.
         */
        ClientTickEvent.CLIENT_LEVEL_POST.register(clientWorld -> {
            MinecraftClient client = MinecraftClient.getInstance();
            PlayerEntity player = client.player;

            if (player != null) {
                if (client.options.keyAttack.isPressed()) {
                    HitResult crosshairTarget = client.crosshairTarget;

                    if (crosshairTarget != null) {
                        if (crosshairTarget.getType() != HitResult.Type.BLOCK) {
                            if (player.getAttackCooldownProgress(0.0F) == 1.0F) {
                                if (EnchantmentUtil.hasEnchantment(player.getMainHandStack(), EntrancedEnchantments.AUTOSWING)) {
                                    ((MinecraftClientInvoker) client).entranced$invokeDoAttack();
                                }
                            }
                        }
                    }
                }
            }
        });
    }
}
