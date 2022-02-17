package io.github.shaksternano.entranced.commonside.enchantment;

import dev.architectury.injectables.annotations.ExpectPlatform;
import io.github.shaksternano.entranced.commonside.Entranced;
import io.github.shaksternano.entranced.commonside.config.ImperishableBlacklists;
import io.github.shaksternano.entranced.commonside.registry.EntrancedEnchantments;
import io.github.shaksternano.entranced.commonside.util.EnchantmentUtil;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

/**
 * Prevents items from despawning or being destroyed. Additionally, if a tool with this enchantment reaches 0 durability,
 * it won't break, but it will lose any special properties instead. Repairing the tool will restore those properties.
 * For example, a pickaxe with this enchantment at 0 durability will lose it's increased mining speed.
 */
public class ImperishableEnchantment extends ConfigurableEnchantment {

    public ImperishableEnchantment(EnchantmentTarget enchantmentTarget) {
        super(Entranced.INSTANCE.getConfig().getImperishableRarity(), enchantmentTarget, EquipmentSlot.values());
    }

    public ImperishableEnchantment() {
        this(EnchantmentTarget.VANISHABLE);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean isTreasure() {
        return Entranced.INSTANCE.getConfig().isImperishableTreasure();
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return Entranced.INSTANCE.getConfig().isImperishableSoldByVillagers();
    }

    @Override
    public boolean isEnabled() {
        return Entranced.INSTANCE.getConfig().isImperishableEnabled();
    }

    @Override
    protected int minPower() {
        return Entranced.INSTANCE.getConfig().getImperishableMinPower();
    }

    @Override
    protected int maxPowerAboveMin() {
        return Entranced.INSTANCE.getConfig().getImperishableMaxPowerAboveMin();
    }

    @Override
    public String getPath() {
        return "imperishable";
    }

    @ExpectPlatform
    public static ImperishableEnchantment newInstance() {
        throw new AssertionError();
    }

    // Removes the "(Broken)" string from the name of tools with Imperishable at 0 durability, so it doesn't mess with anvil renaming.
    public static String itemNameRemoveBroken(String name, ItemStack stack) {
        if (ImperishableBlacklists.INSTANCE.isItemProtected(stack, ImperishableBlacklists.ProtectionType.BREAK_PROTECTION)) {
            if (EnchantmentUtil.isBrokenImperishable(stack)) {
                Text broken = new TranslatableText("item.name." + EntrancedEnchantments.IMPERISHABLE.getTranslationKey() + ".broken");
                return name.replace(broken.getString(), "");
            }
        }

        return name;
    }
}
