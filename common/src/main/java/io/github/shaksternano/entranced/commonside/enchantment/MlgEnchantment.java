package io.github.shaksternano.entranced.commonside.enchantment;

import io.github.shaksternano.entranced.commonside.Entranced;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.FluidModificationItem;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class MlgEnchantment extends ConfigurableEnchantment {

    public MlgEnchantment() {
        super(Entranced.getConfig().getMlgRarity(), EnchantmentTarget.VANISHABLE, EquipmentSlot.values());
    }

    @Override
    public boolean isTreasure() {
        return Entranced.getConfig().isMlgTreasure();
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return Entranced.getConfig().isMlgSoldByVillagers();
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() instanceof FluidModificationItem;
    }

    @Override
    public boolean isEnabled() {
        return Entranced.getConfig().isMlgEnabled();
    }

    @Override
    protected int minPower() {
        return Entranced.getConfig().getMlgMinPower();
    }

    @Override
    protected int maxPowerAboveMin() {
        return Entranced.getConfig().getMlgMaxPowerAboveMin();
    }

    @Override
    public @NotNull String getId() {
        return "mlg";
    }
}
