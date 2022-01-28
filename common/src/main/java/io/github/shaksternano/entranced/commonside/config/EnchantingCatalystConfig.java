package io.github.shaksternano.entranced.commonside.config;

import com.google.common.collect.Multimap;
import io.github.shaksternano.entranced.commonside.Entranced;
import io.github.shaksternano.entranced.commonside.util.CollectionUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Supplier;

public enum EnchantingCatalystConfig {

    // This is an enum.
    INSTANCE;

    private final Multimap<EnchantingCatalystType, Enchantment> catalystAffectedEnchantments = CollectionUtil.createEnumSetMultimap(EnchantingCatalystType.class);
    private final Multimap<EnchantingCatalystType, Item> catalystConsumableItems = CollectionUtil.createEnumSetMultimap(EnchantingCatalystType.class);
    private final Multimap<EnchantingCatalystType, Item> catalystUnconsumableItems = CollectionUtil.createEnumSetMultimap(EnchantingCatalystType.class);

    public void updateCatalystConfigCollections() {
        catalystAffectedEnchantments.clear();
        catalystConsumableItems.clear();
        catalystUnconsumableItems.clear();

        for (EnchantingCatalystType catalystType : EnchantingCatalystType.values()) {
            for (String enchantmentId : catalystType.AFFECTED_ENCHANTMENTS_LIST_GETTER.get()) {
                CollectionUtil.addEnchantmentToCollection(enchantmentId, catalystAffectedEnchantments.get(catalystType));
            }

            for (String itemId : catalystType.CATALYST_CONSUMABLE_ITEMS_LIST_GETTER.get()) {
                CollectionUtil.addItemToCollection(itemId, catalystConsumableItems.get(catalystType));
            }

            for (String itemId : catalystType.CATALYST_UNCONSUMABLE_ITEMS_LIST_GETTER.get()) {
                CollectionUtil.addItemToCollection(itemId, catalystUnconsumableItems.get(catalystType));
            }
        }
    }

    public boolean isCatalystAffected(EnchantingCatalystType catalystType, Enchantment enchantment) {
        return catalystAffectedEnchantments.containsEntry(catalystType, enchantment);
    }

    public boolean isCatalystConsumable(EnchantingCatalystType catalystType, Item item) {
        return catalystConsumableItems.containsEntry(catalystType, item);
    }

    public boolean isCatalystUnconsumable(EnchantingCatalystType catalystType, Item item) {
        return catalystUnconsumableItems.containsEntry(catalystType, item);
    }

    public enum EnchantingCatalystType {

        MELEE(Entranced.getConfig()::getMeleeCatalystAffectedEnchantments, Entranced.getConfig()::getMeleeCatalystConsumableItems, Entranced.getConfig()::getMeleeCatalystUnconsumableItems),
        RANGED(Entranced.getConfig()::getRangedCatalystAffectedEnchantments, Entranced.getConfig()::getRangedCatalystConsumableItems, Entranced.getConfig()::getRangedCatalystUnconsumableItems),
        DEFENSIVE(Entranced.getConfig()::getDefensiveCatalystAffectedEnchantments, Entranced.getConfig()::getDefensiveCatalystConsumableItems, Entranced.getConfig()::getDefensiveCatalystUnconsumableItems),
        MAGIC(Entranced.getConfig()::getMagicCatalystAffectedEnchantments, Entranced.getConfig()::getMagicCatalystConsumableItems, Entranced.getConfig()::getMagicCatalystUnconsumableItems),
        UTILITY(Entranced.getConfig()::getUtilityCatalystAffectedEnchantments, Entranced.getConfig()::getUtilityCatalystConsumableItems, Entranced.getConfig()::getUtilityCatalystUnconsumableItems),
        MISC(Entranced.getConfig()::getMiscCatalystAffectedEnchantments, Entranced.getConfig()::getMiscCatalystConsumableItems, Entranced.getConfig()::getMiscCatalystUnconsumableItems);

        @NotNull
        private final Supplier<List<String>> AFFECTED_ENCHANTMENTS_LIST_GETTER;
        @NotNull
        private final Supplier<List<String>> CATALYST_CONSUMABLE_ITEMS_LIST_GETTER;
        @NotNull
        private final Supplier<List<String>> CATALYST_UNCONSUMABLE_ITEMS_LIST_GETTER;

        EnchantingCatalystType(@NotNull Supplier<List<String>> affectedEnchantmentsListGetter, @NotNull Supplier<List<String>> catalystConsumableItemsListGetter, @NotNull Supplier<List<String>> catalystUnconsumableItemsListGetter) {
            AFFECTED_ENCHANTMENTS_LIST_GETTER = affectedEnchantmentsListGetter;
            CATALYST_CONSUMABLE_ITEMS_LIST_GETTER = catalystConsumableItemsListGetter;
            CATALYST_UNCONSUMABLE_ITEMS_LIST_GETTER = catalystUnconsumableItemsListGetter;
        }
    }
}
