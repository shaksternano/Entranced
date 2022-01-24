package io.github.shaksternano.entranced.mixin.commonloader.commonside.enchantment.infinity.bucket;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import io.github.shaksternano.entranced.commonside.util.EnchantmentUtil;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.enchantment.InfinityEnchantment;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@SuppressWarnings("unused")
@Mixin(AbstractFurnaceBlockEntity.class)
abstract class AbstractFurnaceBlockEntityMixin {

    /**
     * A bucket fuel with the {@link InfinityEnchantment}
     * doesn't have its count decremented when used as furnace fuel.
     */
    @WrapWithCondition(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;decrement(I)V"))
    private static boolean entranced$infinityNoDecrement(ItemStack itemStack, int amount) {
        return !EnchantmentUtil.isBucketAndHasInfinityAndBucketEnabled(itemStack);
    }
}
