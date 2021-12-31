package io.github.shaksternano.entranced.mixin.commonloader.commonside.enchantment.infinity.bucket;

import io.github.shaksternano.entranced.commonside.util.EnchantmentUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MilkBucketItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MilkBucketItem.class)
abstract class MilkBucketItemMixin {

    // A milk bucket ItemStack with the Infinity enchantment doesn't have its count decremented.
    @Redirect(method = "finishUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;decrement(I)V"))
    private void infinityNoDecrement(ItemStack stack, int amount) {
        if (!EnchantmentUtil.isBucketAndHasInfinityAndBucketEnabled(stack)) {
            stack.decrement(amount);
        }
    }
}
