package io.github.shaksternano.entranced.mixin.forge.commonside.retainenchantment.item;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.shaksternano.entranced.commonside.util.EnchantmentUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.PowderSnowBucketItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@SuppressWarnings("unused")
@Mixin(PowderSnowBucketItem.class)
abstract class PowderSnowBucketItemMixin {

    /**
     * Powder snow buckets retain their enchantments when placing powder snow.
     * Fabric equivalent is io.github.shaksternano.entranced.mixin.fabric.commonside.retainenchantment.item.PowderSnowBucketItemMixin#entranced$placeTransferEnchantments
     */
    @ModifyExpressionValue(method = "useOnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;getDefaultStack()Lnet/minecraft/item/ItemStack;"))
    private ItemStack entranced$placeTransferEnchantments(ItemStack getDefaultStack, ItemUsageContext context) {
        ItemStack stack = context.getStack();
        EnchantmentUtil.copyEnchantmentsAndName(stack, getDefaultStack);
        return getDefaultStack;
    }
}
