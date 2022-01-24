package io.github.shaksternano.entranced.mixin.commonloader.commonside.enchantment.infinity.bucket;

import io.github.shaksternano.entranced.commonside.util.BucketUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BucketItem.class)
public abstract class BucketItemMixin {

    /**
     * Fluids in buckets with the {@link net.minecraft.enchantment.InfinityEnchantment}
     * don't go away when placing the fluid.
     */
    @Inject(method = "getEmptiedStack", at = @At("HEAD"), cancellable = true)
    private static void entranced$infinityEntityBucket(ItemStack stack, PlayerEntity player, CallbackInfoReturnable<ItemStack> cir) {
        if (!player.getAbilities().creativeMode) {
            ItemStack bucketStack = BucketUtil.infinityBucketKeepFluid(stack);

            if (bucketStack != null) {
                cir.setReturnValue(bucketStack);
            }
        }
    }
}
