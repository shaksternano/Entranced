package io.github.shaksternano.entranced.mixin.fabric.commonside.retainenchantment.item;

import io.github.shaksternano.entranced.commonside.util.EnchantmentUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.PowderSnowBucketItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(PowderSnowBucketItem.class)
abstract class PowderSnowBucketItemMixin {

    // Powder snow buckets retain their enchantments when placing powder snow.
    @ModifyArgs(method = "useOnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;setStackInHand(Lnet/minecraft/util/Hand;Lnet/minecraft/item/ItemStack;)V"))
    private void placeTransferEnchantments(Args args, ItemUsageContext context) {
        ItemStack stack = context.getStack();
        ItemStack getDefaultStack = args.get(1);
        EnchantmentUtil.copyEnchantments(stack, getDefaultStack);
    }
}
