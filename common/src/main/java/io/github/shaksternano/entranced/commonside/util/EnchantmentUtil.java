package io.github.shaksternano.entranced.commonside.util;

import io.github.shaksternano.entranced.commonside.Entranced;
import io.github.shaksternano.entranced.commonside.registry.EntrancedEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtElement;

public final class EnchantmentUtil {

    private EnchantmentUtil() {}

    // Returns true if an ItemStack has the enchantment. Returns false otherwise.
    public static boolean hasEnchantment(ItemStack stack, Enchantment enchantment) {
        return EnchantmentHelper.getLevel(enchantment, stack) != 0;
    }

    // Returns true if an ItemStack is a bucket, has the Infinity enchantment, and the Infinity enchantment is allowed on buckets. Returns false otherwise.
    public static boolean isBucketAndHasInfinityAndBucketEnabled(ItemStack stack) {
        return Entranced.getConfig().isInfinityAllowedOnBuckets() && BucketUtil.isBucket(stack.getItem()) && EnchantmentUtil.hasEnchantment(stack, Enchantments.INFINITY);
    }

    // Returns true if an ItemStack is damageable, has the Imperishable enchantment, the Imperishable enchantment is enabled, and the damage on it is more than the item's maximum damage. Returns false otherwise.
    public static boolean isBrokenImperishable(ItemStack stack) {
        return stack.isDamageable() && stack.getDamage() >= stack.getMaxDamage() && (hasEnchantment(stack, EntrancedEnchantments.IMPERISHABLE) || Entranced.getConfig().isEnchantmentNotNeededToPreventBreaking());
    }

    // Copies the enchantments from one ItemStack to another.
    public static void copyEnchantments(ItemStack stackToCopyFrom, ItemStack stackToCopyTo) {
        if (Entranced.getConfig().isRetainEnchantmentsMoreOften()) {
            if (stackToCopyFrom.hasEnchantments()) {
                if (!stackToCopyTo.hasEnchantments()) {
                    NbtElement enchantmentsNbt = stackToCopyFrom.getEnchantments().copy();
                    stackToCopyTo.setSubNbt(ItemStack.ENCHANTMENTS_KEY, enchantmentsNbt);
                }
            }
        }
    }
}
