package io.github.shaksternano.entranced.mixin.commonloader.commonside.retainenchantment.block;

import io.github.shaksternano.entranced.commonside.Entranced;
import io.github.shaksternano.entranced.commonside.util.BlockEntityUtil;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(AbstractBlock.class)
abstract class AbstractBlockMixin {

    /**
     * {@link BlockEntity}s that have enchantments retain those enchantments when broken.
     */
    @Inject(method = "getDroppedStacks", at = @At("RETURN"))
    private void entranced$setDroppedItemStackEnchantments(BlockState state, LootContext.Builder builder, CallbackInfoReturnable<List<ItemStack>> cir) {
        if (Entranced.getConfig().isBlockEntitiesStoreEnchantments()) {
            if (state.getBlock() instanceof BlockWithEntity) {
                List<ItemStack> stacks = cir.getReturnValue();

                for (ItemStack stack : stacks) {
                    /*
                    Checks if the dropped item is the same as the broken block entity, for example
                    an Ender Chest doesn't drop an Ender Chest when broken without silk touch.
                     */
                    if (stack.isOf(state.getBlock().asItem())) {
                        BlockEntity blockEntity = builder.get(LootContextParameters.BLOCK_ENTITY);
                        BlockEntityUtil.setDroppedItemStackEnchantments(blockEntity, stack);
                    }
                }
            }
        }
    }
}
