package io.github.shaksternano.entranced.commonside.access.enchantingtablefilter;

import net.minecraft.item.ItemStack;

public interface EnchantmentScreenHandlerAccess {

    void entranced$setCatalystInventoryIndex(int slotIndex);

    void entranced$setEnchantingCatalyst();

    boolean entranced$isCatalystSlot(boolean isLapis, int index);

    boolean entranced$moveOutOfCatalystSlot(boolean isLapis, ItemStack selectedSlotStack, int index);
}
