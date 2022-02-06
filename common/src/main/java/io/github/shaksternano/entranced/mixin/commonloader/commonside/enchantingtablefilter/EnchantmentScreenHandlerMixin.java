package io.github.shaksternano.entranced.mixin.commonloader.commonside.enchantingtablefilter;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.WrapWithCondition;
import io.github.shaksternano.entranced.commonside.Entranced;
import io.github.shaksternano.entranced.commonside.access.enchantingtablefilter.EnchantingCatalystTypeHolder;
import io.github.shaksternano.entranced.commonside.access.enchantingtablefilter.EnchantmentScreenHandlerAccess;
import io.github.shaksternano.entranced.commonside.access.enchantingtablefilter.ExtraEnchantingCatalystTypeArgument;
import io.github.shaksternano.entranced.commonside.config.EnchantingCatalystConfig;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.EnchantmentScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentScreenHandler.class)
abstract class EnchantmentScreenHandlerMixin extends ScreenHandler implements EnchantmentScreenHandlerAccess {

    @SuppressWarnings("unused")
    private EnchantmentScreenHandlerMixin(@Nullable ScreenHandlerType<?> screenHandlerType, int i) {
        super(screenHandlerType, i);
    }

    @Shadow @Final private Inventory inventory;

    @Unique
    private int entranced$catalystInventoryIndex;
    @Unique
    private int entranced$catalystSlotIndex;
    @Unique
    private PlayerEntity entranced$currentPlayer;

    @Inject(method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/screen/ScreenHandlerContext;)V", at = @At("RETURN"))
    private void entranced$addSlot(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context, CallbackInfo ci) {
        entranced$catalystSlotIndex = slots.size();
        entranced$currentPlayer = playerInventory.player;

        if (Entranced.INSTANCE.getConfig().isEnchantingCatalystEnabled()) {
            addSlot(new Slot(inventory, entranced$catalystInventoryIndex, -10, 47) {

                @Override
                public boolean canInsert(ItemStack stack) {
                    return EnchantingCatalystConfig.INSTANCE.isCatalyst(stack);
                }

                @Override
                public ItemStack insertStack(ItemStack stack, int count) {
                    EnchantingCatalystConfig.EnchantingCatalystType catalystType = ((EnchantingCatalystTypeHolder) entranced$currentPlayer).entranced$getEnchantingCatalystType();
                    ((ExtraEnchantingCatalystTypeArgument) (Object) stack).entranced$setArgument(catalystType);

                    return super.insertStack(stack, count);
                }

                @Override
                public void onTakeItem(PlayerEntity player, ItemStack stack) {
                    ((ExtraEnchantingCatalystTypeArgument) (Object) stack).entranced$setArgument(null);
                    super.onTakeItem(player, stack);
                }
            });
        }
    }

    @SuppressWarnings("unused")
    @ModifyExpressionValue(method = "onContentChanged", at = @At(value = "INVOKE", target = "Lnet/minecraft/inventory/Inventory;getStack(I)Lnet/minecraft/item/ItemStack;"))
    private ItemStack entranced$setUsedEnchantingCatalyst(ItemStack toEnchantStack) {
        if (Entranced.INSTANCE.getConfig().isEnchantingCatalystEnabled()) {
            EnchantingCatalystConfig.EnchantingCatalystType catalystType = ((EnchantingCatalystTypeHolder) entranced$currentPlayer).entranced$getEnchantingCatalystType();
            ((ExtraEnchantingCatalystTypeArgument) (Object) toEnchantStack).entranced$setArgument(catalystType);
        }

        return toEnchantStack;
    }

    @Inject(method = "onButtonClick", at = @At("HEAD"))
    private void entranced$resetEnchantingCatalyst(PlayerEntity player, int id, CallbackInfoReturnable<Boolean> cir) {
        ((EnchantingCatalystTypeHolder) player).entranced$setEnchantingCatalystType(null);
        ((ExtraEnchantingCatalystTypeArgument) (Object) inventory.getStack(0)).entranced$setArgument(null);
    }

    @Unique
    @Override
    public void entranced$setEnchantingCatalyst() {
        if (Entranced.INSTANCE.getConfig().isEnchantingCatalystEnabled()) {
            ItemStack enchantingCatalystStack = inventory.getStack(entranced$catalystInventoryIndex);

            if (!enchantingCatalystStack.isEmpty()) {
                EnchantingCatalystConfig.EnchantingCatalyst catalyst = EnchantingCatalystConfig.INSTANCE.getCatalystType(enchantingCatalystStack.getItem());

                if (catalyst != null) {
                    EnchantingCatalystTypeHolder catalystTypeHolder = (EnchantingCatalystTypeHolder) entranced$currentPlayer;

                    if (catalyst.catalystType() != catalystTypeHolder.entranced$getEnchantingCatalystType()) {
                        catalystTypeHolder.entranced$setEnchantingCatalystType(catalyst.catalystType());

                        if (catalyst.catalystConsumed()) {
                            enchantingCatalystStack.decrement(1);
                        }

                        onContentChanged(inventory);
                    }
                }
            }
        }
    }

