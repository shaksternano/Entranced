package io.github.shaksternano.entranced.commonside.config;

import com.google.common.collect.SetMultimap;
import io.github.shaksternano.entranced.commonside.Entranced;
import io.github.shaksternano.entranced.commonside.util.CollectionUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
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

    /**
     * Updates the enchanting catalyst collections with values from the config.
     */
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

    /**
     * Sets an item to be an enchanting catalyst.
     * @param itemId The ID of the item.
     * @param catalystType The type of enchantments the item will affect.
     * @param consumable Whether the item is consumed when used or not.
     */
    private void addCatalystItem(String itemId, EnchantingCatalystType catalystType, boolean consumable) {
        Item item = Registry.ITEM.get(Identifier.tryParse(itemId));

        if (!item.equals(Items.AIR)) {
            catalystItems.put(item, new EnchantingCatalyst(catalystType, consumable));
        } else {
            CollectionUtil.notifyInvalidId(itemId, "item");
        }
    }

    /**
     * Determines whether an {@link Enchantment} is filtered out by the enchanting catalyst or not.
     * @param catalystType The enchanting catalyst being used.
     * @param enchantment The enchantment to check.
     * @return {@code true} if enchantment is not filtered out, {@code false} otherwise.
     */
    public boolean isCatalystAllowed(EnchantingCatalystType catalystType, Enchantment enchantment) {
        return catalystAffectedEnchantments.containsEntry(catalystType, enchantment);
    }

    /**
     * Determines whether an {@link Item} is an enchanting catalyst or not.
     * @param item The item to check.
     * @return {@code true} if the item is an enchanting catalyst, false otherwise.
     */
    public boolean isCatalyst(Item item) {
        return catalystItems.containsKey(item);
    }

    /**
     * Determines whether an {@link ItemStack} is an enchanting catalyst or not.
     * @param stack The item stack to check.
     * @return {@code true} if the item stack is an enchanting catalyst, false otherwise.
     */
    public boolean isCatalyst(ItemStack stack) {
        return isCatalyst(stack.getItem());
    }

    /**
     * Gets the enchanting catalyst type of an item.
     * @param item The item to get the enchanting catalyst type of.
     * @return An {@link Optional} describing the enchanting catalyst type.
     */
    public Optional<EnchantingCatalyst> getCatalystType(Item item) {
        return Optional.ofNullable(catalystItems.get(item));
    }

    /**
     * The different types of enchanting catalyst. Each type will filter out a different set of {@link Enchantment}s.
     */
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

    /**
     * For associating an {@link Item} with an {@link EnchantingCatalystType}, and determining whether the item is consumed when used or not.
     */
    public record EnchantingCatalyst(EnchantingCatalystType catalystType, boolean catalystConsumed) {}
}
