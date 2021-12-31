package io.github.shaksternano.entranced.commonside.config;

public final class EnchantmentAllowLists {

    private EnchantmentAllowLists() {}

    // Initialises all enchantment whitelists and blacklists.
    public static void initAllowLists() {
        initWhitelists();
        initBlacklists();
    }

    // Initialises all enchantment whitelists.
    private static void initWhitelists() {
        InfinityBucketWhitelists.initWhitelists();
    }

    // Initialises all enchantment blacklists.
    private static void initBlacklists() {
        ImperishableBlacklists.initBlacklists();
    }
}
