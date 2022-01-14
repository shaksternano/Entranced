package io.github.shaksternano.entranced.commonside.event.enchantment;

import dev.architectury.event.events.client.ClientTickEvent;
import io.github.shaksternano.entranced.commonside.registry.EntrancedEnchantments;
import io.github.shaksternano.entranced.commonside.util.EnchantmentUtil;
import io.github.shaksternano.entranced.mixin.commonloader.commonside.accessor.AbstractBlockAccessor;
import io.github.shaksternano.entranced.mixin.commonloader.commonside.accessor.BucketItemAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.FluidModificationItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public final class MlgEvents {

    private MlgEvents() {}

    @Environment(EnvType.CLIENT)
    public static void registerClientEvents() {
        ClientTickEvent.CLIENT_POST.register(client -> {
            if (EntrancedEnchantments.MLG.isEnabled()) {
                PlayerEntity player = client.player;

                if (player != null) {
                    if (!player.isCreative() && !player.isSpectator()) {
                        if (!player.isOnGround()) {
                            boolean safeFall = false;

                            for (int fallDistance = 0; fallDistance <= player.getSafeFallDistance(); fallDistance++) {
                                Block block = player.world.getBlockState(player.getBlockPos().down(fallDistance)).getBlock();
                                if (((AbstractBlockAccessor) block).entranced$isCollidable()) {
                                    safeFall = true;
                                    break;
                                }
                            }

                            if (!safeFall) {
                                PlayerInventory playerInventory = player.getInventory();

                                int inventoryIndex = -1;
                                for (int newIndex = 0; newIndex < playerInventory.main.size(); newIndex++) {
                                    ItemStack stack = playerInventory.main.get(newIndex);
                                    Item item = stack.getItem();
                                    if (!stack.isEmpty()) {
                                        if (item instanceof FluidModificationItem) {
                                            if (!(item instanceof BucketItem && !(((BucketItemAccessor) item).entranced$getFluid() instanceof FlowableFluid))) {
                                                if (EnchantmentUtil.hasEnchantment(stack, EntrancedEnchantments.MLG)) {
                                                    inventoryIndex = newIndex;
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }

                                if (inventoryIndex != -1) {
                                    if (PlayerInventory.isValidHotbarIndex(inventoryIndex)) {
                                        playerInventory.selectedSlot = inventoryIndex;
                                    } else {
                                        ClientPlayerInteractionManager interactionManager = client.interactionManager;

                                        if (interactionManager != null) {
                                            interactionManager.pickFromInventory(inventoryIndex);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
    }
}
