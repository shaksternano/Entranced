package io.github.shaksternano.entranced.mixin.fabric.commonside.enchantment.imperishable;

import io.github.shaksternano.entranced.commonside.config.ImperishableBlacklists;
import io.github.shaksternano.entranced.commonside.util.EnchantmentUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.TripwireBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TripwireBlock.class)
abstract class TripwireBlockMixin {

    /*
    Shears with the Imperishable enchantment at 0 durability can't disarm tripwires.
    Forge equivalent is io.github.shaksternano.entranced.mixin.forge.commonside.enchantment.imperishable.ShearsItemMixin#imperishableDisarmTripwire
     */
    @Redirect(method = "onBreak", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isEmpty()Z"))
    private boolean imperishableDisarmTripwire(ItemStack getMainHandStack, World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!player.isCreative()) {
            if (ImperishableBlacklists.isItemProtected(getMainHandStack, ImperishableBlacklists.ProtectionType.BREAK_PROTECTION)) {
                if (EnchantmentUtil.isBrokenImperishable(getMainHandStack)) {
                    return true;
                }
            }
        }

        return getMainHandStack.isEmpty();
    }
}
