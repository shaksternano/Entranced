package io.github.shaksternano.entranced.commonside.access.enchantment.infinity.bucket;

import net.minecraft.item.ItemStack;

import java.util.Optional;

/**
 * Used to pass an extra {@link ItemStack} argument to a method in a Mixin by attaching the argument to one
 * of the objects being passed that implements this interface.
 */
public interface ExtraItemStackArgument {

    Optional<ItemStack> entranced$getArgument();
}
