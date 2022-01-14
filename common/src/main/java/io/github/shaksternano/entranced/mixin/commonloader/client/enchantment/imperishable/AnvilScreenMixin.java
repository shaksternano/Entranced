package io.github.shaksternano.entranced.mixin.commonloader.client.enchantment.imperishable;

import io.github.shaksternano.entranced.commonside.enchantment.ImperishableEnchantment;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.minecraft.client.gui.screen.ingame.ForgingScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Environment(EnvType.CLIENT)
@Mixin(AnvilScreen.class)
abstract class AnvilScreenMixin extends ForgingScreen<AnvilScreenHandler> {

    @SuppressWarnings("unused")
    private AnvilScreenMixin(AnvilScreenHandler handler, PlayerInventory playerInventory, Text title, Identifier texture) {
        super(handler, playerInventory, title, texture);
    }

    // Putting "(Broken)" at the end of the name of an item with Imperishable at 0 durability will register as a new name.
    @ModifyArg(method = "onRenamed", at = @At(value = "INVOKE", target = "Ljava/lang/String;equals(Ljava/lang/Object;)Z"))
    private Object entranced$imperishableBrokenOnRenamed(Object name) {
        Slot slot = handler.getSlot(0);

        if (slot != null) {
            if (slot.hasStack()) {
                return ImperishableEnchantment.itemNameRemoveBroken((String) name, slot.getStack());
            }
        }

        return name;
    }
}
