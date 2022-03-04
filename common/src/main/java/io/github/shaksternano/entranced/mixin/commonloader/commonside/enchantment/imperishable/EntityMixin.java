package io.github.shaksternano.entranced.mixin.commonloader.commonside.enchantment.imperishable;

import com.google.common.collect.ImmutableList;
import io.github.shaksternano.entranced.commonside.config.ImperishableBlacklists;
import io.github.shaksternano.entranced.commonside.enchantment.ImperishableEnchantment;
import io.github.shaksternano.entranced.commonside.registry.EntrancedEnchantments;
import io.github.shaksternano.entranced.commonside.util.EnchantmentUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(Entity.class)
abstract class EntityMixin {

    @Shadow public abstract double getX();

    @Shadow public abstract double getY();

    @Shadow public abstract double getZ();

    @Shadow public abstract void setPosition(double x, double y, double z);

    @Shadow public abstract Vec3d getVelocity();

    @Shadow public abstract void setVelocity(Vec3d velocity);

    @Shadow public abstract void setVelocity(double x, double y, double z);

    @Shadow public abstract World getWorld();

    @SuppressWarnings("CancellableInjectionUsage")
    @Inject(method = "isInvulnerableTo", at = @At("HEAD"), cancellable = true)
    protected void entranced$damageImperishable(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {}

    @SuppressWarnings("CancellableInjectionUsage")
    @Inject(method = "tickInVoid", at = @At("HEAD"), cancellable = true)
    protected void entranced$imperishableInVoid(CallbackInfo ci) {}

    /**
     * Items with the {@link ImperishableEnchantment}
     * stop falling when they reach the world's minimum Y.
     */
    @SuppressWarnings("UnstableApiUsage")
    @ModifyVariable(method = "adjustMovementForCollisions(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Box;Lnet/minecraft/world/World;Ljava/util/List;)Lnet/minecraft/util/math/Vec3d;", at = @At("HEAD"), argsOnly = true)
    private static List<VoxelShape> entranced$voidFloor(List<VoxelShape> collisions, @Nullable Entity entity) {
        if (entity instanceof ItemEntity itemEntity) {
            ItemStack stack = itemEntity.getStack();
            if (ImperishableBlacklists.isItemProtected(stack, ImperishableBlacklists.ProtectionType.VOID_PROTECTION)) {
                if (EnchantmentUtil.hasEnchantment(stack, EntrancedEnchantments.IMPERISHABLE)) {
                    ImmutableList.Builder<VoxelShape> builder = ImmutableList.builderWithExpectedSize(collisions.size() + 1);
                    builder.addAll(collisions);
                    builder.add(VoxelShapes.cuboid(
                            Double.NEGATIVE_INFINITY,
                            Double.NEGATIVE_INFINITY,
                            Double.NEGATIVE_INFINITY,
                            Double.POSITIVE_INFINITY,
                            entity.getWorld().getBottomY(),
                            Double.POSITIVE_INFINITY
                    ));

                    return builder.build();
                }
            }
        }

        return collisions;
    }
}
