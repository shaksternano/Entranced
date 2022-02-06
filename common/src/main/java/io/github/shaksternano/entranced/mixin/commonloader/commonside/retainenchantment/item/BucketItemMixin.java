package io.github.shaksternano.entranced.mixin.commonloader.commonside.retainenchantment.item;

import io.github.shaksternano.entranced.commonside.util.EnchantmentUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BucketItem.class)
abstract class BucketItemMixin {

    /**
     * Buckets retain their enchantments when placing fluids.
     */
    @Inject(method = "getEmptiedStack", at = @At("RETURN"))
    private static void entranced$placeTransferEnchantments(ItemStack stack, PlayerEntity player, CallbackInfoReturnable<ItemStack> cir) {
        if (!player.getAbilities().creativeMode) {
            ItemStack emptiedStack = cir.getReturnValue();
            EnchantmentUtil.copyEnchantments(stack, emptiedStack);
        }
    }
}
