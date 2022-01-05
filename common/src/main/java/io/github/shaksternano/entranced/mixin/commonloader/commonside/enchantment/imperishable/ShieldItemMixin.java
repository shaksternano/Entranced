package io.github.shaksternano.entranced.mixin.commonloader.commonside.enchantment.imperishable;

import io.github.shaksternano.entranced.commonside.config.ImperishableBlacklists;
import io.github.shaksternano.entranced.commonside.util.EnchantmentUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.util.UseAction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShieldItem.class)
abstract class ShieldItemMixin extends Item {

    private ShieldItemMixin(Settings settings) {
        super(settings);
    }

    // Shields with the Imperishable enchantment at 0 durability can't block.
    @Inject(method = "getUseAction", at = @At("HEAD"), cancellable = true)
    private void imperishableShield(ItemStack stack, CallbackInfoReturnable<UseAction> cir) {
        if (ImperishableBlacklists.isItemProtected(stack, ImperishableBlacklists.ProtectionType.BREAK_PROTECTION)) {
            if (EnchantmentUtil.isBrokenImperishable(stack)) {
                cir.setReturnValue(super.getUseAction(stack));
            }
        }
    }
}
