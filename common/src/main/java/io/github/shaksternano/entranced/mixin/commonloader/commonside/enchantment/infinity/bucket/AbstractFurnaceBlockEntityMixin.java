package io.github.shaksternano.entranced.mixin.commonloader.commonside.enchantment.infinity.bucket;

import io.github.shaksternano.entranced.commonside.util.EnchantmentUtil;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractFurnaceBlockEntity.class)
abstract class AbstractFurnaceBlockEntityMixin {

    // A bucket ItemStack with Infinity doesn't have its count decremented when used as furnace fuel.
    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;decrement(I)V"))
    private static void infinityNoDecrement(ItemStack itemStack, int amount) {
        if (!EnchantmentUtil.isBucketAndHasInfinityAndBucketEnabled(itemStack)) {
            itemStack.decrement(amount);
        }
    }
}
