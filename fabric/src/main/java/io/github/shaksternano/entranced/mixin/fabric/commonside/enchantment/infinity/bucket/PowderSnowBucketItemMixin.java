package io.github.shaksternano.entranced.mixin.fabric.commonside.enchantment.infinity.bucket;

import io.github.shaksternano.entranced.commonside.access.enchantment.infinity.bucket.ExtraItemStackArgument;
import net.minecraft.enchantment.InfinityEnchantment;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.PowderSnowBucketItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(PowderSnowBucketItem.class)
abstract class PowderSnowBucketItemMixin {

    /**
     * Powder snow in buckets with the {@link InfinityEnchantment} don't go away when placing the powder snow.
     * Forge equivalent is io.github.shaksternano.entranced.mixin.forge.commonside.enchantment.infinity.bucket.PowderSnowBucketItemMixin#entranced$infinityPowderSnowBucket
     */
    @ModifyArgs(method = "useOnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;setStackInHand(Lnet/minecraft/util/Hand;Lnet/minecraft/item/ItemStack;)V"))
    private void entranced$infinityPowderSnowBucket(Args args, ItemUsageContext context) {
        ((ExtraItemStackArgument) context).entranced$getArgument().ifPresent(powderSnowBucketStack -> args.set(1, powderSnowBucketStack));
    }
}
