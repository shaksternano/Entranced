package io.github.shaksternano.entranced.commonside.access.enchantingtablefilter;

import io.github.shaksternano.entranced.commonside.config.EnchantingCatalystConfig;
import org.jetbrains.annotations.Nullable;

public interface EnchantingCatalystTypeHolder {

    @Nullable
    EnchantingCatalystConfig.EnchantingCatalystType entranced$getEnchantingCatalystType();

    void entranced$setEnchantingCatalystType(@Nullable EnchantingCatalystConfig.EnchantingCatalystType catalystType);
}
