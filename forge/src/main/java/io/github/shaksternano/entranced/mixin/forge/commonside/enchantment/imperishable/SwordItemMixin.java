package io.github.shaksternano.entranced.mixin.forge.commonside.enchantment.imperishable;

import io.github.shaksternano.entranced.commonside.config.ImperishableBlacklists;
import io.github.shaksternano.entranced.commonside.util.EnchantmentUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraftforge.common.ToolAction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SwordItem.class)
abstract class SwordItemMixin {

    /*
    Swords with the Imperishable enchantment at 0 durability can't perform sweeping attacks.
    Fabric equivalent is io.github.shaksternano.entranced.mixin.fabric.commonside.enchantment.imperishable.PlayerEntityMixin#imperishableSwordSweeping
     */
    @Inject(method = "canPerformAction", at = @At("HEAD"), cancellable = true)
    private void imperishableSwordSweeping(ItemStack stack, ToolAction toolAction, CallbackInfoReturnable<Boolean> cir) {
        if (ImperishableBlacklists.isItemProtected(stack, ImperishableBlacklists.ProtectionType.BREAK_PROTECTION)) {
            if (EnchantmentUtil.isBrokenImperishable(stack)) {
                cir.setReturnValue(false);
            }
        }
    }
}
