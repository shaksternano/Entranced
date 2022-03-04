package io.github.shaksternano.entranced.mixin.commonloader.commonside.enchantingtablefilter;

import io.github.shaksternano.entranced.commonside.Entranced;
import io.github.shaksternano.entranced.commonside.access.enchantingtablefilter.ExtraEnchantingCatalystTypeArgument;
import io.github.shaksternano.entranced.commonside.config.EnchantingCatalystConfig;
import io.github.shaksternano.entranced.commonside.util.EnchantmentUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

@Mixin(EnchantmentHelper.class)
abstract class EnchantmentHelperMixin {

    /**
     * Applies the enchanting catalyst by not adding certain enchantments to the possible entries list
     */
    @Redirect(method = "getPossibleEntries", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/Enchantment;isAvailableForRandomSelection()Z"))
    private static boolean entranced$filterEnchantment(Enchantment enchantment, int power, ItemStack stack) {
        AtomicBoolean filteredEnchantment = new AtomicBoolean(true);

        if (Entranced.getConfig().isEnchantingCatalystEnabled()) {
            ((ExtraEnchantingCatalystTypeArgument) (Object) stack).entranced$getArgument().ifPresent(
                    usedCatalystType -> filteredEnchantment.set(EnchantingCatalystConfig.isCatalystAllowed(usedCatalystType, enchantment))
            );
        }

        return enchantment.isAvailableForRandomSelection() && filteredEnchantment.get();
    }

    /**
     * When an enchanting catalyst filters out all possible enchantments for an item,
     * an enchanting table will show no enchantments available instead of showing
     * available enchantments that are blank.
     */
    @Inject(method = "calculateRequiredExperienceLevel", at = @At("RETURN"), cancellable = true)
    private static void noEnchantmentsAvailable(Random random, int slotIndex, int bookshelfCount, ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        if (Entranced.getConfig().isEnchantingCatalystEnabled()) {
            int enchantmentPower = cir.getReturnValue();
            int enchantability = EnchantmentUtil.getItemEnchantability(stack);
            enchantmentPower += 1 + random.nextInt(enchantability / 4 + 1) + random.nextInt(enchantability / 4 + 1);
            float multiplier = (random.nextFloat() + random.nextFloat() - 1.0F) * 0.15F;
            final int finalEnchantmentPower = MathHelper.clamp(Math.round((float) enchantmentPower + (float) enchantmentPower * multiplier), 1, Integer.MAX_VALUE);

            ((ExtraEnchantingCatalystTypeArgument) (Object) stack).entranced$getArgument().ifPresent(usedCatalystType -> {
                if (EnchantmentHelper.getPossibleEntries(finalEnchantmentPower, stack, false).isEmpty()) {
                    cir.setReturnValue(0);
                }
            });
        }
    }
}
