package io.github.shaksternano.entranced.mixin.commonloader.commonside.enchantment.imperishable;

import io.github.shaksternano.entranced.commonside.config.ImperishableBlacklists;
import io.github.shaksternano.entranced.commonside.util.EnchantmentUtil;
import net.minecraft.entity.Shearable;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MobEntity.class)
abstract class MobEntityMixin {

    @Shadow protected abstract ActionResult interactMob(PlayerEntity player, Hand hand);

    // Shears with Imperishable at 0 durability have shear specific right click mob actions cancelled.
    @Redirect(method = "interact", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/MobEntity;interactMob(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;"))
    private ActionResult imperishableShearMob(MobEntity thisMob, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);

        if (ImperishableBlacklists.isItemProtected(stack, ImperishableBlacklists.ProtectionType.BREAK_PROTECTION)) {
            if (!player.isCreative()) {
                if (stack.getItem() instanceof ShearsItem) {
                    if (this instanceof Shearable) {
                        if (EnchantmentUtil.isBrokenImperishable(stack)) {
                            return ActionResult.PASS;
                        }
                    }
                }
            }
        }

        return interactMob(player, hand);
    }
}
