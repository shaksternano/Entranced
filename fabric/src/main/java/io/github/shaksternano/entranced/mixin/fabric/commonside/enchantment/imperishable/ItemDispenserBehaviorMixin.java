package io.github.shaksternano.entranced.mixin.fabric.commonside.enchantment.imperishable;

import io.github.shaksternano.entranced.commonside.config.ImperishableBlacklists;
import io.github.shaksternano.entranced.commonside.util.EnchantmentUtil;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Wearable;
import net.minecraft.util.math.BlockPointer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemDispenserBehavior.class)
abstract class ItemDispenserBehaviorMixin {

    @Shadow protected abstract ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack);

    /*
    Dispensing an item is cancelled if that item has Imperishable and is at 0 durability.
    Forge equivalent is io.github.shaksternano.entranced.mixin.forge.commonside.enchantment.imperishable.ItemDispenserBehaviorMixin#entranced$dispenseBrokenImperishable
     */
    @Redirect(method = "dispense", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/dispenser/ItemDispenserBehavior;dispenseSilently(Lnet/minecraft/util/math/BlockPointer;Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;"))
    private ItemStack entranced$dispenseBrokenImperishable(ItemDispenserBehavior thisBehavior, BlockPointer pointer, ItemStack stack) {
        if (ImperishableBlacklists.isItemProtected(stack, ImperishableBlacklists.ProtectionType.BREAK_PROTECTION)) {
            // Still allow a wearable item to be dispensed even if the item is broken.
            if (!(stack.getItem() instanceof Wearable)) {
                if (EnchantmentUtil.isBrokenImperishable(stack)) {
                    if (thisBehavior instanceof FallibleItemDispenserBehavior thisFallibleItemDispenserBehavior) {
                        thisFallibleItemDispenserBehavior.setSuccess(false);
                    }

                    return stack;
                }
            }
        }

        return dispenseSilently(pointer, stack);
    }
}
