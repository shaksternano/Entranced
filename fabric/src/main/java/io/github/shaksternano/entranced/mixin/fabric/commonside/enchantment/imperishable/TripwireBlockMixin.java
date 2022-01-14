package io.github.shaksternano.entranced.mixin.fabric.commonside.enchantment.imperishable;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.shaksternano.entranced.commonside.config.ImperishableBlacklists;
import io.github.shaksternano.entranced.commonside.util.EnchantmentUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.TripwireBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@SuppressWarnings("unused")
@Mixin(TripwireBlock.class)
abstract class TripwireBlockMixin {

    /*
    Shears with the Imperishable enchantment at 0 durability can't disarm tripwires.
    Forge equivalent is io.github.shaksternano.entranced.mixin.forge.commonside.enchantment.imperishable.ShearsItemMixin#imperishableDisarmTripwire
     */
    @ModifyExpressionValue(method = "onBreak", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private boolean imperishableDisarmTripwire(boolean isOf, World world, BlockPos pos, BlockState state, PlayerEntity player) {
        return isOf && !(!player.isCreative() && ImperishableBlacklists.isItemProtected(player.getMainHandStack(), ImperishableBlacklists.ProtectionType.BREAK_PROTECTION) && EnchantmentUtil.isBrokenImperishable(player.getMainHandStack()));
    }
}
