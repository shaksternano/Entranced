package io.github.shaksternano.entranced.commonside.fabricasm.fabric;

import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;

public class EntrancedFabricEarlyRiser implements Runnable {

    @Override
    public void run() {
        MappingResolver remapper = FabricLoader.getInstance().getMappingResolver();
        String enchantmentTarget = remapper.mapClassName("intermediary", "net.minecraft.class_1886");

        addEnum(
                enchantmentTarget,
                EntrancedEnchantmentTargets.ANY,
                EnchantmentTargets.class.getName() + "$AnyTarget"
        );

        addEnum(
                enchantmentTarget,
                EntrancedEnchantmentTargets.FLUID_CONTAINER,
                EnchantmentTargets.class.getName() + "$FluidContainerTarget"
        );
    }

    private static void addEnum(String mappedClassName, String name, String structClass) {
        ClassTinkerers.enumBuilder(mappedClassName).addEnumSubclass(name, structClass).build();
    }
}
