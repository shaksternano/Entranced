package io.github.shaksternano.entranced.mixin.commonloader.commonside.enchantingtablefilter;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.WrapWithCondition;
import io.github.shaksternano.entranced.commonside.Entranced;
import io.github.shaksternano.entranced.commonside.access.enchantingtablefilter.EnchantmentScreenHandlerAccess;
import io.github.shaksternano.entranced.commonside.access.enchantingtablefilter.ExtraEnchantingCatalystTypeArgument;
import io.github.shaksternano.entranced.commonside.access.enchantingtablefilter.PlayerEntityAccess;
import io.github.shaksternano.entranced.commonside.config.EnchantingCatalystConfig;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnchantmentScreenHandler.class)
abstract class EnchantmentScreenHandlerMixin extends ScreenHandler implements EnchantmentScreenHandlerAccess {

    @SuppressWarnings("unused")
    private EnchantmentScreenHandlerMixin(@Nullable ScreenHandlerType<?> screenHandlerType, int i) {
        super(screenHandlerType, i);
    }

    @Shadow @Final private Inventory inventory;
    @Shadow @Final private ScreenHandlerContext context;
    @Shadow @Final private Property seed;

    @Unique
    private int entranced$catalystInventoryIndex;
    @Unique
    private int entranced$catalystSlotIndex;
    @Unique
    private PlayerEntity entranced$currentPlayer;

    /**
     * Adds the enchanting catalyst slot to the enchanting table screen.
     */
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
                    ((ExtraEnchantingCatalystTypeArgument) (Object) stack).entranced$setArgument(
                            ((PlayerEntityAccess) entranced$currentPlayer).entranced$getEnchantingCatalystType().orElse(null)
                    );

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

    /**
     * Let
     * io.github.shaksternano.entranced.mixin.commonloader.commonside.enchantingtablefilter.EnchantmentHelperMixin#entranced$filterEnchantment
     * know if an enchanting catalyst is being used or not.
     */
    @SuppressWarnings("unused")
    @ModifyExpressionValue(method = "onContentChanged", at = @At(value = "INVOKE", target = "Lnet/minecraft/inventory/Inventory;getStack(I)Lnet/minecraft/item/ItemStack;"))
    private ItemStack entranced$setUsedEnchantingCatalyst(ItemStack toEnchantStack) {
        if (Entranced.INSTANCE.getConfig().isEnchantingCatalystEnabled()) {
            ((ExtraEnchantingCatalystTypeArgument) (Object) toEnchantStack).entranced$setArgument(
                    ((PlayerEntityAccess) entranced$currentPlayer).entranced$getEnchantingCatalystType().orElse(null)
            );
        }

        return toEnchantStack;
    }

