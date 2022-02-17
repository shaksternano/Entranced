package io.github.shaksternano.entranced.commonside.enchantment.fabric;

import com.chocohead.mm.api.ClassTinkerers;
import io.github.shaksternano.entranced.commonside.enchantment.ImperishableEnchantment;
import io.github.shaksternano.entranced.commonside.fabricasm.fabric.EntrancedEnchantmentTargets;
import net.minecraft.enchantment.EnchantmentTarget;

public class ImperishableEnchantmentImpl {

    public static ImperishableEnchantment newInstance() {
        return new ImperishableEnchantment(ClassTinkerers.getEnum(EnchantmentTarget.class, EntrancedEnchantmentTargets.ANY));
    }
}
