package io.github.shaksternano.entranced.commonside.config;

import io.github.shaksternano.entranced.commonside.Entranced;
import io.github.shaksternano.entranced.commonside.util.CollectionUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Supplier;

public final class ImperishableBlacklists {

    private ImperishableBlacklists() {}

    private static final Set<Item> globalBlacklist = new HashSet<>();
    private static final Map<ProtectionType, Set<Item>> blacklists = initBlacklistsMap();

    /**
     * Creates a blacklist for every ProtectionType
     */
    private static Map<ProtectionType, Set<Item>> initBlacklistsMap() {
        Map<ProtectionType, Set<Item>> blacklists = new HashMap<>();
        for (ProtectionType protectionType : ProtectionType.values()) {
            blacklists.put(protectionType, new HashSet<>());
        }
        return blacklists;
    }

    /**
     * Produces Item blacklists from the Item ID String blacklists in the config.
     */
    public static void initBlacklists() {
        globalBlacklist.clear();
        for (String itemId : Entranced.getConfig().getImperishableGlobalBlacklist()) {
            CollectionUtil.addItemToSet(itemId, globalBlacklist);
        }

        for (Map.Entry<ProtectionType, Set<Item>> entry : blacklists.entrySet()) {
            ProtectionType protectionType = entry.getKey();
            Set<Item> blacklist = entry.getValue();

            blacklist.clear();
            for (String itemId : protectionType.ITEM_ID_BLACKLIST_GETTER.get()) {
                CollectionUtil.addItemToSet(itemId, blacklist);
            }
        }
    }

    /**
     * @return True if the item is on the global blacklist, otherwise, returns false.
     */
    public static boolean isItemBlacklistedGlobally(Item item) {
        return globalBlacklist.contains(item);
    }

    /**
     * Convenience method for {@link ImperishableBlacklists#isItemBlacklistedGlobally(Item)}
     */
    public static boolean isItemBlacklistedGlobally(ItemStack stack) {
        return isItemBlacklistedGlobally(stack.getItem());
    }

    /**
     * @return True if an item is on the global blacklist or the blacklist
     * for the specified protection type. Otherwise, returns false.
     */
    private static boolean isItemBlacklisted(Item item, ProtectionType protectionType) {
        if (isItemBlacklistedGlobally(item)) {
            return true;
        } else {
            Set<Item> blacklist = blacklists.get(protectionType);
            if (blacklist != null) {
                return blacklist.contains(item);
            }

            return false;
        }
    }

    /**
     * @return True if the specified item is not blacklisted and Imperishable is set
     * to protect from the specified protection type. Otherwise, returns false.
     */
    public static boolean isItemProtected(Item item, ProtectionType protectionType) {
        return protectionType.PROTECTION_ENABLED && !isItemBlacklisted(item, protectionType);
    }

    /**
     * Convenience method for {@link ImperishableBlacklists#isItemProtected(Item, ProtectionType)}.
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

        @NotNull
        private final Supplier<List<String>> ITEM_ID_BLACKLIST_GETTER;
        private final boolean PROTECTION_ENABLED;

        ProtectionType(@NotNull Supplier<List<String>> itemIdBlacklistGetter, boolean protectionEnabled) {
            ITEM_ID_BLACKLIST_GETTER = itemIdBlacklistGetter;
            PROTECTION_ENABLED = protectionEnabled;
        }
    }
}
