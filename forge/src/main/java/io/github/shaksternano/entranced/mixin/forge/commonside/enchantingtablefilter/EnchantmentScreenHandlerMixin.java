package io.github.shaksternano.entranced.mixin.forge.commonside.enchantingtablefilter;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.shaksternano.entranced.commonside.access.enchantingtablefilter.EnchantmentScreenHandlerAccess;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.EnchantmentScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(EnchantmentScreenHandler.class)
abstract class EnchantmentScreenHandlerMixin extends ScreenHandler {

    @SuppressWarnings("unused")
    private EnchantmentScreenHandlerMixin(@Nullable ScreenHandlerType<?> arg, int i) {
        super(arg, i);
    }

    // All methods below are used together to add transferSlot functionality to the catalyst slot.

    // For shift-clicking out of the catalyst slot.

    /**
     * Moves an item out of the enchanting catalyst slot and into other slots.
     */
    @SuppressWarnings("unused")
    @ModifyExpressionValue(method = "transferSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isIn(Lnet/minecraft/tag/TagKey;)Z"))
    private boolean entranced$isCatalystSlot(boolean isEnchantingFuel, PlayerEntity player, int index) {
        return ((EnchantmentScreenHandlerAccess) this).entranced$isCatalystSlotImpl(isEnchantingFuel, index);
    }

    /**
     * Moves an item out of the enchanting catalyst slot and into other slots.
     */
    @Redirect(method = "transferSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/EnchantmentScreenHandler;insertItem(Lnet/minecraft/item/ItemStack;IIZ)Z"), slice = @Slice(
            from = @At(value = "FIELD", target = "Lnet/minecraftforge/common/Tags$Items;ENCHANTING_FUELS:Lnet/minecraft/tag/TagKey;", opcode = Opcodes.GETSTATIC),
            to = @At(value = "INVOKE", target = "Lnet/minecraft/screen/slot/Slot;canInsert(Lnet/minecraft/item/ItemStack;)Z")
    ))
    private boolean entranced$moveOutOfCatalystSlot(EnchantmentScreenHandler thisScreenHandler, ItemStack selectedSlotStack, int startIndex, int endIndex, boolean fromLast, PlayerEntity player, int index) {
        return ((EnchantmentScreenHandlerAccess) this).entranced$moveOutOfCatalystSlotImpl(selectedSlotStack.isIn(Tags.Items.ENCHANTING_FUELS), selectedSlotStack, index);
    }
}
