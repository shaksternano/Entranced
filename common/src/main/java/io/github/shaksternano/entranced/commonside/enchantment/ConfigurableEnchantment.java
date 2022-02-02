package io.github.shaksternano.entranced.commonside.enchantment;

import io.github.shaksternano.entranced.commonside.Entranced;
import io.github.shaksternano.entranced.commonside.registry.EntrancedEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * For easier configuring of enchantments, and convenient ways of getting information like enchantment ID.
 */
public abstract class ConfigurableEnchantment extends Enchantment {

    public ConfigurableEnchantment(Rarity weight, EnchantmentTarget type, EquipmentSlot... slotTypes) {
        super(weight, type, slotTypes);

        if (isEnabled()) {
            EntrancedEnchantments.addEnchantmentToRegister(this);
        }
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return type == EnchantmentTarget.WEAPON && stack.getItem() instanceof AxeItem || super.isAcceptableItem(stack);
    }

    @Override
    public int getMinPower(int level) {
        return Math.max(minPower(), 0);
    }

    @Override
    public int getMaxPower(int level) {
        return getMinPower(level) + Math.max(maxPowerAboveMin(), 0);
    }

    public abstract boolean isEnabled();

    protected abstract int minPower();

    protected abstract int maxPowerAboveMin();

    @NotNull
    public abstract String getPath();

    public String getId() {
        return Entranced.MOD_ID + ":" + getPath();
    }
}
