package io.github.shaksternano.entranced.mixin.forge.commonside.retainenchantment.item;

import io.github.shaksternano.entranced.commonside.util.EnchantmentUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractFurnaceBlockEntity.class)
abstract class AbstractFurnaceBlockEntityMixin {

    // Items retain their enchantments when used as furnace fuel.
    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/collection/DefaultedList;set(ILjava/lang/Object;)Ljava/lang/Object;"))
    private static <E> E furnaceFuelTransferEnchantments(DefaultedList<E> inventory, int i, E getContainerItem, World world, BlockPos pos, BlockState state, AbstractFurnaceBlockEntity blockEntity) {
        ItemStack unusedFuel = blockEntity.getStack(i);
        EnchantmentUtil.copyEnchantments(unusedFuel, (ItemStack) getContainerItem);
        return inventory.set(i, getContainerItem);
    }
}
