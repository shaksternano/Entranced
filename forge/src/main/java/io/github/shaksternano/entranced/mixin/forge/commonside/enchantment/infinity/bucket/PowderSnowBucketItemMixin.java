package io.github.shaksternano.entranced.mixin.forge.commonside.enchantment.infinity.bucket;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.shaksternano.entranced.commonside.access.enchantment.infinity.bucket.ExtraItemStackArgument;
import net.minecraft.enchantment.InfinityEnchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.PowderSnowBucketItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@SuppressWarnings("unused")
@Mixin(PowderSnowBucketItem.class)
abstract class PowderSnowBucketItemMixin {

    /**
     * Powder snow in buckets with the {@link InfinityEnchantment} don't go away when placing the powder snow.
     * Fabric equivalent is io.github.shaksternano.entranced.mixin.fabric.commonside.enchantment.infinity.bucket.PowderSnowBucketItemMixin#entranced$infinityPowderSnowBucket
     */
    @ModifyExpressionValue(method = "useOnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;getDefaultStack()Lnet/minecraft/item/ItemStack;"))
    private ItemStack entranced$infinityPowderSnowBucket(ItemStack getDefaultStack, ItemUsageContext context) {
        return ((ExtraItemStackArgument) context).entranced$getArgument().orElse(getDefaultStack);
    }
}
