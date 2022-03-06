package io.github.shaksternano.entranced.commonside.registry;

import dev.architectury.registry.registries.DeferredRegister;
import io.github.shaksternano.entranced.commonside.Entranced;
import io.github.shaksternano.entranced.commonside.enchantment.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.registry.Registry;

import java.util.HashSet;
import java.util.Set;

public class EntrancedEnchantments {

    private static final DeferredRegister<Enchantment> enchantments = DeferredRegister.create(Entranced.MOD_ID, Registry.ENCHANTMENT_KEY);
    private static final Set<ConfigurableEnchantment> enchantmentsToRegister = new HashSet<>();

    public static final ConfigurableEnchantment AUTOSWING = addEnchantmentToRegister(new AutoswingEnchantment());
    public static final ConfigurableEnchantment FRENZY = addEnchantmentToRegister(new FrenzyEnchantment());
    public static final ConfigurableEnchantment IMPERISHABLE = addEnchantmentToRegister(ImperishableEnchantment.newInstance());
    public static final ConfigurableEnchantment MLG = addEnchantmentToRegister(MlgEnchantment.newInstance());

    /**
     * Sets a {@link ConfigurableEnchantment} to be registered if it's enabled and returns it.
     *
     * @param enchantment The enchantment to be registered.
     * @return The enchantment.
     */
    private static <T extends ConfigurableEnchantment> T addEnchantmentToRegister(T enchantment) {
        if (enchantment.isEnabled()) {
            enchantmentsToRegister.add(enchantment);
        }

        return enchantment;
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
