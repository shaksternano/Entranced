package io.github.shaksternano.entranced.mixin.commonloader.commonside.enchantingtablefilter;

import io.github.shaksternano.entranced.commonside.access.enchantingtablefilter.ExtraEnchantingCatalystTypeArgument;
import io.github.shaksternano.entranced.commonside.config.EnchantingCatalystConfig;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@SuppressWarnings("unused")
@Mixin(ItemStack.class)
abstract class ItemStackMixin implements ExtraEnchantingCatalystTypeArgument {

    @Unique
    @Nullable
    private EnchantingCatalystConfig.EnchantingCatalystType entranced$catalystType;

    @Unique
    @Nullable
    @Override
    public EnchantingCatalystConfig.EnchantingCatalystType entranced$getArgument() {
        return entranced$catalystType;
    }

    @Unique
    @Override
    public void entranced$setArgument(@Nullable EnchantingCatalystConfig.EnchantingCatalystType catalystType) {
        entranced$catalystType = catalystType;
    }
}
