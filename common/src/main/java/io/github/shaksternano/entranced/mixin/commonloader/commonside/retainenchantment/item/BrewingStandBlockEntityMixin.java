package io.github.shaksternano.entranced.mixin.commonloader.commonside.retainenchantment.item;

import io.github.shaksternano.entranced.commonside.util.EnchantmentUtil;
import net.minecraft.block.entity.BrewingStandBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(BrewingStandBlockEntity.class)
abstract class BrewingStandBlockEntityMixin {

    // Items retain their enchantments when used as brewing stand ingredients.
    @ModifyVariable(method = "craft", at = @At("STORE"), ordinal = 1)
    private static ItemStack brewingStandTransferEnchantments(ItemStack newItemStack, World world, BlockPos pos, DefaultedList<ItemStack> slots) {
        ItemStack itemStack = slots.get(3);
        EnchantmentUtil.copyEnchantments(itemStack, newItemStack);
        return newItemStack;
    }
}