    /**
     * Clear the last used enchanting catalyst after it has been used.
     */
    @Inject(method = "method_17410", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;applyEnchantmentCosts(Lnet/minecraft/item/ItemStack;I)V"))
    private void entranced$resetEnchantingCatalyst(ItemStack itemStack, int i, PlayerEntity player, int j, ItemStack itemStack2, World world, BlockPos pos, CallbackInfo ci) {
        ((PlayerEntityAccess) player).entranced$setEnchantingCatalystType(null);
        ((ExtraEnchantingCatalystTypeArgument) (Object) inventory.getStack(0)).entranced$setArgument(null);
    }

    /**
     * Sets the player's enchanting catalyst if there is a valid one in the enchanting catalyst slot and applies it.
     */
    @Unique
    @Override
    public void entranced$applyEnchantingCatalyst() {
        if (Entranced.INSTANCE.getConfig().isEnchantingCatalystEnabled()) {
            ItemStack enchantingCatalystStack = inventory.getStack(entranced$catalystInventoryIndex);

            if (!enchantingCatalystStack.isEmpty()) {
                EnchantingCatalystConfig.INSTANCE.getCatalystType(enchantingCatalystStack.getItem()).ifPresent(catalyst -> context.run((world, blockPos) -> {
                    PlayerEntityAccess catalystTypeHolder = (PlayerEntityAccess) entranced$currentPlayer;

                    if (catalyst.catalystType() != catalystTypeHolder.entranced$getEnchantingCatalystType().orElse(null)) {
                        catalystTypeHolder.entranced$setEnchantingCatalystType(catalyst.catalystType());

                        if (catalyst.catalystConsumed()) {
                            if (!entranced$currentPlayer.isCreative()) {
                                enchantingCatalystStack.decrement(1);
                            }
                        }

                        ((PlayerEntityAccess) entranced$currentPlayer).entranced$setNewEnchantingTableSeed();
                        inventory.markDirty();
                        seed.set(entranced$currentPlayer.getEnchantmentTableSeed());
                        onContentChanged(inventory);
                        world.playSound(null, blockPos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0F, world.random.nextFloat() * 0.1F + 0.9F);
                    }
                }));
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

    /**
     * Move an enchanting catalyst to the enchanting catalyst slot.
     */
    @SuppressWarnings("unused")
    @ModifyExpressionValue(method = "transferSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/slot/Slot;hasStack()Z"), slice = @Slice(
            from = @At(value = "INVOKE", target = "Lnet/minecraft/screen/EnchantmentScreenHandler;insertItem(Lnet/minecraft/item/ItemStack;IIZ)Z"),
            to = @At(value = "INVOKE", target = "Lnet/minecraft/screen/slot/Slot;canInsert(Lnet/minecraft/item/ItemStack;)Z")
    ))
    private boolean entranced$moveToCatalystSlot(boolean hasStack, PlayerEntity player, int index) {
        if (Entranced.INSTANCE.getConfig().isEnchantingCatalystEnabled()) {
            Slot slot = slots.get(index);
            ItemStack selectedSlotStack = slot.getStack();
            return !insertItem(selectedSlotStack, entranced$catalystSlotIndex, entranced$catalystSlotIndex + 1, true) && hasStack;
        } else {
            return hasStack;
        }
    }

    /**
     * Move an enchanting catalyst to the enchanting catalyst slot.
     */
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
            return insertItem(selectedSlotStack, entranced$catalystSlotIndex, entranced$catalystSlotIndex + 1, true) || canInsert;
        } else {
            return canInsert;
        }
    }

    /**
     * Don't move an item to the enchanting slot if an item has been moved to the enchanting catalyst slot.
     */
    @SuppressWarnings("unused")
    @WrapWithCondition(method = "transferSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;decrement(I)V"))
    private boolean entranced$doNotDecrement(ItemStack selectedSlotStack, int amount) {
        return !Entranced.INSTANCE.getConfig().isEnchantingCatalystEnabled() || entranced$canInsertToEnchant(selectedSlotStack);
    }

    /**
     * Don't move an item to the enchanting slot if an item has been moved to the enchanting catalyst slot.
     */
    @SuppressWarnings("unused")
    @WrapWithCondition(method = "transferSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/slot/Slot;setStack(Lnet/minecraft/item/ItemStack;)V"), slice = @Slice(
            from = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;decrement(I)V"),
            to = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isEmpty()Z")
    ))
    private boolean entranced$doNotSetStack(Slot slotZero, ItemStack movedStack, PlayerEntity player, int index) {
        Slot slot = slots.get(index);
        ItemStack selectedSlotStack = slot.getStack();
        return !Entranced.INSTANCE.getConfig().isEnchantingCatalystEnabled() || entranced$canInsertToEnchant(selectedSlotStack);
    }

    /**
     * @return {@code true} if an item can be inserted into the enchanting slot, {@code false} otherwise.
     */
    @Unique
    private boolean entranced$canInsertToEnchant(ItemStack stack) {
        return !(slots.get(0).hasStack() || !slots.get(0).canInsert(stack));
    }

    // For shift-clicking out of the catalyst slot.

    /**
     * Moves an item out of the enchanting catalyst slot and into other slots.
     */
    @Unique
    @Override
    public boolean entranced$isCatalystSlotImpl(boolean isLapis, int index) {
        return isLapis || (Entranced.INSTANCE.getConfig().isEnchantingCatalystEnabled() && index == entranced$catalystSlotIndex);
    }

    /**
     * Moves an item out of the enchanting catalyst slot and into other slots.
     */
    @Unique
    @Override
    public boolean entranced$moveOutOfCatalystSlotImpl(boolean isLapis, ItemStack selectedSlotStack, int index) {
        if (!Entranced.INSTANCE.getConfig().isEnchantingCatalystEnabled() || isLapis) {
            return insertItem(selectedSlotStack, 1, 2, true);
        } else if (index == entranced$catalystSlotIndex) {
            return insertItem(selectedSlotStack, 2, entranced$catalystSlotIndex, true);
        } else {
            return false;
        }
    }
}
