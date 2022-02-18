package io.github.shaksternano.entranced.mixin.fabric.commonside.enchantingtablefilter;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.shaksternano.entranced.commonside.access.enchantingtablefilter.EnchantmentScreenHandlerAccess;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.EnchantmentScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(EnchantmentScreenHandler.class)
abstract class EnchantmentScreenHandlerMixin extends ScreenHandler {

    @SuppressWarnings("unused")
    private EnchantmentScreenHandlerMixin(@Nullable ScreenHandlerType<?> screenHandlerType, int i) {
        super(screenHandlerType, i);
    }

    // All methods below are used together to add transferSlot functionality to the catalyst slot.

    // For shift-clicking out of the catalyst slot.

    /**
     * Moves an item out of the enchanting catalyst slot and into other slots.
     */
    @SuppressWarnings("unused")
    @ModifyExpressionValue(method = "transferSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private boolean entranced$isCatalystSlot(boolean isLapis, PlayerEntity player, int index) {
        return ((EnchantmentScreenHandlerAccess) this).entranced$isCatalystSlotImpl(isLapis, index);
    }

    /**
     * Moves an item out of the enchanting catalyst slot and into other slots.
     */
    @Redirect(method = "transferSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/EnchantmentScreenHandler;insertItem(Lnet/minecraft/item/ItemStack;IIZ)Z"), slice = @Slice(
            from = @At(value = "FIELD", target = "Lnet/minecraft/item/Items;LAPIS_LAZULI:Lnet/minecraft/item/Item;", opcode = Opcodes.GETSTATIC),
            to = @At(value = "INVOKE", target = "Lnet/minecraft/screen/slot/Slot;canInsert(Lnet/minecraft/item/ItemStack;)Z")
    ))
    private boolean entranced$moveOutOfCatalystSlot(EnchantmentScreenHandler thisScreenHandler, ItemStack selectedSlotStack, int startIndex, int endIndex, boolean fromLast, PlayerEntity player, int index) {
        return ((EnchantmentScreenHandlerAccess) this).entranced$moveOutOfCatalystSlotImpl(selectedSlotStack.isOf(Items.LAPIS_LAZULI), selectedSlotStack, index);
    }
}
