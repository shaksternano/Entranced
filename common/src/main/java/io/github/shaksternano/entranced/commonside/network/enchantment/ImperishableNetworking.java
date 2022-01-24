package io.github.shaksternano.entranced.commonside.network.enchantment;

import dev.architectury.networking.NetworkManager;
import io.github.shaksternano.entranced.commonside.Entranced;
import io.github.shaksternano.entranced.commonside.config.ImperishableBlacklists;
import io.github.shaksternano.entranced.mixin.commonloader.commonside.invoker.LivingEntityInvoker;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public final class ImperishableNetworking {

    private ImperishableNetworking() {}

    public static final Identifier EQUIPMENT_BREAK_EFFECTS = new Identifier(Entranced.MOD_ID, "equipment_break_effects");

    /**
     * Registers client receivers related to the {@link io.github.shaksternano.entranced.commonside.enchantment.ImperishableEnchantment}.
     */
    @Environment(EnvType.CLIENT)
    public static void registerClientReceivers() {
        // Plays item break effects when the durability of an item reaches 0.
        NetworkManager.registerReceiver(NetworkManager.serverToClient(), EQUIPMENT_BREAK_EFFECTS, (buf, context) -> {
            int itemId = buf.readInt();

            context.queue(() -> {
                PlayerEntity player = context.getPlayer();

                if (player != null) {
                    Item item = Item.byRawId(itemId);

                    if (ImperishableBlacklists.isItemProtected(item, ImperishableBlacklists.ProtectionType.BREAK_PROTECTION)) {
                        ((LivingEntityInvoker) player).entranced$invokePlayEquipmentBreakEffects(new ItemStack(item));
                    }
                }
            });
        });
    }
}
