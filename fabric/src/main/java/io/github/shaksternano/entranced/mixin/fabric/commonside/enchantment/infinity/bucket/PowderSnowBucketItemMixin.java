package io.github.shaksternano.entranced.mixin.fabric.commonside.enchantment.infinity.bucket;

import io.github.shaksternano.entranced.commonside.access.ItemStackHolder;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.PowderSnowBucketItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(PowderSnowBucketItem.class)
abstract class PowderSnowBucketItemMixin {

    // Powder snow in buckets with infinity don't go away when placing the powder snow.
    @ModifyArgs(method = "useOnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;setStackInHand(Lnet/minecraft/util/Hand;Lnet/minecraft/item/ItemStack;)V"))
    private void infinityPowderSnowBucket(Args args, ItemUsageContext context) {
        ItemStack powderSnowBucketStack = ((ItemStackHolder) context).entranced$getItemStack();

        if (powderSnowBucketStack != null) {
            args.set(1, powderSnowBucketStack);
        }
    }
}
