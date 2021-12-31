package io.github.shaksternano.entranced.commonside.util;

import io.github.shaksternano.entranced.mixin.commonloader.commonside.accessor.BucketItemAccessor;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.*;
import org.jetbrains.annotations.Nullable;

public final class BucketUtil {

    private BucketUtil() {}

    // Returns true if the Item is some kind of bucket. Returns false otherwise.
    public static boolean isBucket(Item item) {
        return item instanceof BucketItem || item instanceof MilkBucketItem || item instanceof PowderSnowBucketItem;
    }

    // Returns the bucket item equivalent that doesn't contain an entity.
    public static Item getNoEntityBucket(EntityBucketItem entityBucketItem) {
        Fluid fluid = ((BucketItemAccessor) entityBucketItem).getFluid();
        Item bucketItem = fluid.getBucketItem();

        if (bucketItem == null) {
            bucketItem = Items.BUCKET;
        }

        return bucketItem;
    }

    // Bucket keeps its fluid is the Infinity enchantment is set to affect buckets.
    @Nullable
    public static ItemStack infinityBucketKeepFluid(ItemStack bucketStack) {
        if (EnchantmentUtil.isBucketAndHasInfinityAndBucketEnabled(bucketStack)) {
            Item bucketItem = bucketStack.getItem();

            if (bucketItem instanceof EntityBucketItem entityBucketItem) {
                ItemStack newBucketStack = new ItemStack(BucketUtil.getNoEntityBucket(entityBucketItem));
                EnchantmentUtil.copyEnchantments(bucketStack, newBucketStack);
                return newBucketStack;
            } else {
                return bucketStack;
            }
        } else {
            return null;
        }
    }
}
