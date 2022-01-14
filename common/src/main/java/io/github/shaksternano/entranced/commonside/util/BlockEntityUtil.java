package io.github.shaksternano.entranced.commonside.util;

import io.github.shaksternano.entranced.commonside.access.EnchantmentHolder;
import io.github.shaksternano.entranced.mixin.commonloader.commonside.accessor.ItemStackAccessor;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtElement;

public final class BlockEntityUtil {

    private BlockEntityUtil() {}

    // Copies the enchantments and repair cost of an ItemStack to a BlockEntity.
    @SuppressWarnings("ConstantConditions")
    public static void setBlockEntityEnchantments(BlockEntity blockEntity, ItemStack stack) {
        EnchantmentHolder enchantmentHolder = (EnchantmentHolder) blockEntity;

        if (stack.hasEnchantments()) {
            enchantmentHolder.entranced$setEnchantments(stack.getEnchantments());
        }

        if (stack.hasNbt()) {
            if (stack.getNbt().contains(ItemStackAccessor.entranced$getRepairCostKey(), 3)) {
                enchantmentHolder.entranced$setRepairCost(stack.getRepairCost());
            }
        }
    }

    // Copies the enchantments and repair cost of a BlockEntity to an ItemStack.
    public static void setDroppedItemStackEnchantments(BlockEntity blockEntity, ItemStack stack) {
        EnchantmentHolder enchantmentHolder = (EnchantmentHolder) blockEntity;

        NbtElement enchantmentsNbt = enchantmentHolder.entranced$getEnchantments();
        if (enchantmentsNbt != null) {
            stack.setSubNbt(ItemStack.ENCHANTMENTS_KEY, enchantmentsNbt);
        }

        Integer repairCost = enchantmentHolder.entranced$getRepairCost();
        if (repairCost != null) {
            stack.setRepairCost(repairCost);
        }
    }
}
