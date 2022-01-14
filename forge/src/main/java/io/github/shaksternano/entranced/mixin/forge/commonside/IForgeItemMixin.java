package io.github.shaksternano.entranced.mixin.forge.commonside;

import io.github.shaksternano.entranced.commonside.util.EnchantmentUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.extensions.IForgeItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = IForgeItem.class, remap = false)
interface IForgeItemMixin {

    @Shadow boolean hasContainerItem(ItemStack stack);

    @Shadow Item self();

    /**
     * @author ShaksterNano
     * @reason Used to make the recipe remainder have the same enchantments as the
     * original ItemStack,and make the recipe remainder for buckets with the Infinity
     * enchantment a copy of the original ItemStack.
     *
     * Fabric equivalents are:
     *      io.github.shaksternano.entranced.mixin.fabric.commonside.enchantment.infinity.bucket.RecipeMixin#entranced$infinityKeepItem
     *      io.github.shaksternano.entranced.mixin.fabric.commonside.retainenchantment.item.AbstractFurnaceBlockEntityMixin#entranced$furnaceFuelTransferEnchantments
     *      io.github.shaksternano.entranced.mixin.fabric.commonside.retainenchantment.item.BrewingStandBlockEntityMixin#entranced$brewingStandTransferEnchantments
     *      io.github.shaksternano.entranced.mixin.fabric.commonside.retainenchantment.item.RecipeMixin#entranced$recipeTransferEnchantments
     */
    @SuppressWarnings("deprecation")
    @Overwrite
    default ItemStack getContainerItem(ItemStack itemStack) {
        if (EnchantmentUtil.isBucketAndHasInfinityAndBucketEnabled(itemStack)) {
            return itemStack.copy();
        } else if (!hasContainerItem(itemStack)) {
            return ItemStack.EMPTY;
        } else {
            ItemStack remainderStack = new ItemStack(self().getRecipeRemainder());
            EnchantmentUtil.copyEnchantments(itemStack, remainderStack);

            return remainderStack;
        }
    }
}
