package io.github.shaksternano.entranced.commonside.config;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;

public enum EnchantingCatalystConfig {

    INSTANCE;

    private final Multimap<EnchantingCatalystType, Enchantment> catalystAffectedEnchantments = MultimapBuilder.enumKeys(EnchantingCatalystType.class).hashSetValues().build();

    private final Multimap<EnchantingCatalystType, Item> catalystConsumableItems = MultimapBuilder.enumKeys(EnchantingCatalystType.class).hashSetValues().build();

    private final Multimap<EnchantingCatalystType, Item> catalystUnconsumableItems = MultimapBuilder.enumKeys(EnchantingCatalystType.class).hashSetValues().build();

    public void updateCatalystConfig() {
        
    }

    public enum EnchantingCatalystType {

        OFFENSIVE,
        DEFENSIVE,
        UTILITY;
    }
}
