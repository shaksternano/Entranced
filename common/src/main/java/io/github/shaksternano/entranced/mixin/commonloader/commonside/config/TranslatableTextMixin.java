package io.github.shaksternano.entranced.mixin.commonloader.commonside.config;

import com.google.common.collect.ImmutableSet;
import io.github.shaksternano.entranced.commonside.Entranced;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TranslatableText.class)
abstract class TranslatableTextMixin {

    @Shadow @Mutable @Final private String key;

    @Unique
    private static final String ENTRANCED$CONFIG_KEY_START = "text.autoconfig." + Entranced.MOD_ID + ".option.";
    @Unique
    private static final String ENTRANCED$GENERIC_CONFIG_KEY_START = ENTRANCED$CONFIG_KEY_START + "generic.";

    @Unique
    private static final ImmutableSet<String> ENTRANCED$CONFIG_KEYS_ENDINGS_TO_CHANGE = ImmutableSet.of(
            "enabled",
            "rarity",
            "treasure",
            "soldByVillagers",
            "minPower",
            "maxPowerAboveMin"
    );

    @Inject(method = "<init>(Ljava/lang/String;)V", at = @At("RETURN"))
    private void entranced$setConfigOptionKey(String key, CallbackInfo ci) {
        entranced$setConfigOptionKeyImpl();
    }

    @Inject(method = "<init>(Ljava/lang/String;[Ljava/lang/Object;)V", at = @At("RETURN"))
    private void entranced$setConfigOptionKey(String key, Object[] args, CallbackInfo ci) {
        entranced$setConfigOptionKeyImpl();
    }

    /**
     * All enchantments added by this mod share the same config options and so the same config names,
     * but the config keys are different. This method changes the config key to a generic one so that
     * there doesn't have to be different keys for each enchantment when they all share the same translation.
     */
    @Unique
    private void entranced$setConfigOptionKeyImpl() {
        if (key != null) {
            if (key.startsWith(ENTRANCED$CONFIG_KEY_START)) {
                String configOptionKeyEnding = key.substring(key.lastIndexOf('.') + 1);

                if (ENTRANCED$CONFIG_KEYS_ENDINGS_TO_CHANGE.contains(configOptionKeyEnding)) {
                    key = ENTRANCED$GENERIC_CONFIG_KEY_START + configOptionKeyEnding;
                }
            }
        }
    }
}
