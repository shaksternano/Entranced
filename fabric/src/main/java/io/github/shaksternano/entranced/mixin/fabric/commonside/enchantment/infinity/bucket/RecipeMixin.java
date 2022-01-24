package io.github.shaksternano.entranced.mixin.fabric.commonside.enchantment.infinity.bucket;

import io.github.shaksternano.entranced.commonside.util.EnchantmentUtil;
import net.minecraft.enchantment.InfinityEnchantment;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(Recipe.class)
interface RecipeMixin<C extends Inventory> {

    /**
     * The {@link ItemStack} being used in the recipe doesn't change if it is a bucket with {@link InfinityEnchantment}.
     * Forge equivalent is io.github.shaksternano.entranced.mixin.forge.commonside.IForgeItemMixin#entranced$getContainerItem
     */
    @ModifyArgs(method = "getRemainder", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/collection/DefaultedList;set(ILjava/lang/Object;)Ljava/lang/Object;"))
    private void entranced$infinityKeepItem(Args args, C inventory) {
        int index = args.get(0);
        ItemStack originalStack = inventory.getStack(index);

        if (EnchantmentUtil.isBucketAndHasInfinityAndBucketEnabled(originalStack)) {
            args.set(1, originalStack.copy());
        }
    }
}
