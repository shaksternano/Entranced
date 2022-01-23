package io.github.shaksternano.entranced.mixin.commonloader.commonside.enchantment.infinity.bucket;

import io.github.shaksternano.entranced.commonside.access.ItemStackParameter;
import io.github.shaksternano.entranced.commonside.util.EnchantmentUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.PowderSnowBucketItem;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemUsageContext.class)
abstract class ItemUsageContextMixin implements ItemStackParameter {

    @Unique
    @Nullable
    private ItemStack entranced$infinityPowderSnowBucketStack;

    // Remember the powder snow bucket ItemStack and its data.
    @Inject(method = "<init>(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/hit/BlockHitResult;)V", at = @At("RETURN"))
    private void entranced$setInfinityPowderSnowBucket(World world, PlayerEntity player, Hand hand, ItemStack stack, BlockHitResult hit, CallbackInfo ci) {
        if (stack.getItem() instanceof PowderSnowBucketItem) {
            if (EnchantmentUtil.isBucketAndHasInfinityAndBucketEnabled(stack)) {
                entranced$infinityPowderSnowBucketStack = stack.copy();
            }
        }
    }

    @Unique
    @Nullable
    @Override
    public ItemStack entranced$getItemStackParameter() {
        return entranced$infinityPowderSnowBucketStack;
    }
}
