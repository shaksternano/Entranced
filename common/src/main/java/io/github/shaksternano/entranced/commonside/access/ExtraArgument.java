package io.github.shaksternano.entranced.commonside.access;

import io.github.shaksternano.entranced.commonside.config.EnchantingCatalystSets;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

/**
 * Used to pass extra arguments to a method in a Mixin by attaching the argument to one
 * of the objects being passed that implements this interface.
 */
public interface ExtraArgument {

    @Nullable
    default ItemStack entranced$getItemStackArgument() {
        return null;
    }

    @Nullable
    default EnchantingCatalystSets.EnchantingCatalystType entranced$getCatalystType() {
        return null;
    }

    default void entranced$setCatalystType(EnchantingCatalystSets.EnchantingCatalystType catalystType) {}
}
