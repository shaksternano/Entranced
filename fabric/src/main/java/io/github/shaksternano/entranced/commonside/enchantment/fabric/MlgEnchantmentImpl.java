package io.github.shaksternano.entranced.commonside.enchantment.fabric;

import io.github.shaksternano.entranced.commonside.enchantment.MlgEnchantment;
import io.github.shaksternano.entranced.commonside.fabricasm.fabric.CustomEnum;
import io.github.shaksternano.entranced.commonside.fabricasm.fabric.EntrancedEnchantmentTarget;
import net.minecraft.enchantment.EnchantmentTarget;

public class MlgEnchantmentImpl {

    /**
     * Creates a new {@link MlgEnchantment} instance with a custom {@link EnchantmentTarget}.
     * @return A new Imperishable enchantment instance with a custom enchantment target.
     */
    public static MlgEnchantment newInstance() {
        return new MlgEnchantment(CustomEnum.getEnum(EnchantmentTarget.class, EntrancedEnchantmentTarget.FLUID_CONTAINER));
    }
}
