package io.github.shaksternano.entranced.commonside.config;

import com.google.common.collect.SetMultimap;
import io.github.shaksternano.entranced.commonside.Entranced;
import io.github.shaksternano.entranced.commonside.util.CollectionUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public enum ImperishableBlacklists {

    INSTANCE;

    private final Set<Item> globalBlacklist = new HashSet<>();
    private final SetMultimap<ProtectionType, Item> blacklists = CollectionUtil.createEnumMultimap(ProtectionType.class);

    /**
     * Produces Item blacklists from the Item ID String blacklists in the {@link EntrancedConfig}.
     */
    public void updateBlacklists() {
        globalBlacklist.clear();

        for (String itemId : Entranced.INSTANCE.getConfig().getImperishableGlobalBlacklist()) {
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
     * @return True if the item is on the global blacklist, otherwise, returns false.
     */
    public boolean isItemBlacklistedGlobally(Item item) {
        return globalBlacklist.contains(item);
    }

    /**
     * Convenience method for {@link #isItemBlacklistedGlobally(Item item)}
     */
    public boolean isItemBlacklistedGlobally(ItemStack stack) {
        return isItemBlacklistedGlobally(stack.getItem());
    }

    /**
     * @return True if an item is on the global blacklist or the blacklist
     * for the specified {@link ProtectionType}. Otherwise, returns false.
     */
    private boolean isItemBlacklisted(Item item, ProtectionType protectionType) {
        return isItemBlacklistedGlobally(item) || blacklists.containsEntry(protectionType, item);
    }

    /**
     * @return True if the specified item is not blacklisted and Imperishable is set
     * to protect from the specified {@link ProtectionType}. Otherwise, returns false.
     */
    public boolean isItemProtected(Item item, ProtectionType protectionType) {
        return protectionType.PROTECTION_ENABLED && !isItemBlacklisted(item, protectionType);
    }

    /**
     * Convenience method for {@link #isItemProtected(Item item, ProtectionType protectionType)}.
     */
    public boolean isItemProtected(ItemStack stack, ProtectionType protectionType) {
        return isItemProtected(stack.getItem(), protectionType);
    }

    /**
     * Protection types and their associated item ID blacklists
     * from config and boolean enabled value from config.
     */
    public enum ProtectionType {
        DESPAWN_PROTECTION(Entranced.INSTANCE.getConfig()::getImperishableDespawnProtectionBlacklist, Entranced.INSTANCE.getConfig().isImperishablePreventsDespawn()),
        DAMAGE_PROTECTION(Entranced.INSTANCE.getConfig()::getImperishableDamageProtectionBlacklist, Entranced.INSTANCE.getConfig().isImperishableProtectsFromDamage()),
        VOID_PROTECTION(Entranced.INSTANCE.getConfig()::getImperishableVoidProtectionBlacklist, Entranced.INSTANCE.getConfig().isImperishableProtectsFromVoid()),
        BREAK_PROTECTION(Entranced.INSTANCE.getConfig()::getImperishableBreakProtectionBlacklist, Entranced.INSTANCE.getConfig().isImperishablePreventsBreaking());

        @NotNull
        private final Supplier<List<String>> ITEM_ID_BLACKLIST_GETTER;
        private final boolean PROTECTION_ENABLED;

        ProtectionType(@NotNull Supplier<List<String>> itemIdBlacklistGetter, boolean protectionEnabled) {
            ITEM_ID_BLACKLIST_GETTER = itemIdBlacklistGetter;
            PROTECTION_ENABLED = protectionEnabled;
        }
    }
}
