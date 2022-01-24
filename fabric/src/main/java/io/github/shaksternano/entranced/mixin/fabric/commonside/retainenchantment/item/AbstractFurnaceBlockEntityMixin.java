package io.github.shaksternano.entranced.mixin.fabric.commonside.retainenchantment.item;

import io.github.shaksternano.entranced.commonside.util.EnchantmentUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(AbstractFurnaceBlockEntity.class)
abstract class AbstractFurnaceBlockEntityMixin {

    /**
     * Items retain their enchantments when used as furnace fuel.
     * Forge equivalent is io.github.shaksternano.entranced.mixin.forge.commonside.IForgeItemMixin#entranced$getContainerItem
     */
    @ModifyArgs(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/collection/DefaultedList;set(ILjava/lang/Object;)Ljava/lang/Object;"))
    private static void entranced$furnaceFuelTransferEnchantments(Args args, World world, BlockPos pos, BlockState state, AbstractFurnaceBlockEntity blockEntity) {
        ItemStack unusedFuel = blockEntity.getStack(1);
        ItemStack usedFuel = args.get(1);
        EnchantmentUtil.copyEnchantments(unusedFuel, usedFuel);
    }
}
