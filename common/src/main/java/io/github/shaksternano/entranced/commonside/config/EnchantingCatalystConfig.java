package io.github.shaksternano.entranced.commonside.config;

import com.google.common.collect.SetMultimap;
import io.github.shaksternano.entranced.commonside.Entranced;
import io.github.shaksternano.entranced.commonside.util.CollectionUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.InvalidIdentifierException;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public enum EnchantingCatalystConfig {

    INSTANCE;

    private final SetMultimap<EnchantingCatalystType, Enchantment> catalystAffectedEnchantments = CollectionUtil.createEnumMultimap(EnchantingCatalystType.class);
    private final Map<Item, EnchantingCatalyst> catalystItems = new HashMap<>();

    public void updateCatalystConfigCollections() {
        catalystAffectedEnchantments.clear();
        catalystItems.clear();

        for (EnchantingCatalystType catalystType : EnchantingCatalystType.values()) {
            for (String enchantmentId : catalystType.AFFECTED_ENCHANTMENTS_LIST_GETTER.get()) {
                CollectionUtil.addEnchantmentToCollection(enchantmentId, catalystAffectedEnchantments.get(catalystType));
            }

            for (String itemId : catalystType.CATALYST_CONSUMABLE_ITEMS_LIST_GETTER.get()) {
                addCatalystItem(itemId, catalystType, true);
            }

            for (String itemId : catalystType.CATALYST_UNCONSUMABLE_ITEMS_LIST_GETTER.get()) {
                addCatalystItem(itemId, catalystType, false);
            }
        }
    }

    private void addCatalystItem(String itemId, EnchantingCatalystType catalystType, boolean consumable) {
        try {
            Item item = Registry.ITEM.get(new Identifier(itemId));

            if (!item.equals(Items.AIR)) {
                catalystItems.put(item, new EnchantingCatalyst(catalystType, consumable));
            } else {
                CollectionUtil.notifyInvalidId(itemId, "item");
            }
        } catch (InvalidIdentifierException e) {
            CollectionUtil.notifyInvalidId(itemId, "item");
        }
    }

    public boolean isCatalystAffected(EnchantingCatalystType catalystType, Enchantment enchantment) {
        return catalystAffectedEnchantments.containsEntry(catalystType, enchantment);
    }

    public boolean isCatalyst(Item item) {
        return catalystItems.containsKey(item);
    }

    public boolean isCatalyst(ItemStack stack) {
        return isCatalyst(stack.getItem());
    }

    public Optional<EnchantingCatalyst> getCatalystType(Item item) {
        return Optional.ofNullable(catalystItems.get(item));
    }

    public enum EnchantingCatalystType {

        MELEE(Entranced.INSTANCE.getConfig()::getMeleeCatalystAffectedEnchantments, Entranced.INSTANCE.getConfig()::getMeleeCatalystConsumableItems, Entranced.INSTANCE.getConfig()::getMeleeCatalystUnconsumableItems),
        RANGED(Entranced.INSTANCE.getConfig()::getRangedCatalystAffectedEnchantments, Entranced.INSTANCE.getConfig()::getRangedCatalystConsumableItems, Entranced.INSTANCE.getConfig()::getRangedCatalystUnconsumableItems),
        DEFENSIVE(Entranced.INSTANCE.getConfig()::getDefensiveCatalystAffectedEnchantments, Entranced.INSTANCE.getConfig()::getDefensiveCatalystConsumableItems, Entranced.INSTANCE.getConfig()::getDefensiveCatalystUnconsumableItems),
        MAGIC(Entranced.INSTANCE.getConfig()::getMagicCatalystAffectedEnchantments, Entranced.INSTANCE.getConfig()::getMagicCatalystConsumableItems, Entranced.INSTANCE.getConfig()::getMagicCatalystUnconsumableItems),
        UTILITY(Entranced.INSTANCE.getConfig()::getUtilityCatalystAffectedEnchantments, Entranced.INSTANCE.getConfig()::getUtilityCatalystConsumableItems, Entranced.INSTANCE.getConfig()::getUtilityCatalystUnconsumableItems),
        MISC(Entranced.INSTANCE.getConfig()::getMiscCatalystAffectedEnchantments, Entranced.INSTANCE.getConfig()::getMiscCatalystConsumableItems, Entranced.INSTANCE.getConfig()::getMiscCatalystUnconsumableItems);

        private final Supplier<List<String>> AFFECTED_ENCHANTMENTS_LIST_GETTER;
        private final Supplier<List<String>> CATALYST_CONSUMABLE_ITEMS_LIST_GETTER;
        private final Supplier<List<String>> CATALYST_UNCONSUMABLE_ITEMS_LIST_GETTER;

        EnchantingCatalystType(Supplier<List<String>> affectedEnchantmentsListGetter, Supplier<List<String>> catalystConsumableItemsListGetter, Supplier<List<String>> catalystUnconsumableItemsListGetter) {
            AFFECTED_ENCHANTMENTS_LIST_GETTER = affectedEnchantmentsListGetter;
            CATALYST_CONSUMABLE_ITEMS_LIST_GETTER = catalystConsumableItemsListGetter;
            CATALYST_UNCONSUMABLE_ITEMS_LIST_GETTER = catalystUnconsumableItemsListGetter;
        }
    }

    public record EnchantingCatalyst(EnchantingCatalystType catalystType, boolean catalystConsumed) {}
}
