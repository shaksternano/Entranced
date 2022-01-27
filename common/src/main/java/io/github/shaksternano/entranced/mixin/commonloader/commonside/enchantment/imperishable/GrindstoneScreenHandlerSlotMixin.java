package io.github.shaksternano.entranced.mixin.commonloader.commonside.enchantment.imperishable;

import io.github.shaksternano.entranced.commonside.config.ImperishableBlacklists;
import io.github.shaksternano.entranced.commonside.enchantment.ImperishableEnchantment;
import io.github.shaksternano.entranced.commonside.registry.EntrancedEnchantments;
import io.github.shaksternano.entranced.commonside.util.EnchantmentUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "net/minecraft/screen/GrindstoneScreenHandler$4")
abstract class GrindstoneScreenHandlerSlotMixin {

    /**
     * No extra experience is dropped when grinding the {@link ImperishableEnchantment}
     * and the item in the grindstone is at 0 durability.
     */
    @Redirect(method = "getExperience(Lnet/minecraft/item/ItemStack;)I", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/Enchantment;isCursed()Z"))
    private boolean entranced$imperishableNoGrindExperience(Enchantment enchantment, ItemStack stack) {
        return enchantment.isCursed() || (enchantment == EntrancedEnchantments.IMPERISHABLE && ImperishableBlacklists.INSTANCE.isItemProtected(stack, ImperishableBlacklists.ProtectionType.BREAK_PROTECTION) && EnchantmentUtil.isBrokenImperishable(stack));
    }
}
