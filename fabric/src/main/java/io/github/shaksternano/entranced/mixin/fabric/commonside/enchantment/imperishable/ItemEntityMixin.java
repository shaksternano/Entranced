package io.github.shaksternano.entranced.mixin.fabric.commonside.enchantment.imperishable;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import io.github.shaksternano.entranced.commonside.config.ImperishableBlacklists;
import io.github.shaksternano.entranced.commonside.enchantment.ImperishableEnchantment;
import io.github.shaksternano.entranced.commonside.registry.EntrancedEnchantments;
import io.github.shaksternano.entranced.commonside.util.EnchantmentUtil;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@SuppressWarnings("unused")
@Mixin(ItemEntity.class)
abstract class ItemEntityMixin {

    /**
     * Item entities with the {@link ImperishableEnchantment} don't despawn.
     * Forge equivalent is io.github.shaksternano.entranced.commonside.event.enchantment.forge.ImperishableEvents#entranced$imperishableNoItemDespawn
     */
    @WrapWithCondition(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ItemEntity;discard()V"), slice = @Slice(
            from = @At(value = "FIELD", target = "Lnet/minecraft/entity/ItemEntity;itemAge:I", opcode = Opcodes.GETFIELD)
    ))
    private boolean entranced$imperishableNoItemDespawn(ItemEntity thisItemEntity) {
        ItemStack stack = thisItemEntity.getStack();
        return !EnchantmentUtil.hasEnchantment(stack, EntrancedEnchantments.IMPERISHABLE) || !ImperishableBlacklists.isItemProtected(stack, ImperishableBlacklists.ProtectionType.DESPAWN_PROTECTION);
    }
}
