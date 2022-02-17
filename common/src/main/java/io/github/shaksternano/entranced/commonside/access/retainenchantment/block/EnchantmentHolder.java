package io.github.shaksternano.entranced.commonside.access.retainenchantment.block;

import net.minecraft.nbt.NbtElement;

import java.util.Optional;

public interface EnchantmentHolder {

    Optional<NbtElement> entranced$getEnchantments();

    void entranced$setEnchantments(NbtElement enchantments);

    Optional<Integer> entranced$getRepairCost();

    void entranced$setRepairCost(Integer repairCost);
}
