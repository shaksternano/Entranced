package io.github.shaksternano.entranced.mixin.fabric.commonside.enchantment.infinity.bucket;

import io.github.shaksternano.entranced.commonside.util.BucketUtil;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.enchantment.InfinityEnchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPointer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemDispenserBehavior.class)
abstract class ItemDispenserBehaviorMixin {

    /**
     * The {@link ItemStack} being dispensed doesn't change if it is a bucket with the {@link InfinityEnchantment}.
     * Forge equivalent is io.github.shaksternano.entranced.mixin.forge.commonside.enchantment.infinity.bucket.ItemDispenserBehaviorMixin#entranced$dispenseInfinity
     */
    @Inject(method = "dispense", at = @At("RETURN"), cancellable = true)
    private void entranced$dispenseInfinity(BlockPointer blockPointer, ItemStack itemStack, CallbackInfoReturnable<ItemStack> cir) {
        ItemStack bucketStack = BucketUtil.infinityBucketKeepFluid(itemStack);

        if (bucketStack != null) {
            cir.setReturnValue(bucketStack);
        }
    }
}
