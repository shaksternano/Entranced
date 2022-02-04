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
import org.spongepowered.asm.mixin.injection.Redirect;
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

    @Shadow public abstract void onContentChanged(Inventory inventory);

    @Unique
    private int entranced$catalystInventoryIndex;
    @Unique
    private int entranced$catalystSlotIndex;
    @Unique
    private PlayerEntity entranced$currentPlayer;

    @Inject(method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/screen/ScreenHandlerContext;)V", at = @At("RETURN"))
    private void entranced$addSlot(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context, CallbackInfo ci) {
        entranced$catalystSlotIndex = slots.size();

        if (Entranced.INSTANCE.getConfig().isEnchantingCatalystEnabled()) {
            addSlot(new Slot(inventory, entranced$catalystInventoryIndex, -10, 47) {

                @Override
                public ItemStack insertStack(ItemStack stack, int count) {
                    if (Entranced.INSTANCE.getConfig().isEnchantingCatalystEnabled()) {
                        EnchantingCatalystConfig.EnchantingCatalystType catalystType = ((EnchantingCatalystTypeHolder) entranced$currentPlayer).entranced$getEnchantingCatalystType();
                        ((ExtraEnchantingCatalystTypeArgument) (Object) stack).entranced$setArgument(catalystType);
                    }

                    return super.insertStack(stack, count);
                }

                @Override
                public void onTakeItem(PlayerEntity player, ItemStack stack) {
                    ((ExtraEnchantingCatalystTypeArgument) (Object) stack).entranced$setArgument(null);
                    super.onTakeItem(player, stack);
                }
            });
        }

        entranced$currentPlayer = playerInventory.player;
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
    private void resetEnchantingCatalyst(PlayerEntity player, int id, CallbackInfoReturnable<Boolean> cir) {
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

    @Override
    public void entranced$setCatalystInventoryIndex(int index) {
        entranced$catalystInventoryIndex = index;
    }

    // All methods below are used together to add transferSlot functionality to the catalyst slot.

    @SuppressWarnings("unused")
    @ModifyExpressionValue(method = "transferSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/slot/Slot;hasStack()Z"), slice = @Slice(
            from = @At(value = "INVOKE", target = "Lnet/minecraft/screen/EnchantmentScreenHandler;insertItem(Lnet/minecraft/item/ItemStack;IIZ)Z"),
            to = @At(value = "INVOKE", target = "Lnet/minecraft/screen/slot/Slot;canInsert(Lnet/minecraft/item/ItemStack;)Z")
    ))
    private boolean entranced$catalystSlotHasStack(boolean hasStack, PlayerEntity player, int index) {
        Slot slot = slots.get(index);
        ItemStack itemStack2 = slot.getStack();
        return hasStack && !entranced$canInsertCatalyst(itemStack2);
    }

    @SuppressWarnings("unused")
    @ModifyExpressionValue(method = "transferSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/slot/Slot;canInsert(Lnet/minecraft/item/ItemStack;)Z"), slice = @Slice(
            from = @At(value = "INVOKE", target = "Lnet/minecraft/util/collection/DefaultedList;get(I)Ljava/lang/Object;"),
            to = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;setCount(I)V")
    ))
    private boolean entranced$catalystSlotCanInsert(boolean canInsert, PlayerEntity player, int index) {
        Slot slot = slots.get(index);
        ItemStack itemStack2 = slot.getStack();
        // Will be equivalent to !canInsert && !entranced$canInsertCatalyst(itemStack2)
        return canInsert || entranced$canInsertCatalyst(itemStack2);
    }

    @Inject(method = "transferSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;copy()Lnet/minecraft/item/ItemStack;"), slice = @Slice(
            from = @At(value = "INVOKE", target = "Lnet/minecraft/screen/slot/Slot;canInsert(Lnet/minecraft/item/ItemStack;)Z"),
            to = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;setCount(I)V")
    ))
    private void entranced$transferCatalystSlot(PlayerEntity player, int index, CallbackInfoReturnable<ItemStack> cir) {
        Slot slot = slots.get(index);
        ItemStack itemStack2 = slot.getStack();

        if (!entranced$canInsertToEnchant(itemStack2)) {
            if (entranced$canInsertCatalyst(itemStack2)) {
                ItemStack stack = itemStack2.copy();
                itemStack2.setCount(0);
                slots.get(entranced$catalystSlotIndex).setStack(stack);
            }
        }
    }

    @SuppressWarnings("unused")
    @WrapWithCondition(method = "transferSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;decrement(I)V"))
    private boolean entranced$doNotDecrement(ItemStack itemStack2, int amount) {
        return entranced$canInsertToEnchant(itemStack2);
    }

    @SuppressWarnings("unused")
    @WrapWithCondition(method = "transferSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/slot/Slot;setStack(Lnet/minecraft/item/ItemStack;)V"), slice = @Slice(
            from = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;decrement(I)V"),
            to = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isEmpty()Z")
    ))
    private boolean entranced$doSetStack(Slot slotZero, ItemStack itemStack3, PlayerEntity player, int index) {
        Slot slot = this.slots.get(index);
        ItemStack itemStack2 = slot.getStack();
        return entranced$canInsertToEnchant(itemStack2);
    }

    @Unique
    private boolean entranced$canInsertToEnchant(ItemStack stack) {
        return !(slots.get(0).hasStack() || !slots.get(0).canInsert(stack));
    }

    @Unique
    private boolean entranced$canInsertCatalyst(ItemStack stack) {
        return slots.get(entranced$catalystSlotIndex).getStack().getCount() < stack.getMaxCount() && slots.get(entranced$catalystSlotIndex).canInsert(stack);
    }
}
