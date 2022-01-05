package io.github.shaksternano.entranced.commonside.util;

import io.github.shaksternano.entranced.commonside.Entranced;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.InvalidIdentifierException;
import net.minecraft.util.registry.Registry;

import java.util.Set;

public final class CollectionUtil {

    private CollectionUtil() {}

    // Adds the item with the corresponding item ID to the set.
    public static void addItemToSet(String itemID, Set<Item> itemSet) {
        addToSet(itemID, itemSet, Registry.ITEM, Items.AIR, "item");
    }

    // Adds the block with the corresponding block ID to the set.
    public static void addBlockToSet(String blockID, Set<Block> blockSet) {
        addToSet(blockID, blockSet, Registry.BLOCK, Blocks.AIR, "block");
    }

    // Adds the fluid with the corresponding fluid ID to the set.
    public static void addFluidToSet(String fluidId, Set<Fluid> fluidSet) {
        addToSet(fluidId, fluidSet, Registry.FLUID, Fluids.EMPTY, "fluid");
    }

    // Adds the registered object with the corresponding ID to the set.
    private static <T> void addToSet(String id, Set<T> set, Registry<T> registry, T defaultEntry, String idType) {
        try {
            T t = registry.get(new Identifier(id));
            // If the ID isn't valid then t will be the default entry.
            if (t != null && !t.equals(defaultEntry)) {
                set.add(t);
            } else {
                notifyInvalidId(id, idType);
            }
        } catch (InvalidIdentifierException e) {
            notifyInvalidId(id, idType);
        }
    }

    // Outputs and logs if there is an invalid Identifier.
    private static void notifyInvalidId(String id, String idType) {
        Entranced.LOGGER.info("\"" + id + "\" in " + Entranced.MOD_ID + ".json5 is not a valid " + idType + " ID!");
    }
}
