package io.github.shaksternano.entranced.mixin.commonloader.commonside.enchantment.imperishable;

import io.github.shaksternano.entranced.commonside.config.ImperishableBlacklists;
import io.github.shaksternano.entranced.commonside.enchantment.ImperishableEnchantment;
import io.github.shaksternano.entranced.commonside.registry.EntrancedEnchantments;
import io.github.shaksternano.entranced.commonside.util.EnchantmentUtil;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TridentEntity.class)
abstract class TridentEntityMixin extends PersistentProjectileEntityMixin {

    @Shadow private ItemStack tridentStack;

    /**
     * Tridents with the {@link ImperishableEnchantment}
     * stop falling when they reach the world's minimum Y.
     */
    @Inject(method = "tick", at = @At("TAIL"))
    private void entranced$checkTridentImperishable(CallbackInfo ci) {
        if (ImperishableBlacklists.INSTANCE.isItemProtected(tridentStack, ImperishableBlacklists.ProtectionType.VOID_PROTECTION)) {
            if (EnchantmentUtil.hasEnchantment(tridentStack, EntrancedEnchantments.IMPERISHABLE)) {
                if (!isNoClip()) {
                    if (getY() < world.getBottomY()) {
                        setVelocity(Vec3d.ZERO);
                        setPosition(getX(), world.getBottomY(), getZ());
                        inGround = true;
                        setNoClip(true);
                    }
                }
            }
        }
    }

    /**
     * Tridents with the {@link ImperishableEnchantment}
     * don't despawn.
     */
    @Inject(method = "age", at = @At("HEAD"), cancellable = true)
    private void entranced$imperishableAge(CallbackInfo ci) {
        if (ImperishableBlacklists.INSTANCE.isItemProtected(tridentStack, ImperishableBlacklists.ProtectionType.DESPAWN_PROTECTION)) {
            if (EnchantmentUtil.hasEnchantment(tridentStack, EntrancedEnchantments.IMPERISHABLE)) {
                ci.cancel();
            }
        }
    }

    /**
     * Tridents with the {@link ImperishableEnchantment}
     * don't get removed when 64 blocks below the world's minimum Y position.
     */
    @SuppressWarnings("unused")
    @Override
    protected void entranced$imperishableInVoid(CallbackInfo ci) {
        if (ImperishableBlacklists.INSTANCE.isItemProtected(tridentStack, ImperishableBlacklists.ProtectionType.VOID_PROTECTION)) {
            if (EnchantmentUtil.hasEnchantment(tridentStack, EntrancedEnchantments.IMPERISHABLE)) {
                ci.cancel();
            }
        }
    }
}
