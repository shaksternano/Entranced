package io.github.shaksternano.entranced.mixin.fabric.commonside.enchantment.frenzy;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import io.github.shaksternano.entranced.commonside.enchantment.FrenzyEnchantment;
import io.github.shaksternano.entranced.commonside.registry.EntrancedEnchantments;
import io.github.shaksternano.entranced.commonside.util.EnchantmentUtil;
import io.github.shaksternano.entranced.mixin.commonloader.commonside.accessor.ItemAccessor;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Iterator;

@Mixin(ItemStack.class)
abstract class ItemStackMixin {

    /*
    Increases the player's attack speed if the item they are holding
    in their main hand has the Frenzy enchantment.
    Forge equivalent is io.github.shaksternano.entranced.commonside.event.enchantment.forge.FrenzyEvents#entranced$frenzyIncreaseAttackSpeed
     */
    @SuppressWarnings("ConstantConditions")
    @Inject(method = "getAttributeModifiers", at = @At("RETURN"), cancellable = true)
    private void entranced$frenzyIncreaseAttackSpeed(EquipmentSlot slot, CallbackInfoReturnable<Multimap<EntityAttribute, EntityAttributeModifier>> cir) {
        ItemStack stack = (ItemStack) (Object) this;
        if (slot == EquipmentSlot.MAINHAND) {
            if (EntrancedEnchantments.FRENZY.isEnabled()) {
                if (EnchantmentUtil.hasEnchantment(stack, EntrancedEnchantments.FRENZY)) {
                    Multimap<EntityAttribute, EntityAttributeModifier> attributes = cir.getReturnValue();

                    if (attributes instanceof ImmutableMultimap) {
                        attributes = MultimapBuilder.hashKeys().hashSetValues().build(attributes);
                    }

                    double attackSpeed = 0.0D;
                    Iterator<EntityAttributeModifier> iterator = attributes.get(EntityAttributes.GENERIC_ATTACK_SPEED).iterator();
                    if (iterator.hasNext()) {
                        attackSpeed = iterator.next().getValue();
                    }

                    attributes.removeAll(EntityAttributes.GENERIC_ATTACK_SPEED);
                    attributes.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ItemAccessor.entranced$getAttackSpeedModifierId(), "Weapon modifier", FrenzyEnchantment.getAttackSpeed(stack, attackSpeed), EntityAttributeModifier.Operation.ADDITION));

                    cir.setReturnValue(attributes);
                }
            }
        }
    }
}
