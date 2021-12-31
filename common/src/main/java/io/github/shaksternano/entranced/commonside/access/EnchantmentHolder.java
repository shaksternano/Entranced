package io.github.shaksternano.entranced.commonside.access;

import net.minecraft.nbt.NbtElement;

public interface EnchantmentHolder {

    NbtElement entranced$getEnchantments();

    void entranced$setEnchantments(NbtElement enchantments);

    Integer entranced$getRepairCost();

    void entranced$setRepairCost(Integer repairCost);
}
