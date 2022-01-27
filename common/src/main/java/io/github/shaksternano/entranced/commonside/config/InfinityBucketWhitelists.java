package io.github.shaksternano.entranced.commonside.config;

import io.github.shaksternano.entranced.commonside.Entranced;
import io.github.shaksternano.entranced.commonside.util.CollectionUtil;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;

import java.util.HashSet;
import java.util.Set;

public final class InfinityBucketWhitelists {

    private InfinityBucketWhitelists() {}

    private static final Set<Fluid> fluidWhitelist = new HashSet<>();
    private static final Set<Block> blockWhitelist = new HashSet<>();
    private static final Set<Item> itemWhitelist = new HashSet<>();

    /**
     * Produces Item whitelists from the Item ID string whitelists in the {@link EntrancedConfig}.
     */
    public static void updateWhitelists() {
        fluidWhitelist.clear();
        blockWhitelist.clear();
        itemWhitelist.clear();

        for (String fluidId : Entranced.getConfig().getInfinityFluidWhitelist()) {
            CollectionUtil.addFluidToCollection(fluidId, fluidWhitelist);
        }

        for (String blockId : Entranced.getConfig().getInfinityBlockWhitelist()) {
            CollectionUtil.addBlockToCollection(blockId, blockWhitelist);
        }

        for (String itemId : Entranced.getConfig().getInfinityItemWhitelist()) {
            CollectionUtil.addItemToCollection(itemId, itemWhitelist);
        }
    }

    /**
     * @return True if a fluid is whitelisted. Otherwise, returns false.
     * The water check is to allow water to be set as whitelisted by default.
     * Putting water in the {@link EntrancedConfig} list by default causes a new "minecraft:water"
     * entry to be added to the {@link EntrancedConfig} whitelist everytime the game is launched.
     */
    public static boolean isFluidWhitelisted(Fluid fluid) {
        return fluidWhitelist.contains(fluid);
    }

    /**
     * @return True if a block is whitelisted. Otherwise, returns false.
     */
    public static boolean isBlockWhitelisted(Block block) {
        return blockWhitelist.contains(block);
    }

    /**
     * @return True if an item is whitelisted. Otherwise, returns false.
     */
    public static boolean isItemWhitelisted(Item item) {
        return itemWhitelist.contains(item);
    }
}
