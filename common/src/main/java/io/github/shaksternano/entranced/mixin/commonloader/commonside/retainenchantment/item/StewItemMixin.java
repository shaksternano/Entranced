package io.github.shaksternano.entranced.mixin.commonloader.commonside.retainenchantment.item;

import io.github.shaksternano.entranced.commonside.util.EnchantmentUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.StewItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StewItem.class)
abstract class StewItemMixin {

    /**
     * Stews retain their enchantments when eaten.
     */
    @Inject(method = "finishUsing", at = @At("RETURN"))
    private void entranced$consumeTransferEnchantments(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
        ItemStack consumedStack = cir.getReturnValue();
        EnchantmentUtil.copyEnchantmentsAndName(stack, consumedStack);
    }
}
