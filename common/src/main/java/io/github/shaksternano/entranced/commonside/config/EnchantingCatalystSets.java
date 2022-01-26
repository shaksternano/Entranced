package io.github.shaksternano.entranced.commonside.config;

import io.github.shaksternano.entranced.commonside.util.CollectionUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;

import java.util.Map;
import java.util.Set;

public final class EnchantingCatalystSets {

    private EnchantingCatalystSets() {}

    private static final Map<EnchantingCatalystType, Set<Enchantment>> catalystAffectedEnchantments = CollectionUtil.initEnumSetMap(EnchantingCatalystType.class, EnchantingCatalystType.values());

    private static final Map<EnchantingCatalystType, Set<Item>> catalystConsumableItems = CollectionUtil.initEnumSetMap(EnchantingCatalystType.class, EnchantingCatalystType.values());

    private static final Map<EnchantingCatalystType, Set<Item>> catalystUnconsumableItems = CollectionUtil.initEnumSetMap(EnchantingCatalystType.class, EnchantingCatalystType.values());

    public static void updateCatalystSets() {
        for (Map.Entry<EnchantingCatalystType, Set<Enchantment>> entry : catalystAffectedEnchantments.entrySet()) {

        }
    }

    public enum EnchantingCatalystType {

        OFFENSIVE,
        DEFENSIVE,
        UTILITY
    }
}
