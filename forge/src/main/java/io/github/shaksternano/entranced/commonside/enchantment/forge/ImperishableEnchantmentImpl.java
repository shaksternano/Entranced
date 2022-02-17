package io.github.shaksternano.entranced.commonside.enchantment.forge;

import io.github.shaksternano.entranced.commonside.config.ImperishableBlacklists;
import io.github.shaksternano.entranced.commonside.enchantment.ImperishableEnchantment;
import net.minecraft.item.ItemStack;

public class ImperishableEnchantmentImpl {

    public static ImperishableEnchantment newInstance() {
        return new ImperishableEnchantment() {

            @Override
            public boolean canApplyAtEnchantingTable(ItemStack stack) {
                return !ImperishableBlacklists.INSTANCE.isItemBlacklistedGlobally(stack);
            }
        };
    }
}
