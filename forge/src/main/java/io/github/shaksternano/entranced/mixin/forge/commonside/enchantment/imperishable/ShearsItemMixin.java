package io.github.shaksternano.entranced.mixin.forge.commonside.enchantment.imperishable;

import io.github.shaksternano.entranced.commonside.config.ImperishableBlacklists;
import io.github.shaksternano.entranced.commonside.util.EnchantmentUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraftforge.common.ToolAction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShearsItem.class)
abstract class ShearsItemMixin {

    /*
    Shears with the Imperishable enchantment at 0 durability can't disarm tripwires.
    Fabric equivalent is io.github.shaksternano.entranced.mixin.fabric.commonside.enchantment.imperishable.TripwireBlockMixin#imperishableDisarmTripwire
     */
    @Inject(method = "canPerformAction", at = @At("HEAD"), cancellable = true)
    private void imperishableDisarmTripwire(ItemStack stack, ToolAction toolAction, CallbackInfoReturnable<Boolean> cir) {
        if (ImperishableBlacklists.isItemProtected(stack, ImperishableBlacklists.ProtectionType.BREAK_PROTECTION)) {
            if (EnchantmentUtil.isBrokenImperishable(stack)) {
                cir.setReturnValue(false);
            }
        }
    }
}
