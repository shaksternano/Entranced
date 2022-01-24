package io.github.shaksternano.entranced.mixin.commonloader.commonside.retainenchantment.item;

import io.github.shaksternano.entranced.commonside.util.EnchantmentUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemUsage.class)
abstract class ItemUsageMixin {

    /**
     * Items retain their enchantments when used.
     */
    @Inject(method = "exchangeStack(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;Z)Lnet/minecraft/item/ItemStack;", at = @At("HEAD"))
    private static void entranced$transferEnchantments(ItemStack inputStack, PlayerEntity player, ItemStack outputStack, boolean creativeOverride, CallbackInfoReturnable<ItemStack> cir) {
        EnchantmentUtil.copyEnchantments(inputStack, outputStack);
    }
}
