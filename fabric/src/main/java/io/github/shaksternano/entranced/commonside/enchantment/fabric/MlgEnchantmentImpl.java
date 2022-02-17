package io.github.shaksternano.entranced.commonside.enchantment.fabric;

import com.chocohead.mm.api.ClassTinkerers;
import io.github.shaksternano.entranced.commonside.enchantment.MlgEnchantment;
import io.github.shaksternano.entranced.commonside.fabricasm.fabric.EntrancedEnchantmentTargets;
import net.minecraft.enchantment.EnchantmentTarget;

public final class MlgEnchantmentImpl {

    private MlgEnchantmentImpl() {}

    public static MlgEnchantment newInstance() {
        return new MlgEnchantment(ClassTinkerers.getEnum(EnchantmentTarget.class, EntrancedEnchantmentTargets.FLUID_CONTAINER));
    }
}
