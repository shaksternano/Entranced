package io.github.shaksternano.entranced.mixin.commonloader.commonside.retainenchantment.block;

import io.github.shaksternano.entranced.commonside.Entranced;
import io.github.shaksternano.entranced.commonside.util.BlockEntityUtil;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
abstract class BlockItemMixin {

    // Sets a block entity's enchantments and repair cost when it's placed.
    @Inject(method = "writeTagToBlockEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/BlockItem;getBlockEntityNbt(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/nbt/NbtCompound;"), cancellable = true)
    private static void entranced$setBlockEntityEnchantments(World world, PlayerEntity player, BlockPos pos, ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (Entranced.getConfig().isBlockEntitiesStoreEnchantments()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity != null) {
                if (world.isClient || !blockEntity.copyItemDataRequiresOperator() || player != null && player.isCreativeLevelTwoOp()) {
                    BlockEntityUtil.setBlockEntityEnchantments(blockEntity, stack);
                } else {
                    cir.setReturnValue(false);
                }
            }
        }
    }
}
