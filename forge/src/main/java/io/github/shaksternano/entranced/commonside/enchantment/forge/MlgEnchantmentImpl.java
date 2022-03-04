package io.github.shaksternano.entranced.commonside.enchantment.forge;

import io.github.shaksternano.entranced.commonside.enchantment.MlgEnchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.item.FluidModificationItem;
import net.minecraft.item.ItemStack;

public class MlgEnchantmentImpl {

    /**
     * Creates a new {@link MlgEnchantment} instance with a custom {@link EnchantmentTarget}.
     * @return A new Imperishable enchantment instance with a custom enchantment target.
     */
    public static MlgEnchantment newInstance() {
        return new MlgEnchantment(EnchantmentTarget.VANISHABLE) {

            @Override
            public boolean canApplyAtEnchantingTable(ItemStack stack) {
                return stack.getItem() instanceof FluidModificationItem;
            }
        };
    }
}
