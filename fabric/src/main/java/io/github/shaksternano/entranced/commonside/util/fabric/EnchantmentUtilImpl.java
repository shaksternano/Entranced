package io.github.shaksternano.entranced.commonside.util.fabric;

import net.minecraft.item.ItemStack;

public class EnchantmentUtilImpl {

    public static int getItemEnchantability(ItemStack stack) {
        return stack.getItem().getEnchantability();
    }
}
