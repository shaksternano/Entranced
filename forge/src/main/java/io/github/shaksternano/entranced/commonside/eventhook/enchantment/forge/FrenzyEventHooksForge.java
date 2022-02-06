package io.github.shaksternano.entranced.commonside.eventhook.enchantment.forge;

import com.google.common.collect.Multimap;
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
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public final class FrenzyEventHooksForge {

    private FrenzyEventHooksForge() {}

    /**
     * Increases the player's attack speed if the item they are holding
     * in their main hand has the {@link FrenzyEnchantment}.
     * Fabric equivalent is io.github.shaksternano.entranced.mixin.fabric.commonside.enchantment.frenzy.ItemStackMixin#frenzyIncreaseAttackSpeed
     */
    @SubscribeEvent
    public static void frenzyIncreaseAttackSpeed(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();
        if (event.getSlotType() == EquipmentSlot.MAINHAND) {
            if (Entranced.INSTANCE.getConfig().isFrenzyEnabled()) {
                if (EnchantmentUtil.hasEnchantment(stack, EntrancedEnchantments.FRENZY)) {
                    Multimap<EntityAttribute, EntityAttributeModifier> attributes = event.getModifiers();

                    double attackSpeed = 0.0D;
                    for (EntityAttributeModifier attributeModifier : attributes.get(EntityAttributes.GENERIC_ATTACK_SPEED)) {
                        attackSpeed += attributeModifier.getValue();
                    }

                    event.removeAttribute(EntityAttributes.GENERIC_ATTACK_SPEED);
                    event.addModifier(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(
                            ItemAccessor.entranced$getAttackSpeedModifierId(),
                            "Weapon modifier",
                            FrenzyEnchantment.getAttackSpeed(stack, attackSpeed),
                            EntityAttributeModifier.Operation.ADDITION
                    ));
                }
            }
        }
    }
}
