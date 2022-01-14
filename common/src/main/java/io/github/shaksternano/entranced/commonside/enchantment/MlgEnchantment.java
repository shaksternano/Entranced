package io.github.shaksternano.entranced.commonside.enchantment;

import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.FluidModificationItem;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class MlgEnchantment extends ConfigurableEnchantment {

    public MlgEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.VANISHABLE, EquipmentSlot.values());
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() instanceof FluidModificationItem;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    protected int minPower() {
        return 10;
    }

    @Override
    protected int maxPowerAboveMin() {
        return 50;
    }

    @Override
    public @NotNull String getId() {
        return "mlg";
    }
}
