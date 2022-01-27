package io.github.shaksternano.entranced.mixin.fabric.commonside.enchantment.imperishable;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.shaksternano.entranced.commonside.config.ImperishableBlacklists;
import io.github.shaksternano.entranced.commonside.enchantment.ImperishableEnchantment;
import io.github.shaksternano.entranced.commonside.util.EnchantmentUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@SuppressWarnings("unused")
@Mixin(PlayerEntity.class)
abstract class PlayerEntityMixin extends LivingEntity {

    @SuppressWarnings("unused")
    private PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    @Shadow public abstract boolean isCreative();

    /**
     * Swords with the {@link ImperishableEnchantment} at 0 durability can't perform sweeping attacks.
     * Forge equivalent is io.github.shaksternano.entranced.mixin.forge.commonside.enchantment.imperishable.SwordItemMixin#entranced$imperishableSwordSweeping
     */
    @ModifyExpressionValue(method = "attack", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerEntity;onGround:Z", opcode = Opcodes.GETFIELD), slice = @Slice(
            from = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerEntity;horizontalSpeed:F", opcode = Opcodes.GETFIELD),
            to = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getFireAspect(Lnet/minecraft/entity/LivingEntity;)I")
    ))
    private boolean entranced$imperishableSwordSweeping(boolean onGround) {
        return onGround && !(!isCreative() && ImperishableBlacklists.INSTANCE.isItemProtected(getMainHandStack(), ImperishableBlacklists.ProtectionType.BREAK_PROTECTION) && EnchantmentUtil.isBrokenImperishable(getMainHandStack()));
    }
}
