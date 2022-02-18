package io.github.shaksternano.entranced.commonside.config;

import io.github.shaksternano.entranced.commonside.Entranced;
import io.github.shaksternano.entranced.commonside.util.CollectionUtil;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.InfinityEnchantment;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.HashSet;
import java.util.Set;

public enum InfinityBucketWhitelists {

    INSTANCE;

    private final Set<Fluid> fluidWhitelist = new HashSet<>();
    private final Set<Block> blockWhitelist = new HashSet<>();
    private final Set<Item> itemWhitelist = new HashSet<>();

    /**
     * Updates the whitelist collections with values from the config.
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
     * Determines whether a {@link Fluid} is whitelisted to work with the {@link InfinityEnchantment},
     * for example, {@link Fluids#WATER}.
     * @param fluid The fluid to check
     * @return {@code true} if a fluid is whitelisted, {@code false} otherwise.
     */
    public boolean isFluidWhitelisted(Fluid fluid) {
        return fluidWhitelist.contains(fluid);
    }

    /**
     * Determines whether a {@link Block} is whitelisted to work with the {@link InfinityEnchantment},
     * for example, {@link Blocks#POWDER_SNOW}.
     * @param block The block to check
     * @return {@code true} if a block is whitelisted, {@code false} otherwise.
     */
    public boolean isBlockWhitelisted(Block block) {
        return blockWhitelist.contains(block);
    }

    /**
     * Determines whether an {@link Item} is whitelisted to work with the {@link InfinityEnchantment},
     * for example, {@link Items#MILK_BUCKET}.
     * @param item The item to check
     * @return {@code true} if an item is whitelisted, {@code false} otherwise.
     */
    public boolean isItemWhitelisted(Item item) {
        return itemWhitelist.contains(item);
    }
}
