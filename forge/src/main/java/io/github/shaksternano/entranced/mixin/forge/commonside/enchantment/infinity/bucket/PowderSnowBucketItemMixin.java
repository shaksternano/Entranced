package io.github.shaksternano.entranced.mixin.forge.commonside.enchantment.infinity.bucket;

import io.github.shaksternano.entranced.commonside.access.ItemStackHolder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.PowderSnowBucketItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PowderSnowBucketItem.class)
abstract class PowderSnowBucketItemMixin {

    /*
    Powder snow in buckets with infinity don't go away when placing the powder snow.
    Fabric equivalent is io.github.shaksternano.entranced.mixin.fabric.commonside.enchantment.infinity.bucket.PowderSnowBucketItemMixin#infinityPowderSnowBucket
     */
    @Redirect(method = "useOnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;getDefaultStack()Lnet/minecraft/item/ItemStack;"))
    private ItemStack infinityPowderSnowBucket(Item BUCKET, ItemUsageContext context) {
        ItemStack powderSnowBucketStack = ((ItemStackHolder) context).entranced$getItemStack();
        return powderSnowBucketStack == null ? BUCKET.getDefaultStack() : powderSnowBucketStack;
    }
}
