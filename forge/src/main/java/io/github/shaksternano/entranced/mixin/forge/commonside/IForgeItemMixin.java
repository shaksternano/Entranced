package io.github.shaksternano.entranced.mixin.forge.commonside;

import io.github.shaksternano.entranced.commonside.util.EnchantmentUtil;
import net.minecraft.enchantment.InfinityEnchantment;
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
     * Used to make the recipe remainder have the same enchantments as the
     * original {@link ItemStack}, and make the recipe remainder for buckets with the
     * {@link InfinityEnchantment} enchantment a copy of the original {@link ItemStack}.
     *
     * <br><br>
     *
     * Fabric equivalents are:
     * <ul>
     * <li>io.github.shaksternano.entranced.mixin.fabric.commonside.enchantment.infinity.bucket.RecipeMixin#entranced$infinityKeepItem</li>
     * <li>io.github.shaksternano.entranced.mixin.fabric.commonside.retainenchantment.item.AbstractFurnaceBlockEntityMixin#entranced$furnaceFuelTransferEnchantments</li>
     * <li>io.github.shaksternano.entranced.mixin.fabric.commonside.retainenchantment.item.BrewingStandBlockEntityMixin#entranced$brewingStandTransferEnchantments</li>
     * <li>io.github.shaksternano.entranced.mixin.fabric.commonside.retainenchantment.item.RecipeMixin#entranced$recipeTransferEnchantments</li>
     * </ul>
     *
     * @author ShaksterNano
     * @reason Injecting into default methods in interfaces on Forge is not allowed for now.
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
            EnchantmentUtil.copyEnchantmentsAndName(itemStack, remainderStack);

            return remainderStack;
        }
    }
}
