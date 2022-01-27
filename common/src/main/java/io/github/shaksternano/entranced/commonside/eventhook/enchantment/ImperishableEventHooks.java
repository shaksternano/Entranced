package io.github.shaksternano.entranced.commonside.eventhook.enchantment;

import dev.architectury.event.CompoundEventResult;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientTooltipEvent;
import dev.architectury.event.events.common.InteractionEvent;
import io.github.shaksternano.entranced.commonside.config.ImperishableBlacklists;
import io.github.shaksternano.entranced.commonside.enchantment.ImperishableEnchantment;
import io.github.shaksternano.entranced.commonside.registry.EntrancedEnchantments;
import io.github.shaksternano.entranced.commonside.util.EnchantmentUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Wearable;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

public final class ImperishableEventHooks {

    private ImperishableEventHooks() {}

    /**
     * Registers logical server event hooks related to the {@link ImperishableEnchantment}.
     */
    public static void registerServerEventHooks() {
        // Item specific right click actions are cancelled if the item has Imperishable and is at 0 durability.
        InteractionEvent.RIGHT_CLICK_ITEM.register((player, hand) -> {
            ItemStack stack = player.getStackInHand(hand);

            if (ImperishableBlacklists.INSTANCE.isItemProtected(stack, ImperishableBlacklists.ProtectionType.BREAK_PROTECTION)) {
                if (!player.isCreative() && !player.isSpectator()) {
                    // Still allow a wearable item to be equipped even if the item is broken.
                    if (!(stack.getItem() instanceof Wearable)) {
                        if (EnchantmentUtil.isBrokenImperishable(stack)) {
                            return CompoundEventResult.interruptFalse(stack);
                        }
                    }
                }
            }

            return CompoundEventResult.pass();
        });

        // Item specific right click block actions are cancelled if the item has Imperishable and is at 0 durability.
        InteractionEvent.RIGHT_CLICK_BLOCK.register((player, hand, pos, face) -> imperishableCancelInteract(player, player.getStackInHand(hand)));

        // Item specific right click entity are cancelled if the item has Imperishable and is at 0 durability.
        InteractionEvent.INTERACT_ENTITY.register((player, entity, hand) -> imperishableCancelInteract(player, player.getStackInHand(hand)));
    }

    /**
     * Registers client event hooks related to the {@link ImperishableEnchantment}.
     */
    @Environment(EnvType.CLIENT)
    public static void registerClientEventHooks() {
        // Adds a message to the tooltip of an item with Imperishable at 0 durability.
        ClientTooltipEvent.ITEM.register((stack, lines, context) -> {
            if (ImperishableBlacklists.INSTANCE.isItemProtected(stack, ImperishableBlacklists.ProtectionType.BREAK_PROTECTION)) {
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

                    // When !context.isAdvanced()
                    if (!inserted) {
                        lines.add(LiteralText.EMPTY);
                        lines.add(new TranslatableText("item.tooltip." + EntrancedEnchantments.IMPERISHABLE.getTranslationKey() + ".broken").formatted(Formatting.RED));
                    }
                }
            }
        });
    }

    /**
     * Item specific interactions are cancelled if the item has the {@link ImperishableEnchantment} and is at 0 durability.
     */
    private static EventResult imperishableCancelInteract(PlayerEntity player, ItemStack stack) {
        if (ImperishableBlacklists.INSTANCE.isItemProtected(stack, ImperishableBlacklists.ProtectionType.BREAK_PROTECTION)) {
            if (!player.isCreative() && !player.isSpectator()) {
                if (EnchantmentUtil.isBrokenImperishable(stack)) {
                    return EventResult.interruptFalse();
                }
            }
        }

        return EventResult.pass();
    }
}
