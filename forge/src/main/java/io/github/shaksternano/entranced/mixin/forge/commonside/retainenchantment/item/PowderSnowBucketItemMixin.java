package io.github.shaksternano.entranced.mixin.forge.commonside.retainenchantment.item;

import io.github.shaksternano.entranced.commonside.util.EnchantmentUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.PowderSnowBucketItem;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PowderSnowBucketItem.class)
abstract class PowderSnowBucketItemMixin {

    /*
    Powder snow buckets retain their enchantments when placing powder snow.
    Fabric equivalent is io.github.shaksternano.entranced.mixin.fabric.commonside.retainenchantment.item.PowderSnowBucketItemMixin#placeTransferEnchantments
     */
    @Redirect(method = "useOnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;setStackInHand(Lnet/minecraft/util/Hand;Lnet/minecraft/item/ItemStack;)V"))
    private void placeTransferEnchantments(PlayerEntity playerEntity, Hand hand, ItemStack getDefaultStack, ItemUsageContext context) {
        ItemStack stack = context.getStack();
        EnchantmentUtil.copyEnchantments(stack, getDefaultStack);
        playerEntity.setStackInHand(hand, getDefaultStack);
    }
}
