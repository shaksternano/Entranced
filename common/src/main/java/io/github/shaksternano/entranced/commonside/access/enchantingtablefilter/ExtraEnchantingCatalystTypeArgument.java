package io.github.shaksternano.entranced.commonside.access.enchantingtablefilter;

import io.github.shaksternano.entranced.commonside.config.EnchantingCatalystConfig;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public interface ExtraEnchantingCatalystTypeArgument {

    Optional<EnchantingCatalystConfig.EnchantingCatalystType> entranced$getArgument();

    void entranced$setArgument(@Nullable EnchantingCatalystConfig.EnchantingCatalystType catalystType);
}
