package io.github.shaksternano.entranced.commonside.fabric;

import com.chocohead.mm.api.ClassTinkerers;
import io.github.shaksternano.entranced.commonside.Entranced;
import net.fabricmc.api.ModInitializer;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.item.Items;

public final class EntrancedFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        Entranced.init();

        EnchantmentTarget target = ClassTinkerers.getEnum(EnchantmentTarget.class, "FLUID_CONTAINER");
        Entranced.LOGGER.info("Can enchant bucket? " + target.isAcceptableItem(Items.WATER_BUCKET));
    }
}
