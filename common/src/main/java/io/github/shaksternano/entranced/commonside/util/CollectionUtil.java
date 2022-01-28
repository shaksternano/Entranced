package io.github.shaksternano.entranced.commonside.util;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import io.github.shaksternano.entranced.commonside.Entranced;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.InvalidIdentifierException;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public final class CollectionUtil {

    private CollectionUtil() {}
    public static <K extends Enum<K>, V> Multimap<K, V> createEnumSetMultimap(Class<K> keyClass) {
        return MultimapBuilder.enumKeys(keyClass).hashSetValues().build();
    }

    /**
     * Adds the {@link Item} with the corresponding item ID to the collection.
     */
    public static void addItemToCollection(String itemID, Collection<Item> items) {
        addToCollection(itemID, items, Registry.ITEM, Items.AIR, "item");
    }

    /**
     * Adds the {@link Block} with the corresponding block ID to the collection.
     */
    public static void addBlockToCollection(String blockID, Collection<Block> blocks) {
        addToCollection(blockID, blocks, Registry.BLOCK, Blocks.AIR, "block");
    }

    /**
     * Adds the {@link Fluid} with the corresponding fluid ID to the collection.
     */
    public static void addFluidToCollection(String fluidId, Collection<Fluid> fluids) {
        addToCollection(fluidId, fluids, Registry.FLUID, Fluids.EMPTY, "fluid");
    }

    public static void addEnchantmentToCollection(String enchantmentId, Collection<Enchantment> enchantments) {
        addToCollection(enchantmentId, enchantments, Registry.ENCHANTMENT, null, "enchantment");
    }

    /**
     * Adds the registered object with the corresponding ID to the collection.
     */
    public static <E> void addToCollection(String id, Collection<E> collection, Registry<E> registry, @Nullable E defaultEntry, String idType) {
        if (defaultEntry == null && registry instanceof DefaultedRegistry<E>) {
            Entranced.LOGGER.warn("Passed a null default entry when looking up an element in a DefaultedRegistry");
            Thread.dumpStack();
        }

        try {
            E element = registry.get(new Identifier(id));
            // If the ID isn't valid then element will be the default entry.
            if (element != null && !element.equals(defaultEntry)) {
                try {
                    collection.add(element);
                } catch (Exception e) {
                    Entranced.LOGGER.warn("Could not add an element to the collection");
                    e.printStackTrace();
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
        Entranced.LOGGER.warn("\"" + id + "\" in the " + Entranced.MOD_ID + ".json5 config file is not a valid " + idType + " ID!");
    }
}
