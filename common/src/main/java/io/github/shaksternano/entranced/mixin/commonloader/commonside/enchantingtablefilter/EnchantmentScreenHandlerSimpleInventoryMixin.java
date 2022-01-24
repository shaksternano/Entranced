package io.github.shaksternano.entranced.mixin.commonloader.commonside.enchantingtablefilter;

import io.github.shaksternano.entranced.commonside.access.EnchantmentScreenHandlerAccess;
import net.minecraft.screen.EnchantmentScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(targets = "net/minecraft/screen/EnchantmentScreenHandler$1")
abstract class EnchantmentScreenHandlerSimpleInventoryMixin {

    /**
     * Sets the enchanting catalyst inventory index on the EnchantmentScreenHandler
     * and increments the inventory size by 1 to allow the catalyst to be put in the
     * enchanting table inventory.
     */
    @ModifyVariable(method = "<init>", at = @At("HEAD"), argsOnly = true)
    private static int entranced$increaseInventorySize(int size, EnchantmentScreenHandler enchantmentScreenHandler) {
        ((EnchantmentScreenHandlerAccess) enchantmentScreenHandler).entranced$setCatalystInventoryIndex(size);
        return ++size;
    }
}
