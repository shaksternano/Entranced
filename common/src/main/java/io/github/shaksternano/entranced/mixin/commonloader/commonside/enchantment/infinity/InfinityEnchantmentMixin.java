package io.github.shaksternano.entranced.mixin.commonloader.commonside.enchantment.infinity;

import io.github.shaksternano.entranced.commonside.Entranced;
import io.github.shaksternano.entranced.commonside.config.InfinityBucketWhitelists;
import io.github.shaksternano.entranced.mixin.commonloader.commonside.accessor.BucketItemAccessor;
import io.github.shaksternano.entranced.mixin.commonloader.commonside.enchantment.EnchantmentMixin;
import net.minecraft.enchantment.InfinityEnchantment;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MilkBucketItem;
import net.minecraft.item.PowderSnowBucketItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("unused")
@Mixin(InfinityEnchantment.class)
abstract class InfinityEnchantmentMixin extends EnchantmentMixin {

    /**
     * The {@link InfinityEnchantment} can be put on buckets.
     */

    @Override
    protected void entranced$addAcceptableItem(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (Entranced.getConfig().isInfinityAllowedOnBuckets()) {
            Item item = stack.getItem();

            if (item instanceof BucketItemAccessor bucketItem) {
                if (!bucketItem.entranced$getFluid().equals(Fluids.EMPTY)) {
                    if (InfinityBucketWhitelists.INSTANCE.isFluidWhitelisted(bucketItem.entranced$getFluid())) {
                        cir.setReturnValue(true);
                    }
                }
            } else if (item instanceof PowderSnowBucketItem powderSnowBucketItem) {
                if (InfinityBucketWhitelists.INSTANCE.isBlockWhitelisted(powderSnowBucketItem.getBlock())) {
                    cir.setReturnValue(true);
                }
            } else if (item instanceof MilkBucketItem) {
                if (InfinityBucketWhitelists.INSTANCE.isItemWhitelisted(item)) {
                    cir.setReturnValue(true);
                }
            }
        }
    }
}
