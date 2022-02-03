package io.github.shaksternano.entranced.mixin.commonloader.commonside.enchantingtablefilter;

import io.github.shaksternano.entranced.commonside.Entranced;
import io.github.shaksternano.entranced.commonside.access.enchantingtablefilter.ExtraEnchantingCatalystTypeArgument;
import io.github.shaksternano.entranced.commonside.config.EnchantingCatalystConfig;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EnchantmentHelper.class)
abstract class EnchantmentHelperMixin {

    @Redirect(method = "getPossibleEntries", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/Enchantment;isAvailableForRandomSelection()Z"))
    private static boolean entranced$filterEnchantment(Enchantment enchantment, int power, ItemStack stack) {
        boolean filteredEnchantment = true;

        if (Entranced.INSTANCE.getConfig().isEnchantingCatalystEnabled()) {
            EnchantingCatalystConfig.EnchantingCatalystType usedCatalystType = ((ExtraEnchantingCatalystTypeArgument) (Object) stack).entranced$getArgument();

            if (usedCatalystType != null) {
                filteredEnchantment = EnchantingCatalystConfig.INSTANCE.isCatalystAffected(usedCatalystType, enchantment);
            }
        }

        return enchantment.isAvailableForRandomSelection() && filteredEnchantment;
    }
}
