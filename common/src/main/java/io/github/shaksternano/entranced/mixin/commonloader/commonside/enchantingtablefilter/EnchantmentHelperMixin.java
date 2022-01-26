package io.github.shaksternano.entranced.mixin.commonloader.commonside.enchantingtablefilter;

import io.github.shaksternano.entranced.commonside.access.ExtraArgument;
import io.github.shaksternano.entranced.commonside.config.EnchantingCatalystSets;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Set;

@Mixin(EnchantmentHelper.class)
abstract class EnchantmentHelperMixin {

    private static final Set<Enchantment> entranced$offensiveEnchantments = Set.of(
            Enchantments.SHARPNESS,
            Enchantments.SMITE,
            Enchantments.BANE_OF_ARTHROPODS,
            Enchantments.SWEEPING,
            Enchantments.FIRE_ASPECT,
            Enchantments.POWER,
            Enchantments.PUNCH,
            Enchantments.FLAME
    );

    @Redirect(method = "getPossibleEntries", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/Enchantment;isAvailableForRandomSelection()Z"))
    private static boolean entranced$filterEnchantment(Enchantment enchantment, int power, ItemStack stack) {
        EnchantingCatalystSets.EnchantingCatalystType usedCatalystType = ((ExtraArgument) (Object) stack).entranced$getCatalystType();
        boolean filteredEnchantment = usedCatalystType == EnchantingCatalystSets.EnchantingCatalystType.OFFENSIVE && !entranced$offensiveEnchantments.contains(enchantment);

        return enchantment.isAvailableForRandomSelection() && !filteredEnchantment;
    }
}
