package io.github.shaksternano.entranced.commonside.enchantment;

import io.github.shaksternano.entranced.commonside.Entranced;
import io.github.shaksternano.entranced.commonside.config.ImperishableBlacklists;
import io.github.shaksternano.entranced.commonside.registry.EntrancedEnchantments;
import io.github.shaksternano.entranced.commonside.util.EnchantmentUtil;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.jetbrains.annotations.NotNull;

/**
 * Prevents items from despawning or being destroyed. Additionally, if a tool with this enchantment reaches 0 durability,
 * it won't break, but it will lose any special properties instead. Repairing the tool will restore those properties.
 * For example, a pickaxe with this enchantment at 0 durability will lose it's increased mining speed.
 */
public final class ImperishableEnchantment extends ConfigurableEnchantment {

    public ImperishableEnchantment() {
        super(Entranced.getConfig().getImperishableRarity(), EnchantmentTarget.VANISHABLE, EquipmentSlot.values());
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }


    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return !ImperishableBlacklists.isItemBlacklistedGlobally(stack) && super.isAcceptableItem(stack);
    }

    @Override
    public boolean isTreasure() {
        return Entranced.getConfig().isImperishableTreasure();
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return Entranced.getConfig().isImperishableSoldByVillagers();
    }

    @Override
    public boolean isEnabled() {
        return Entranced.getConfig().isImperishableEnabled();
    }

    @Override
    protected int minPower() {
        return Entranced.getConfig().getImperishableMinPower();
    }

    @Override
    protected int maxPowerAboveMin() {
        return Entranced.getConfig().getImperishableMaxPowerAboveMin();
    }

    @Override
    public @NotNull String getId() {
        return "imperishable";
    }

    // Removes the "(Broken)" string from the name of tools with Imperishable at 0 durability, so it doesn't mess with anvil renaming.
    public static String itemNameRemoveBroken(String name, ItemStack stack) {
        if (ImperishableBlacklists.isItemProtected(stack, ImperishableBlacklists.ProtectionType.BREAK_PROTECTION)) {
            if (EnchantmentUtil.isBrokenImperishable(stack)) {
                Text broken = new TranslatableText("item.name." + EntrancedEnchantments.IMPERISHABLE.getTranslationKey() + ".broken");
                return name.replace(broken.getString(), "");
            }
        }

        return name;
    }
}
