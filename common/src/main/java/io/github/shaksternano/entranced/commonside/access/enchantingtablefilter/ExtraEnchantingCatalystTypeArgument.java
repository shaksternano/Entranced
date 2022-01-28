package io.github.shaksternano.entranced.commonside.access.enchantingtablefilter;

import io.github.shaksternano.entranced.commonside.config.EnchantingCatalystConfig;
import org.jetbrains.annotations.Nullable;

public interface ExtraEnchantingCatalystTypeArgument {

    @Nullable
    EnchantingCatalystConfig.EnchantingCatalystType entranced$getArgument();

    void entranced$setArgument(@Nullable EnchantingCatalystConfig.EnchantingCatalystType catalystType);
}
