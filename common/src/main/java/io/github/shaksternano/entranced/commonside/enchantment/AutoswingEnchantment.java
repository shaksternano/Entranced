package io.github.shaksternano.entranced.commonside.enchantment;

import io.github.shaksternano.entranced.commonside.Entranced;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

/**
 * Weapons with this enchantment will attack automatically as long as the attack button is held down.
 */
public class AutoswingEnchantment extends ConfigurableEnchantment {

    public AutoswingEnchantment() {
        super(Entranced.INSTANCE.getConfig().getAutoswingRarity(), EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND);
    }

    @Override
    public boolean isTreasure() {
        return Entranced.INSTANCE.getConfig().isAutoswingTreasure();
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return Entranced.INSTANCE.getConfig().isAutoswingSoldByVillagers();
    }

    @Override
    public boolean isEnabled() {
        return Entranced.INSTANCE.getConfig().isAutoswingEnabled();
    }

    @Override
    protected int minPower() {
        return Entranced.INSTANCE.getConfig().getAutoswingMinPower();
    }

    @Override
    protected int maxPowerAboveMin() {
        return Entranced.INSTANCE.getConfig().getAutoswingMaxPowerAboveMin();
    }

    @Override
    public String getPath() {
        return "autoswing";
    }
}
