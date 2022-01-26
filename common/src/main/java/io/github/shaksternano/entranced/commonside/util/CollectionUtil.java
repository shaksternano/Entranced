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
     * Adds the {@link Item} with the corresponding item ID to the set.
     */
    public static void addItemToSet(String itemID, Set<Item> itemSet) {
        addToSet(itemID, itemSet, Registry.ITEM, Items.AIR, "item");
    }

    /**
     * Adds the {@link Block} with the corresponding block ID to the set.
     */
    public static void addBlockToSet(String blockID, Set<Block> blockSet) {
        addToSet(blockID, blockSet, Registry.BLOCK, Blocks.AIR, "block");
    }

    /**
     * Adds the {@link Fluid} with the corresponding fluid ID to the set.
     */
    public static void addFluidToSet(String fluidId, Set<Fluid> fluidSet) {
        addToSet(fluidId, fluidSet, Registry.FLUID, Fluids.EMPTY, "fluid");
    }

    /**
     * Adds the registered object with the corresponding ID to the set.
     */
    private static <V> void addToSet(String id, Set<V> set, Registry<V> registry, @Nullable V defaultEntry, String idType) {
        try {
            V v = registry.get(new Identifier(id));
            // If the ID isn't valid then t will be the default entry.
            if (v != null && !v.equals(defaultEntry)) {
                set.add(v);
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
