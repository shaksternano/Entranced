package io.github.shaksternano.entranced.mixin.fabric.commonside.enchantment.frenzy;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import io.github.shaksternano.entranced.commonside.Entranced;
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

@Mixin(ItemStack.class)
abstract class ItemStackMixin {

    private static final MultimapBuilder<Object, Object> ENTRANCED$MULTIMAP_BUILDER = MultimapBuilder.hashKeys().hashSetValues();

    /**
     * Increases the player's attack speed if the item they are holding
     * in their main hand has the {@link FrenzyEnchantment}.
     * Forge equivalent is io.github.shaksternano.entranced.commonside.event.enchantment.forge.FrenzyEvents#entranced$frenzyIncreaseAttackSpeed
     */
    @SuppressWarnings("ConstantConditions")
    @Inject(method = "getAttributeModifiers", at = @At("RETURN"), cancellable = true)
    private void entranced$frenzyIncreaseAttackSpeed(EquipmentSlot slot, CallbackInfoReturnable<Multimap<EntityAttribute, EntityAttributeModifier>> cir) {
        ItemStack stack = (ItemStack) (Object) this;

        if (slot == EquipmentSlot.MAINHAND) {
            if (Entranced.getConfig().isFrenzyEnabled()) {
                if (EnchantmentUtil.hasEnchantment(stack, EntrancedEnchantments.FRENZY)) {
                    Multimap<EntityAttribute, EntityAttributeModifier> attributes = cir.getReturnValue();
                    boolean createdNewMap = false;

                    if (attributes instanceof ImmutableMultimap) {
                        attributes = ENTRANCED$MULTIMAP_BUILDER.build(attributes);
                        createdNewMap = true;
                    }

                    double attackSpeed = 0.0D;

                    for (EntityAttributeModifier attributeModifier : attributes.get(EntityAttributes.GENERIC_ATTACK_SPEED)) {
                        attackSpeed += attributeModifier.getValue();
                    }

                    attributes.removeAll(EntityAttributes.GENERIC_ATTACK_SPEED);
                    attributes.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(
                            ItemAccessor.entranced$getAttackSpeedModifierId(),
                            "Weapon modifier",
                            FrenzyEnchantment.getAttackSpeed(stack, attackSpeed),
                            EntityAttributeModifier.Operation.ADDITION
                    ));

                    if (createdNewMap) {
                        cir.setReturnValue(attributes);
                    }
                }
            }
        }
    }
}
