package io.github.shaksternano.entranced.commonside.enchantment;

import io.github.shaksternano.entranced.commonside.Entranced;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import org.jetbrains.annotations.NotNull;

/**
 * Weapons with this enchantment will attack automatically as long as the attack button is held down.
 */
public final class AutoswingEnchantment extends ConfigurableEnchantment {

    public AutoswingEnchantment() {
        super(Entranced.getConfig().getAutoswingRarity(), EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND);
    }

    @Override
    public boolean isTreasure() {
        return Entranced.getConfig().isAutoswingTreasure();
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return Entranced.getConfig().isAutoswingSoldByVillagers();
    }

    @Override
    public boolean isEnabled() {
        return Entranced.getConfig().isAutoswingEnabled();
    }

    @Override
    protected int minPower() {
        return Entranced.getConfig().getAutoswingMinPower();
    }

    @Override
    protected int maxPowerAboveMin() {
        return Entranced.getConfig().getAutoswingMaxPowerAboveMin();
    }

    @Override
    public @NotNull String getPath() {
        return "autoswing";
    }
}
