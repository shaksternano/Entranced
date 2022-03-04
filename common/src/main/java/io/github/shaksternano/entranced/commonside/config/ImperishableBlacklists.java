package io.github.shaksternano.entranced.commonside.config;

import com.google.common.collect.SetMultimap;
import io.github.shaksternano.entranced.commonside.Entranced;
import io.github.shaksternano.entranced.commonside.enchantment.ImperishableEnchantment;
import io.github.shaksternano.entranced.commonside.util.CollectionUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class ImperishableBlacklists {

    private static final Set<Item> globalBlacklist = new HashSet<>();
    private static final SetMultimap<ProtectionType, Item> blacklists = CollectionUtil.createEnumMultimap(ProtectionType.class);

    /**
     * Produces Item blacklists from the Item ID String blacklists in the {@link EntrancedConfig}.
     */
    public static void updateBlacklists() {
        globalBlacklist.clear();

        for (String itemId : Entranced.getConfig().getImperishableGlobalBlacklist()) {
            CollectionUtil.addItemToCollection(itemId, globalBlacklist);
        }

        blacklists.clear();

        for (ProtectionType protectionType : ProtectionType.values()) {
            for (String itemId : protectionType.ITEM_ID_BLACKLIST_GETTER.get()) {
                CollectionUtil.addItemToCollection(itemId, blacklists.get(protectionType));
            }
        }
    }

    /**
     * Determines whether an {@link Item} is blacklisted from being protected by the
     * {@link ImperishableEnchantment} or not.
     * @param item The item to check.
     * @return {@code true} if the item is blacklisted, {@code false} otherwise.
     */
    public static boolean isItemBlacklistedGlobally(Item item) {
        return globalBlacklist.contains(item);
    }

    /**
     * Determines whether an {@link ItemStack} is blacklisted from being protected by the
     * {@link ImperishableEnchantment} or not.
     * @param stack The item stack to check.
     * @return {@code true} if the item stack is blacklisted, {@code false} otherwise.
     */
    public static boolean isItemBlacklistedGlobally(ItemStack stack) {
        return isItemBlacklistedGlobally(stack.getItem());
    }

    /**
     * Determines whether an {@link Item} is blacklisted from being protected by the
     * {@link ImperishableEnchantment} for a certain {@link ProtectionType} or not.
     * @param item The item to check.
     * @param protectionType The protection type to check for.
     * @return {@code true} if an item is on the blacklist for
     * the specified {@link ProtectionType}, {@code false} otherwise.
     */
    private static boolean isItemBlacklisted(Item item, ProtectionType protectionType) {
        return isItemBlacklistedGlobally(item) || blacklists.containsEntry(protectionType, item);
    }

    /**
     * Determines whether the {@link ImperishableEnchantment} protects an {@link Item} from a certain {@link ProtectionType} or not.
     * @param item The item to check.
     * @param protectionType The protection type to check for.
     * @return {@code true} if the Imperishable enchantment protects the item for the specified protection type, {@code false} otherwise.
     */
    public static boolean isItemProtected(Item item, ProtectionType protectionType) {
        return protectionType.PROTECTION_ENABLED && !isItemBlacklisted(item, protectionType);
    }

    /**
     * Determines whether the {@link ImperishableEnchantment} protects an {@link ItemStack} from a certain {@link ProtectionType} or not.
     * @param stack The item stack to check.
     * @param protectionType The protection type to check for.
     * @return {@code true} if the Imperishable enchantment protects the item stack for the specified protection type, {@code false} otherwise.
     */
    public static boolean isItemProtected(ItemStack stack, ProtectionType protectionType) {
        return isItemProtected(stack.getItem(), protectionType);
    }

    /**
     * Protection types and their associated item ID blacklists
     * from config and boolean enabled value from config.
     */
    public enum ProtectionType {
        DESPAWN_PROTECTION(Entranced.getConfig()::getImperishableDespawnProtectionBlacklist, Entranced.getConfig().isImperishablePreventsDespawn()),
        DAMAGE_PROTECTION(Entranced.getConfig()::getImperishableDamageProtectionBlacklist, Entranced.getConfig().isImperishableProtectsFromDamage()),
        VOID_PROTECTION(Entranced.getConfig()::getImperishableVoidProtectionBlacklist, Entranced.getConfig().isImperishableProtectsFromVoid()),
        BREAK_PROTECTION(Entranced.getConfig()::getImperishableBreakProtectionBlacklist, Entranced.getConfig().isImperishablePreventsBreaking());

        private final Supplier<List<String>> ITEM_ID_BLACKLIST_GETTER;
        private final boolean PROTECTION_ENABLED;

        ProtectionType(Supplier<List<String>> itemIdBlacklistGetter, boolean protectionEnabled) {
            ITEM_ID_BLACKLIST_GETTER = itemIdBlacklistGetter;
            PROTECTION_ENABLED = protectionEnabled;
        }
    }
}
