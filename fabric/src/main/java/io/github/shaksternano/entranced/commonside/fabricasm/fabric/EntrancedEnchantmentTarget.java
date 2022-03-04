package io.github.shaksternano.entranced.commonside.fabricasm.fabric;

import net.minecraft.enchantment.EnchantmentTarget;

/**
 * Custom {@link EnchantmentTarget}s.
 */
public enum EntrancedEnchantmentTarget implements CustomEnum {

    IMPERISHABLE("ENTRANCED$IMPERISHABLE"),
    FLUID_CONTAINER("ENTRANCED$FLUID_CONTAINER");

    private final String NAME;

    EntrancedEnchantmentTarget(String name) {
        NAME = name;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
