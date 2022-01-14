package io.github.shaksternano.entranced.mixin.forge.commonside.enchantment.imperishable;

import io.github.shaksternano.entranced.commonside.config.ImperishableBlacklists;
import io.github.shaksternano.entranced.commonside.registry.EntrancedEnchantments;
import io.github.shaksternano.entranced.commonside.util.EnchantmentUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.GrindstoneScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Mixin(GrindstoneScreenHandler.class)
abstract class GrindstoneScreenHandlerMixin {

    /*
    The Imperishable enchantment cannot be removed in a grindstone if the item in the grindstone is at 0 durability.
    Fabric equivalent is io.github.shaksternano.entranced.mixin.fabric.commonside.enchantment.imperishable.GrindstoneScreenHandlerMixin#entranced$imperishableNoGrind
     */
    @Redirect(method = "grind", at = @At(value = "INVOKE", target = "Ljava/util/stream/Stream;filter(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;"))
    private Stream<Map.Entry<Enchantment, Integer>> entranced$imperishableNoGrind(Stream<Map.Entry<Enchantment, Integer>> enchantmentsStream, Predicate<Map.Entry<Enchantment, Integer>> cursedPredicate, ItemStack stack) {
        return enchantmentsStream.filter(cursedPredicate.or(entry -> entry.getKey() == EntrancedEnchantments.IMPERISHABLE && ImperishableBlacklists.isItemProtected(stack, ImperishableBlacklists.ProtectionType.BREAK_PROTECTION) && EnchantmentUtil.isBrokenImperishable(stack)));
    }
}
