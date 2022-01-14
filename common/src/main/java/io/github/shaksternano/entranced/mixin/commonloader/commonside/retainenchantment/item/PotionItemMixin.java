package io.github.shaksternano.entranced.mixin.commonloader.commonside.retainenchantment.item;

import io.github.shaksternano.entranced.commonside.util.EnchantmentUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PotionItem.class)
abstract class PotionItemMixin {

    // Potions retain their enchantments when drank.
    @Inject(method = "finishUsing", at = @At("RETURN"))
    private void entranced$consumeTransferEnchantments(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
        ItemStack consumedStack = cir.getReturnValue();
        EnchantmentUtil.copyEnchantments(stack, consumedStack);
    }
}
