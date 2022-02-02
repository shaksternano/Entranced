package io.github.shaksternano.entranced.commonside.fabricasm;

import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;

public final class EntrancedFabricEarlyRiser implements Runnable {

    @Override
    public void run() {
        MappingResolver remapper = FabricLoader.getInstance().getMappingResolver();

        String enchantmentTarget = remapper.mapClassName("intermediary", "net.minecraft.class_1886");
        ClassTinkerers.enumBuilder(enchantmentTarget).addEnumSubclass("FLUID_CONTAINER", "io.github.shaksternano.entranced.commonside.fabricasm.enchantmenttarget.fabric.FluidContainerTarget").build();
    }
}
