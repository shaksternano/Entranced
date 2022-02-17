package io.github.shaksternano.entranced.commonside.util;

import io.github.shaksternano.entranced.mixin.commonloader.commonside.accessor.BucketItemAccessor;
import net.minecraft.enchantment.InfinityEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.*;

import java.util.Optional;

public class BucketUtil {

    /**
     * @return True if the {@link  Item} is some kind of bucket. Returns false otherwise.
     */
    public static boolean isBucket(Item item) {
        return item instanceof BucketItem || item instanceof MilkBucketItem || item instanceof PowderSnowBucketItem;
    }

    /**
     * @return The bucket {@link Item} equivalent that doesn't contain an {@link Entity}.
     */
    public static Item getNoEntityBucket(EntityBucketItem entityBucketItem) {
        Fluid fluid = ((BucketItemAccessor) entityBucketItem).entranced$getFluid();
        Item bucketItem = fluid.getBucketItem();

        if (bucketItem == null) {
            bucketItem = Items.BUCKET;
        }

        return bucketItem;
    }

    /**
     * Bucket keeps its fluid if the {@link InfinityEnchantment} is set to affect buckets.
     */
    public static Optional<ItemStack> infinityBucketKeepFluid(ItemStack bucketStack) {
        if (EnchantmentUtil.isBucketAndHasInfinityAndBucketEnabled(bucketStack)) {
            Item bucketItem = bucketStack.getItem();

            if (bucketItem instanceof EntityBucketItem entityBucketItem) {
                ItemStack newBucketStack = new ItemStack(BucketUtil.getNoEntityBucket(entityBucketItem));
                EnchantmentUtil.copyEnchantments(bucketStack, newBucketStack);
                return Optional.of(newBucketStack);
            } else {
                return Optional.of(bucketStack);
            }
        } else {
            return Optional.empty();
        }
    }
}
