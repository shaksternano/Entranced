package io.github.shaksternano.entranced.commonside.config;

import io.github.shaksternano.entranced.commonside.Entranced;
import io.github.shaksternano.entranced.commonside.util.CollectionUtil;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;

import java.util.HashSet;
import java.util.Set;

public enum InfinityBucketWhitelists {

    INSTANCE;

    private final Set<Fluid> fluidWhitelist = new HashSet<>();
    private final Set<Block> blockWhitelist = new HashSet<>();
    private final Set<Item> itemWhitelist = new HashSet<>();

    /**
     * Produces Item whitelists from the Item ID string whitelists in the {@link EntrancedConfig}.
     */
    public void updateWhitelists() {
        fluidWhitelist.clear();
        blockWhitelist.clear();
        itemWhitelist.clear();

        for (String fluidId : Entranced.INSTANCE.getConfig().getInfinityFluidWhitelist()) {
            CollectionUtil.addFluidToCollection(fluidId, fluidWhitelist);
        }

        for (String blockId : Entranced.INSTANCE.getConfig().getInfinityBlockWhitelist()) {
            CollectionUtil.addBlockToCollection(blockId, blockWhitelist);
        }

        for (String itemId : Entranced.INSTANCE.getConfig().getInfinityItemWhitelist()) {
            CollectionUtil.addItemToCollection(itemId, itemWhitelist);
        }
    }

    /**
     * @return True if a fluid is whitelisted. Otherwise, returns false.
     */
    public boolean isFluidWhitelisted(Fluid fluid) {
        return fluidWhitelist.contains(fluid);
    }

    /**
     * @return True if a block is whitelisted. Otherwise, returns false.
     */
    public boolean isBlockWhitelisted(Block block) {
        return blockWhitelist.contains(block);
    }

    /**
     * @return True if an item is whitelisted. Otherwise, returns false.
     */
    public boolean isItemWhitelisted(Item item) {
        return itemWhitelist.contains(item);
    }
}
