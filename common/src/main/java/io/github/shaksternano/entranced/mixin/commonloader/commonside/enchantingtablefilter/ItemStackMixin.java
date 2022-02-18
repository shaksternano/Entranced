package io.github.shaksternano.entranced.mixin.commonloader.commonside.enchantingtablefilter;

import io.github.shaksternano.entranced.commonside.access.enchantingtablefilter.ExtraEnchantingCatalystTypeArgument;
import io.github.shaksternano.entranced.commonside.config.EnchantingCatalystConfig;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.Optional;

/**
 * For letting
 * io.github.shaksternano.entranced.mixin.commonloader.commonside.enchantingtablefilter.EnchantmentHelperMixin#entranced$filterEnchantment
 * know if an enchanting catalyst is being used or not.
 */
@SuppressWarnings("unused")
@Mixin(ItemStack.class)
abstract class ItemStackMixin implements ExtraEnchantingCatalystTypeArgument {

    @Unique
    @Nullable
    private EnchantingCatalystConfig.EnchantingCatalystType entranced$catalystType;

    @Unique
    @Override
    public Optional<EnchantingCatalystConfig.EnchantingCatalystType> entranced$getArgument() {
        return Optional.ofNullable(entranced$catalystType);
    }

    @Unique
    @Override
    public void entranced$setArgument(@Nullable EnchantingCatalystConfig.EnchantingCatalystType catalystType) {
        entranced$catalystType = catalystType;
    }
}
