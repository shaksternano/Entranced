package io.github.shaksternano.entranced.mixin.fabric.client.enchantment.imperishable;

import io.github.shaksternano.entranced.commonside.enchantment.ImperishableEnchantment;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Environment(EnvType.CLIENT)
@Mixin(AnvilScreen.class)
abstract class AnvilScreenMixin {

    /*
    An item with imperishable at 0 durability in an anvil will not have "(Broken)" at the end if its name.
    Forge equivalent is io.github.shaksternano.entranced.mixin.forge.client.enchantment.imperishable.AnvilScreenMixin#imperishableBrokenOnSlotUpdate
     */
    @ModifyArgs(method = "onSlotUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/TextFieldWidget;setText(Ljava/lang/String;)V"))
    private void imperishableBrokenOnSlotUpdate(Args args, ScreenHandler handler, int slotId, ItemStack stack) {
        args.set(0, ImperishableEnchantment.itemNameRemoveBroken(args.get(0), stack));
    }
}
