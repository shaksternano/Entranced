package io.github.shaksternano.entranced.commonside.enchantment.forge;

import io.github.shaksternano.entranced.commonside.enchantment.MlgEnchantment;
import net.minecraft.item.FluidModificationItem;
import net.minecraft.item.ItemStack;

public class MlgEnchantmentImpl {

    public static MlgEnchantment newInstance() {
        return new MlgEnchantment() {

            @Override
            public boolean canApplyAtEnchantingTable(ItemStack stack) {
                return stack.getItem() instanceof FluidModificationItem;
            }
        };
    }
}
