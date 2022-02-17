package io.github.shaksternano.entranced.commonside.network.debug;

import dev.architectury.networking.NetworkManager;
import io.github.shaksternano.entranced.commonside.Entranced;
import io.github.shaksternano.entranced.commonside.registry.EntrancedEnchantments;
import io.github.shaksternano.entranced.commonside.util.EnchantmentUtil;
import io.github.shaksternano.entranced.mixin.commonloader.commonside.accessor.ItemStackAccessor;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Identifier;

public class DebugNetworking {

    public static final Identifier DEBUG_HOTBAR_SLOT = new Identifier(Entranced.MOD_ID, "debug_hotbar_slot");

    /**
     * Registers logical server receivers related to debugging.
     */
    public static void registerServerReceivers() {
        // For debug mode.
        NetworkManager.registerReceiver(NetworkManager.clientToServer(), DEBUG_HOTBAR_SLOT, (buf, context) -> {
            int slot = buf.readInt();
            PlayerEntity player = context.getPlayer();

            context.queue(() -> {
                if (Entranced.INSTANCE.getConfig().isDebugMode()) {
                    ItemStack stack = player.getMainHandStack();
                    switch (slot) {
                        /*
                        If debug mode is on and the hotbar slot 1 key is pressed, the item
                        in the main hand will be enchanted with Imperishable instead of it
                        being dropped. If the item already has Imperishable, the Imperishable
                        enchantment will be removed from the item.
                         */
                        case 0 -> {
                            if (!EnchantmentUtil.hasEnchantment(stack, EntrancedEnchantments.IMPERISHABLE)) {
                                stack.addEnchantment(EntrancedEnchantments.IMPERISHABLE, EntrancedEnchantments.IMPERISHABLE.getMaxLevel());
                            } else {
                                NbtList enchantmentNbtList = stack.getEnchantments();

                                if (enchantmentNbtList.size() == 1) {
                                    stack.removeSubNbt(ItemStack.ENCHANTMENTS_KEY);
                                    stack.removeSubNbt(ItemStackAccessor.entranced$getRepairCostKey());
                                } else {
                                    boolean removed = false;
                                    int index = 0;

                                    while (index < enchantmentNbtList.size() && !removed) {
                                        Identifier enchantmentId = EnchantmentHelper.getIdFromNbt(enchantmentNbtList.getCompound(index));
                                        if (enchantmentId != null) {
                                            if (enchantmentId.getNamespace().equals(Entranced.MOD_ID)) {
                                                if (enchantmentId.getPath().equals(EntrancedEnchantments.IMPERISHABLE.getPath())) {
                                                    enchantmentNbtList.remove(index);
                                                    removed = true;
                                                }
                                            }
                                        }

                                        index++;
                                    }
                                }
                            }
                        }

                        /*
                        If debug mode is on and the hotbar slot 2 key is pressed, the durability
                        of the item in the main hand will be set to 1. If the durability is
                        already 1, it will set it to full.
                         */
                        case 1 -> {
                            if (stack.isDamageable()) {
                                if (stack.getDamage() == stack.getMaxDamage() - 1) {
                                    stack.setDamage(0);
                                } else {
                                    stack.setDamage(stack.getMaxDamage() - 1);
                                }
                            }
                        }

                        /*
                        If debug mode is on and the hotbar slot 3 key is pressed, the durability
                        of the item in the main hand will be set to 0. If the durability is
                        already 0, it will set it to full.
                         */
                        case 2 -> {
                            if (stack.isDamageable()) {
                                if (stack.getDamage() == stack.getMaxDamage()) {
                                    stack.setDamage(0);
                                } else {
                                    stack.setDamage(stack.getMaxDamage());
                                }
                            }
                        }

                        /*
                        If debug mode is on and the hotbar slot 4 key is pressed, the durability
                        of the item in the main hand will be decreased by 1 if the durability is
                        not already 0.
                         */
                        case 3 -> {
                            if (stack.isDamageable()) {
                                if (stack.getDamage() < stack.getMaxDamage()) {
                                    stack.setDamage(stack.getDamage() + 1);
                                }
                            }
                        }

                        /*
                        If debug mode is on and the hotbar slot 5 key is pressed, the durability
                        of the item in the main hand will be increased by 1 if the durability is
                        not already full.
                         */
                        case 4 -> {
                            if (stack.isDamageable()) {
                                if (stack.getDamage() > 0) {
                                    stack.setDamage(stack.getDamage() - 1);
                                }
                            }
                        }
                    }
                }
            });
        });
    }
}
