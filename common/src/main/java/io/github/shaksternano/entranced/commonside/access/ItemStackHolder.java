package io.github.shaksternano.entranced.commonside.access;

import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface ItemStackHolder {

    @Nullable
    ItemStack entranced$getItemStack();
}
