package io.github.shaksternano.entranced.mixin.fabric.commonside.enchantment.imperishable;

import io.github.shaksternano.entranced.commonside.config.ImperishableBlacklists;
import io.github.shaksternano.entranced.commonside.enchantment.ImperishableEnchantment;
import io.github.shaksternano.entranced.commonside.registry.EntrancedEnchantments;
import io.github.shaksternano.entranced.commonside.util.EnchantmentUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
abstract class ItemEntityMixin extends Entity {

    @SuppressWarnings("unused")
    private ItemEntityMixin(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    @Shadow private int itemAge;

    @Shadow public abstract ItemStack getStack();

    /**
     * Item entities with the {@link ImperishableEnchantment} don't despawn.
     * Forge equivalent is io.github.shaksternano.entranced.commonside.event.enchantment.forge.ImperishableEvents#entranced$imperishableNoItemDespawn
     */
    @Inject(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/ItemEntity;itemAge:I", opcode = Opcodes.PUTFIELD))
    private void entranced$imperishableNoItemDespawn(CallbackInfo ci) {
        if (world.isClient || !EnchantmentUtil.hasEnchantment(getStack(), EntrancedEnchantments.IMPERISHABLE) || !ImperishableBlacklists.INSTANCE.isItemProtected(getStack(), ImperishableBlacklists.ProtectionType.DESPAWN_PROTECTION)) {
            itemAge++;
        }
    }
}
