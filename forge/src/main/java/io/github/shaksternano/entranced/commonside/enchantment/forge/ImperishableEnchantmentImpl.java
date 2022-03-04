package io.github.shaksternano.entranced.commonside.enchantment.forge;

import io.github.shaksternano.entranced.commonside.config.ImperishableBlacklists;
import io.github.shaksternano.entranced.commonside.enchantment.ImperishableEnchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.item.ItemStack;

public class ImperishableEnchantmentImpl {

    /**
     * Creates a new {@link ImperishableEnchantment} instance with a custom {@link EnchantmentTarget}.
     * @return A new Imperishable enchantment instance with a custom enchantment target.
     */
    public static ImperishableEnchantment newInstance() {
        return new ImperishableEnchantment(EnchantmentTarget.VANISHABLE) {

            @Override
            public boolean canApplyAtEnchantingTable(ItemStack stack) {
                return !ImperishableBlacklists.isItemBlacklistedGlobally(stack);
            }
        };
    }
}
