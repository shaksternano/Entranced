package io.github.shaksternano.entranced.commonside.fabricasm.fabric;

import io.github.shaksternano.entranced.commonside.config.ImperishableBlacklists;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.item.FluidModificationItem;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@SuppressWarnings("unused")
class EnchantmentTargets {

    public static class ImperishableTarget extends EnchantmentTargetMixin {

        @Override
        public boolean isAcceptableItem(Item item) {
            return !ImperishableBlacklists.isItemBlacklistedGlobally(item);
        }
    }

    public static class FluidContainerTarget extends EnchantmentTargetMixin {

        @Override
        public boolean isAcceptableItem(Item item) {
            return item instanceof FluidModificationItem;
        }
    }

    @SuppressWarnings({"unused", "UnusedMixin"})
    @Mixin(EnchantmentTarget.class)
    private static abstract class EnchantmentTargetMixin {

        @Shadow public abstract boolean isAcceptableItem(Item item);
    }
}
