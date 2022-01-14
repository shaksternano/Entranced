package io.github.shaksternano.entranced.mixin.forge.client.enchantment.imperishable;

import io.github.shaksternano.entranced.commonside.enchantment.ImperishableEnchantment;
import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@OnlyIn(Dist.CLIENT)
@Mixin(AnvilScreen.class)
abstract class AnvilScreenMixin {

    /*
    An item with imperishable at 0 durability in an anvil will not have "(Broken)" at the end if its name.
    Fabric equivalent is io.github.shaksternano.entranced.mixin.fabric.client.enchantment.imperishable.AnvilScreenMixin#imperishableBrokenOnSlotUpdate
     */
    @Redirect(method = "onSlotUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/text/Text;getString()Ljava/lang/String;"))
    private String imperishableBrokenOnSlotUpdate(Text getName, ScreenHandler handler, int slotId, ItemStack stack) {
        return ImperishableEnchantment.itemNameRemoveBroken(getName.getString(), stack);
    }
}
