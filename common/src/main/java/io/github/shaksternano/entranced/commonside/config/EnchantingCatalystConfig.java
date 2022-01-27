package io.github.shaksternano.entranced.commonside.config;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import io.github.shaksternano.entranced.commonside.util.CollectionUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;

import java.util.Map;
import java.util.Set;

public final class EnchantingCatalystConfig {

    private EnchantingCatalystConfig() {}

    private static final Multimap<EnchantingCatalystType, Enchantment> catalystAffectedEnchantments = MultimapBuilder.enumKeys(EnchantingCatalystType.class).hashSetValues().build();

    private static final Multimap<EnchantingCatalystType, Item> catalystConsumableItems = MultimapBuilder.enumKeys(EnchantingCatalystType.class).hashSetValues().build();

    private static final Multimap<EnchantingCatalystType, Item> catalystUnconsumableItems = MultimapBuilder.enumKeys(EnchantingCatalystType.class).hashSetValues().build();

    public static void updateCatalystConfig() {
        
    }

    public enum EnchantingCatalystType {

        OFFENSIVE,
        DEFENSIVE,
        UTILITY
    }
}
