package io.github.shaksternano.entranced.commonside.registry;

import dev.architectury.registry.registries.DeferredRegister;
import io.github.shaksternano.entranced.commonside.Entranced;
import io.github.shaksternano.entranced.commonside.enchantment.AutoswingEnchantment;
import io.github.shaksternano.entranced.commonside.enchantment.ConfigurableEnchantment;
import io.github.shaksternano.entranced.commonside.enchantment.FrenzyEnchantment;
import io.github.shaksternano.entranced.commonside.enchantment.ImperishableEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.registry.Registry;

import java.util.HashSet;
import java.util.Set;

public final class EntrancedEnchantments {

    private EntrancedEnchantments() {}

    private static final Set<ConfigurableEnchantment> enchantmentsToRegister = new HashSet<>();
    private static final DeferredRegister<Enchantment> enchantments = DeferredRegister.create(Entranced.MOD_ID, Registry.ENCHANTMENT_KEY);

    public static final ConfigurableEnchantment IMPERISHABLE = new ImperishableEnchantment();
    public static final ConfigurableEnchantment FRENZY = new FrenzyEnchantment();
    public static final ConfigurableEnchantment AUTOSWING = new AutoswingEnchantment();

    public static void addEnchantmentToRegister(ConfigurableEnchantment enchantment) {
        enchantmentsToRegister.add(enchantment);
    }

    // Registers all the enchantments.
    public static void registerEnchantments() {
        for (ConfigurableEnchantment enchantment : enchantmentsToRegister) {
            if (enchantment.isEnabled()) {
                enchantments.register(enchantment.getId(), () -> enchantment);
            }
        }

        enchantments.register();
    }
}
