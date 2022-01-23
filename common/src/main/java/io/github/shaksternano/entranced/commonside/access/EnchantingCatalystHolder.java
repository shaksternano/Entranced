package io.github.shaksternano.entranced.commonside.access;

import net.minecraft.item.Item;
import org.jetbrains.annotations.Nullable;

public interface EnchantingCatalystHolder {

    @Nullable
    Item entranced$getEnchantingCatalyst();

    void entranced$setEnchantingCatalyst(Item enchantingCatalyst);
}
