package io.github.shaksternano.entranced.mixin.commonloader.commonside.enchantingtablefilter;

import io.github.shaksternano.entranced.commonside.access.ExtraArgument;
import io.github.shaksternano.entranced.commonside.config.EnchantingCatalystSets;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@SuppressWarnings("unused")
@Mixin(ItemStack.class)
abstract class ItemStackMixin implements ExtraArgument {

    @Unique
    @Nullable
    private EnchantingCatalystSets.EnchantingCatalystType entranced$catalystType;

    @Unique
    @Nullable
    @Override
    public EnchantingCatalystSets.EnchantingCatalystType entranced$getCatalystType() {
        return entranced$catalystType;
    }

    @Unique
    @Override
    public void entranced$setCatalystType(EnchantingCatalystSets.EnchantingCatalystType catalystType) {
        entranced$catalystType = catalystType;
    }
}
