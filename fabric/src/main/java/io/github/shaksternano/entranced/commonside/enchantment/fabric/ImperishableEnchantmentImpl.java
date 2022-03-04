package io.github.shaksternano.entranced.commonside.enchantment.fabric;

import io.github.shaksternano.entranced.commonside.enchantment.ImperishableEnchantment;
import io.github.shaksternano.entranced.commonside.fabricasm.fabric.CustomEnum;
import io.github.shaksternano.entranced.commonside.fabricasm.fabric.EntrancedEnchantmentTarget;
import net.minecraft.enchantment.EnchantmentTarget;

public class ImperishableEnchantmentImpl {

    /**
     * Creates a new {@link ImperishableEnchantment} instance with a custom {@link EnchantmentTarget}.
     * @return A new Imperishable enchantment instance with a custom enchantment target.
     */
    public static ImperishableEnchantment newInstance() {
        return new ImperishableEnchantment(CustomEnum.getEnum(EnchantmentTarget.class, EntrancedEnchantmentTarget.IMPERISHABLE));
    }
}
