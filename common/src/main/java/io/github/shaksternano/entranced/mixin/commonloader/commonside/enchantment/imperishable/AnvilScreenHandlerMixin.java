package io.github.shaksternano.entranced.mixin.commonloader.commonside.enchantment.imperishable;

import io.github.shaksternano.entranced.commonside.enchantment.ImperishableEnchantment;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(AnvilScreenHandler.class)
abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {

    @SuppressWarnings("unused")
    private AnvilScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(type, syncId, playerInventory, context);
    }

    // Removing "(Broken)" at the end of the name of an item with Imperishable at 0 durability in an anvil will not register as renamed.
    @ModifyArg(method = "updateResult", at = @At(value = "INVOKE", target = "Ljava/lang/String;equals(Ljava/lang/Object;)Z"))
    private Object imperishableBrokenUpdateResult(Object oldName) {
        ItemStack stack = input.getStack(0);
        String trimmedName = ImperishableEnchantment.itemNameRemoveBroken((String) oldName, stack);
        return trimmedName == null ? oldName : trimmedName;
    }
}
