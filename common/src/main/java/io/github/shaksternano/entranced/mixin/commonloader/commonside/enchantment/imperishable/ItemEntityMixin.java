package io.github.shaksternano.entranced.mixin.commonloader.commonside.enchantment.imperishable;

import io.github.shaksternano.entranced.commonside.config.ImperishableBlacklists;
import io.github.shaksternano.entranced.commonside.enchantment.ImperishableEnchantment;
import io.github.shaksternano.entranced.commonside.registry.EntrancedEnchantments;
import io.github.shaksternano.entranced.commonside.util.EnchantmentUtil;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
abstract class ItemEntityMixin extends EntityMixin {

    @Shadow
    public abstract ItemStack getStack();

    @Shadow
    public abstract void setNeverDespawn();

    /**
     * Items with the {@link ImperishableEnchantment}
     * are invulnerable to all damage sources.
     */
    @SuppressWarnings("unused")
    @Override
    protected void entranced$damageImperishable(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        if (ImperishableBlacklists.isItemProtected(getStack(), ImperishableBlacklists.ProtectionType.DAMAGE_PROTECTION)) {
            if (EnchantmentUtil.hasEnchantment(getStack(), EntrancedEnchantments.IMPERISHABLE)) {
                cir.setReturnValue(true);
            }
        }
    }

    /**
     * Item entities with the {@link ImperishableEnchantment} don't despawn.
     */
    @Inject(method = "setStack", at = @At("RETURN"))
    private void entranced$imperishableNeverDespawn(ItemStack stack, CallbackInfo ci) {
        if (EnchantmentUtil.hasEnchantment(getStack(), EntrancedEnchantments.IMPERISHABLE)) {
            if (ImperishableBlacklists.isItemProtected(getStack(), ImperishableBlacklists.ProtectionType.DESPAWN_PROTECTION)) {
                setNeverDespawn();
            }
        }
    }

    /**
     * Does various things based if the item has the {@link ImperishableEnchantment}.
     */
    @Inject(method = "tick", at = @At("TAIL"))
    private void entranced$checkImperishable(CallbackInfo ci) {
        if (EnchantmentUtil.hasEnchantment(getStack(), EntrancedEnchantments.IMPERISHABLE)) {
            // Items with Imperishable float up to the world's minimum Y if their Y coordinate is below the world's minimum Y.
            if (ImperishableBlacklists.isItemProtected(getStack(), ImperishableBlacklists.ProtectionType.VOID_PROTECTION)) {
                if (getY() < getWorld().getBottomY()) {

                    Vec3d velocity = getVelocity();
                    setVelocity(velocity.getX() * 0.97D, velocity.getY() + velocity.getY() < 0.06D ? 0.5D : 0.0D, velocity.getZ() * 0.97D);

                    double x = getX() + getVelocity().getX();
                    double y = getY() + getVelocity().getY();
                    double z = getZ() + getVelocity().getZ();

                    if (y >= getWorld().getBottomY()) {
                        setVelocity(Vec3d.ZERO);
                        y = getWorld().getBottomY();
                    }

                    setPosition(x, y, z);
                }

                // Set the Item Entity's Y position to 64 blocks below the world's minimum Y position when below 64 blocks below the world's minimum Y position.
                if (getY() < getWorld().getBottomY() - 64.0D) {
                    if (getVelocity().getY() < 0) {
                        setVelocity(getVelocity().getX(), 0.0D, getVelocity().getZ());
                    }

                    setPosition(getX(), getWorld().getBottomY() - 64.0D, getZ());
                }
            }
        }
    }

    /**
     * Items with the {@link ImperishableEnchantment} don't appear on fire when in fire or lava.
     */
    @Inject(method = "isFireImmune", at = @At("HEAD"), cancellable = true)
    private void entranced$imperishableFireImmune(CallbackInfoReturnable<Boolean> cir) {
        if (ImperishableBlacklists.isItemProtected(getStack(), ImperishableBlacklists.ProtectionType.DAMAGE_PROTECTION)) {
            if (EnchantmentUtil.hasEnchantment(getStack(), EntrancedEnchantments.IMPERISHABLE)) {
                cir.setReturnValue(true);
            }
        }
    }

    /**
     * Items with the {@link ImperishableEnchantment} don't get removed when 64 blocks below the world's minimum Y position.
     */
    @SuppressWarnings("unused")
    @Override
    protected void entranced$imperishableInVoid(CallbackInfo ci) {
        if (ImperishableBlacklists.isItemProtected(getStack(), ImperishableBlacklists.ProtectionType.VOID_PROTECTION)) {
            if (EnchantmentUtil.hasEnchantment(getStack(), EntrancedEnchantments.IMPERISHABLE)) {
                ci.cancel();
            }
        }
    }
}
