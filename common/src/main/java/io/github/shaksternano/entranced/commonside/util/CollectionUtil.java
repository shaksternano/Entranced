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
import org.jetbrains.annotations.Nullable;

import java.util.*;

public final class CollectionUtil {

    private CollectionUtil() {}

    /**
     * @return A {@link Map} mapping {@link Enum} keys specified in the {@code keyArray} to {@link Set}s.
     */
    public static <K extends Enum<K>, V> Map<K, Set<V>> initEnumSetMap(Class<K> keyType, K[] keyArray) {
        Map<K, Set<V>> setMap = new EnumMap<>(keyType);

        for (K key : keyArray) {
            setMap.put(key, new HashSet<>());
        }

        return setMap;
    }

    /**
     * Adds the {@link Item} with the corresponding item ID to the collection.
     */
    public static void addItemToCollection(String itemID, Collection<Item> itemCollection) {
        addToCollection(itemID, itemCollection, Registry.ITEM, Items.AIR, "item");
    }

    /**
     * Adds the {@link Block} with the corresponding block ID to the collection.
     */
    public static void addBlockToCollection(String blockID, Collection<Block> blockCollection) {
        addToCollection(blockID, blockCollection, Registry.BLOCK, Blocks.AIR, "block");
    }

    /**
     * Adds the {@link Fluid} with the corresponding fluid ID to the collection.
     */
    public static void addFluidToCollection(String fluidId, Collection<Fluid> fluidCollection) {
        addToCollection(fluidId, fluidCollection, Registry.FLUID, Fluids.EMPTY, "fluid");
    }

    /**
     * Adds the registered object with the corresponding ID to the collection.
     */
    private static <V> void addToCollection(String id, Collection<V> collection, Registry<V> registry, @Nullable V defaultEntry, String idType) {
        try {
            V v = registry.get(new Identifier(id));
            // If the ID isn't valid then t will be the default entry.
            if (v != null && !v.equals(defaultEntry)) {
                try {
                    collection.add(v);
                } catch (Exception e) {
                    Entranced.LOGGER.warn("Could not add an element to the Collection passed to io.github.shaksternano.entranced.commonside.util.CollectionUtil#addToCollection due to:\n" + e);
                }
            } else {
                notifyInvalidId(id, idType);
            }
        } catch (InvalidIdentifierException e) {
            notifyInvalidId(id, idType);
        }
    }

    /**
     * Outputs and logs if there is an invalid {@link Identifier}.
     */
    private static void notifyInvalidId(String id, String idType) {
        Entranced.LOGGER.info("\"" + id + "\" in " + Entranced.MOD_ID + ".json5 is not a valid " + idType + " ID!");
    }
}
