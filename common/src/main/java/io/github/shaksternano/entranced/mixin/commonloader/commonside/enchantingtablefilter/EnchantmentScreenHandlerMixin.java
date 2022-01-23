package io.github.shaksternano.entranced.mixin.commonloader.commonside.enchantingtablefilter;

import io.github.shaksternano.entranced.commonside.access.EnchantingCatalystHolder;
import io.github.shaksternano.entranced.commonside.access.EnchantmentScreenHandlerAccess;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.EnchantmentScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
    private PlayerEntity entranced$currentPlayer;

    @Inject(method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/screen/ScreenHandlerContext;)V", at = @At("RETURN"))
    private void entranced$addSlot(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context, CallbackInfo ci) {
        addSlot(new Slot(inventory, entranced$catalystInventoryIndex, -10, 47));
        PlayerEntity player = playerInventory.player;

        if (!player.world.isClient) {
            entranced$currentPlayer = player;

            Item item = ((EnchantingCatalystHolder) entranced$currentPlayer).entranced$getEnchantingCatalyst();
            if (item != null) {
                entranced$currentPlayer.sendMessage(Text.of(item.toString()), false);
            }
        }
    }

    @Unique
    @Override
    public void entranced$setEnchantingCatalyst() {
        if (!entranced$currentPlayer.world.isClient) {
            ItemStack enchantingCatalystStack = inventory.getStack(entranced$catalystInventoryIndex);
            ((EnchantingCatalystHolder) entranced$currentPlayer).entranced$setEnchantingCatalyst(enchantingCatalystStack.getItem());
            enchantingCatalystStack.decrement(1);
        }
    }

    @Override
    public void entranced$setInventorySlotIndex(int slotIndex) {
        entranced$catalystInventoryIndex = slotIndex;
    }
}
