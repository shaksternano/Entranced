package io.github.shaksternano.entranced.mixin.fabric.commonside.enchantment.imperishable;

import io.github.shaksternano.entranced.commonside.config.ImperishableBlacklists;
import io.github.shaksternano.entranced.commonside.enchantment.ImperishableEnchantment;
import io.github.shaksternano.entranced.commonside.registry.EntrancedEnchantments;
import io.github.shaksternano.entranced.commonside.util.EnchantmentUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.GrindstoneScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.Map;
import java.util.function.Predicate;

@Mixin(GrindstoneScreenHandler.class)
abstract class GrindstoneScreenHandlerMixin {

    /**
     * The {@link ImperishableEnchantment} cannot be removed in a grindstone if the item in the grindstone is at 0 durability.
     * Forge equivalent is io.github.shaksternano.entranced.mixin.forge.commonside.enchantment.imperishable.GrindstoneScreenHandlerMixin#entranced$imperishableNoGrind
     */
    @ModifyArgs(method = "grind", at = @At(value = "INVOKE", target = "Ljava/util/stream/Stream;filter(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;"))
    private void entranced$imperishableNoGrind(Args args, ItemStack stack, int damage, int amount) {
        Predicate<Map.Entry<Enchantment, Integer>> cursedPredicate = args.get(0);
        args.set(0, cursedPredicate.or(entry -> entry.getKey() == EntrancedEnchantments.IMPERISHABLE && ImperishableBlacklists.isItemProtected(stack, ImperishableBlacklists.ProtectionType.BREAK_PROTECTION) && EnchantmentUtil.isBrokenImperishable(stack)));
    }
}
