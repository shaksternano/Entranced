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

import java.util.concurrent.atomic.AtomicBoolean;

@Mixin(EnchantmentHelper.class)
abstract class EnchantmentHelperMixin {

    /**
     * Applies the enchanting catalyst by not adding certain enchantments to the possible entries list
     */
    @Redirect(method = "getPossibleEntries", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/Enchantment;isAvailableForRandomSelection()Z"))
    private static boolean entranced$filterEnchantment(Enchantment enchantment, int power, ItemStack stack) {
        AtomicBoolean filteredEnchantment = new AtomicBoolean(true);

        if (Entranced.INSTANCE.getConfig().isEnchantingCatalystEnabled()) {
            ((ExtraEnchantingCatalystTypeArgument) (Object) stack).entranced$getArgument().ifPresent(
                    usedCatalystType -> filteredEnchantment.set(EnchantingCatalystConfig.INSTANCE.isCatalystAllowed(usedCatalystType, enchantment))
            );
        }

        return enchantment.isAvailableForRandomSelection() && filteredEnchantment.get();
    }
}
