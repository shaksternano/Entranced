package io.github.shaksternano.entranced.commonside.fabricasm.enchantmenttarget.fabric;

import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.item.FluidModificationItem;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@SuppressWarnings("unused")
public class FluidContainerTarget extends EnchantmentTargetMixin {

    @Override
    public boolean isAcceptableItem(Item item) {
        return item instanceof FluidModificationItem;
    }
}

@SuppressWarnings({"unused", "UnusedMixin"})
@Mixin(EnchantmentTarget.class)
abstract class EnchantmentTargetMixin {

    @Shadow public abstract boolean isAcceptableItem(Item item);
}