    @Unique
    @Override
    public void entranced$setCatalystInventoryIndex(int index) {
        entranced$catalystInventoryIndex = index;
    }

    // All methods below are used together to add transferSlot functionality to the catalyst slot.

    // For shift-clicking into the catalyst slot.

    @SuppressWarnings("unused")
    @ModifyExpressionValue(method = "transferSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/slot/Slot;hasStack()Z"), slice = @Slice(
            from = @At(value = "INVOKE", target = "Lnet/minecraft/screen/EnchantmentScreenHandler;insertItem(Lnet/minecraft/item/ItemStack;IIZ)Z"),
            to = @At(value = "INVOKE", target = "Lnet/minecraft/screen/slot/Slot;canInsert(Lnet/minecraft/item/ItemStack;)Z")
    ))
    private boolean entranced$moveToCatalystSlot(boolean hasStack, PlayerEntity player, int index) {
        if (Entranced.INSTANCE.getConfig().isEnchantingCatalystEnabled()) {
            Slot slot = slots.get(index);
            ItemStack selectedSlotStack = slot.getStack();
            return hasStack && !insertItem(selectedSlotStack, entranced$catalystSlotIndex, entranced$catalystSlotIndex + 1, true);
        } else {
            return hasStack;
        }
    }

    @SuppressWarnings("unused")
    @ModifyExpressionValue(method = "transferSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/slot/Slot;canInsert(Lnet/minecraft/item/ItemStack;)Z"), slice = @Slice(
            from = @At(value = "INVOKE", target = "Lnet/minecraft/util/collection/DefaultedList;get(I)Ljava/lang/Object;"),
            to = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;setCount(I)V")
    ))
    private boolean entranced$catalystSlotCanInsert(boolean canInsert, PlayerEntity player, int index) {
        if (Entranced.INSTANCE.getConfig().isEnchantingCatalystEnabled()) {
            Slot slot = slots.get(index);
            ItemStack selectedSlotStack = slot.getStack();
            // Will be equivalent to !canInsert && !insertItem()
            return canInsert || insertItem(selectedSlotStack, entranced$catalystSlotIndex, entranced$catalystSlotIndex + 1, true);
        } else {
            return canInsert;
        }
    }

    @SuppressWarnings("unused")
    @WrapWithCondition(method = "transferSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;decrement(I)V"))
    private boolean entranced$doNotDecrement(ItemStack selectedSlotStack, int amount) {
        return !Entranced.INSTANCE.getConfig().isEnchantingCatalystEnabled() || entranced$canInsertToEnchant(selectedSlotStack);
    }

    @SuppressWarnings("unused")
    @WrapWithCondition(method = "transferSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/slot/Slot;setStack(Lnet/minecraft/item/ItemStack;)V"), slice = @Slice(
            from = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;decrement(I)V"),
            to = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isEmpty()Z")
    ))
    private boolean entranced$doSetStack(Slot slotZero, ItemStack movedStack, PlayerEntity player, int index) {
        Slot slot = slots.get(index);
        ItemStack selectedSlotStack = slot.getStack();
        return !Entranced.INSTANCE.getConfig().isEnchantingCatalystEnabled() || entranced$canInsertToEnchant(selectedSlotStack);
    }

    @Unique
    private boolean entranced$canInsertToEnchant(ItemStack stack) {
        return !(slots.get(0).hasStack() || !slots.get(0).canInsert(stack));
    }

    // For shift-clicking out of the catalyst slot.

    @Unique
    @Override
    public boolean entranced$isCatalystSlotImpl(boolean isLapis, int index) {
        return isLapis || (Entranced.INSTANCE.getConfig().isEnchantingCatalystEnabled() && index == entranced$catalystSlotIndex);
    }

    @Unique
    @Override
    public boolean entranced$moveOutOfCatalystSlotImpl(boolean isLapis, ItemStack selectedSlotStack, int index) {
        if (!Entranced.INSTANCE.getConfig().isEnchantingCatalystEnabled() || isLapis) {
            return insertItem(selectedSlotStack, 1, 2, true);
        } else if (index == entranced$catalystSlotIndex) {
            return insertItem(selectedSlotStack, 2, 38, true);
        } else {
            return true;
        }
    }
}
