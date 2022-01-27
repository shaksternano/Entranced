package io.github.shaksternano.entranced.mixin.commonloader.commonside.enchantingtablefilter;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.shaksternano.entranced.commonside.access.EnchantingCatalystHolder;
import io.github.shaksternano.entranced.commonside.access.EnchantmentScreenHandlerAccess;
import io.github.shaksternano.entranced.commonside.access.ExtraArgument;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
    private PlayerEntity entranced$currentPlayer;

    @Inject(method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/screen/ScreenHandlerContext;)V", at = @At("RETURN"))
    private void entranced$addSlot(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context, CallbackInfo ci) {
        addSlot(new Slot(inventory, entranced$catalystInventoryIndex, -10, 47));
        entranced$currentPlayer = playerInventory.player;
    }

    @SuppressWarnings("unused")
    @ModifyExpressionValue(method = "onContentChanged", at = @At(value = "INVOKE", target = "Lnet/minecraft/inventory/Inventory;getStack(I)Lnet/minecraft/item/ItemStack;"))
    private ItemStack entranced$setUsedEnchantingCatalyst(ItemStack toEnchant) {
        if (!entranced$currentPlayer.world.isClient) {
            if (((EnchantingCatalystHolder) entranced$currentPlayer).entranced$getEnchantingCatalyst() != null) {
                ((ExtraArgument) (Object) toEnchant).entranced$setCatalystType(EnchantingCatalystConfig.EnchantingCatalystType.OFFENSIVE);
            }
        }

        return toEnchant;
    }

    @Unique
    @Override
    public void entranced$setEnchantingCatalyst() {
        if (!entranced$currentPlayer.world.isClient) {
            ItemStack enchantingCatalystStack = inventory.getStack(entranced$catalystInventoryIndex);
            if (!enchantingCatalystStack.isEmpty()) {
                ((EnchantingCatalystHolder) entranced$currentPlayer).entranced$setEnchantingCatalyst(enchantingCatalystStack.getItem());
                enchantingCatalystStack.decrement(1);
                onContentChanged(inventory);
            }
        }
    }

    @Override
    public void entranced$setCatalystInventoryIndex(int index) {
        entranced$catalystInventoryIndex = index;
    }
}
