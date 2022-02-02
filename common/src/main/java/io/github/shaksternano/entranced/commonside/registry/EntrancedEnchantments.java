package io.github.shaksternano.entranced.commonside.registry;

import dev.architectury.registry.registries.DeferredRegister;
import io.github.shaksternano.entranced.commonside.Entranced;
import io.github.shaksternano.entranced.commonside.enchantment.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.registry.Registry;

import java.util.HashSet;
import java.util.Set;

public final class EntrancedEnchantments {

    private EntrancedEnchantments() {}

    private static final DeferredRegister<Enchantment> enchantments = DeferredRegister.create(Entranced.MOD_ID, Registry.ENCHANTMENT_KEY);
    private static final Set<ConfigurableEnchantment> enchantmentsToRegister = new HashSet<>();

    public static final ConfigurableEnchantment AUTOSWING = AutoswingEnchantment.INSTANCE;
    public static final ConfigurableEnchantment FRENZY = FrenzyEnchantment.INSTANCE;
    public static final ConfigurableEnchantment IMPERISHABLE = ImperishableEnchantment.newEnchantment();
    public static final ConfigurableEnchantment MLG = MlgEnchantment.newEnchantment();

    public static void addEnchantmentToRegister(ConfigurableEnchantment enchantment) {
        enchantmentsToRegister.add(enchantment);
    }

    /**
     * Registers all the enchantments.
     */
    public static void registerEnchantments() {
        for (ConfigurableEnchantment enchantment : enchantmentsToRegister) {
            enchantments.register(enchantment.getPath(), () -> enchantment);
        }

        enchantments.register();
    }
}
