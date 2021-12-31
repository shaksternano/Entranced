package io.github.shaksternano.entranced.commonside.event.enchantment;

import dev.architectury.event.CompoundEventResult;
import dev.architectury.event.events.client.ClientTooltipEvent;
import dev.architectury.event.events.common.InteractionEvent;
import io.github.shaksternano.entranced.commonside.config.ImperishableBlacklists;
import io.github.shaksternano.entranced.commonside.registry.EntrancedEnchantments;
import io.github.shaksternano.entranced.commonside.util.EnchantmentUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Wearable;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

public final class ImperishableEvents {

    private ImperishableEvents() {}

    public static void registerServerEvents() {
        // Item specific right click actions are cancelled if the item has Imperishable and is at 0 durability.
        InteractionEvent.RIGHT_CLICK_ITEM.register((player, hand) -> {
            ItemStack stack = player.getStackInHand(hand);
            if (ImperishableBlacklists.isItemProtected(stack, ImperishableBlacklists.ProtectionType.BREAK_PROTECTION)) {
                if (!player.isCreative() && !player.isSpectator()) {
                    // Still allow a wearable item to be equipped even if the item is broken.
                    if (!(stack.getItem() instanceof Wearable)) {
                        if (EnchantmentUtil.isBrokenImperishable(stack)) {
                            return CompoundEventResult.interruptTrue(stack);
                        }
                    }
                }
            }

            return CompoundEventResult.pass();
        });
    }

    @Environment(EnvType.CLIENT)
    public static void registerClientEvents() {
        // Adds a message to the tooltip of an item with Imperishable at 0 durability.
        ClientTooltipEvent.ITEM.register((stack, lines, context) -> {
            if (ImperishableBlacklists.isItemProtected(stack, ImperishableBlacklists.ProtectionType.BREAK_PROTECTION)) {
                if (EnchantmentUtil.isBrokenImperishable(stack)) {
                    boolean inserted = false;

                    if (context.isAdvanced()) {
                        int index = 0;
                        while (index < lines.size() && !inserted) {
                            Text line = lines.get(index);
                            if (line instanceof TranslatableText translatableLine) {
                                if (translatableLine.getKey().equals("item.durability")) {
                                    lines.add(index, new TranslatableText("item.tooltip." + EntrancedEnchantments.IMPERISHABLE.getTranslationKey() + ".broken").formatted(Formatting.RED));
                                    lines.add(index, LiteralText.EMPTY);
                                    inserted = true;
                                }
                            }

                            index++;
                        }
                    }

                    if (!inserted) {
                        lines.add(LiteralText.EMPTY);
                        lines.add(new TranslatableText("item.tooltip." + EntrancedEnchantments.IMPERISHABLE.getTranslationKey() + ".broken").formatted(Formatting.RED));
                    }
                }
            }
        });
    }
}
