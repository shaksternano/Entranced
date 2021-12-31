package io.github.shaksternano.entranced.mixin.commonloader.commonside.retainenchantment.item;

import io.github.shaksternano.entranced.commonside.util.EnchantmentUtil;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(Recipe.class)
interface RecipeMixin<C extends Inventory> {

    // Items retain their enchantments when using in a crafting recipe.
    @ModifyArgs(method = "getRemainder", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/collection/DefaultedList;set(ILjava/lang/Object;)Ljava/lang/Object;"))
    private void recipeTransferEnchantments(Args args, C inventory) {
        int index = args.get(0);
        ItemStack originalStack = inventory.getStack(index);
        ItemStack newItemStack = args.get(1);
        EnchantmentUtil.copyEnchantments(originalStack, newItemStack);
    }
}
