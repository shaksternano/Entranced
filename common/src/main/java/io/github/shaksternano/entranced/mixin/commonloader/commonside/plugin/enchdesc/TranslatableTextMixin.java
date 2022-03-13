package io.github.shaksternano.entranced.mixin.commonloader.commonside.plugin.enchdesc;

import io.github.shaksternano.entranced.commonside.Entranced;
import io.github.shaksternano.entranced.commonside.config.EntrancedConfig;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(TranslatableText.class)
abstract class TranslatableTextMixin implements MutableText {

    @Shadow
    @Final
    private String key;

    @Unique
    private static final Set<String> ENTRANCED$DESCRIPTIONS_TO_MODIFY = Set.of(
            "enchantment.minecraft.infinity.desc"
    );

    @Inject(method = "<init>(Ljava/lang/String;)V", at = @At(value = "RETURN"))
    private void entranced$addExtraDescription(String key, CallbackInfo ci) {
        entranced$addExtraDescriptionImpl();
    }

    @Inject(method = "<init>(Ljava/lang/String;[Ljava/lang/Object;)V", at = @At("RETURN"))
    private void entranced$addExtraDescription(String key, Object[] args, CallbackInfo ci) {
        entranced$addExtraDescriptionImpl();
    }

    private void entranced$addExtraDescriptionImpl() {
        if (key != null) {
            EntrancedConfig config = Entranced.getConfig();

            if (config != null) {
                if (config.isInfinityAllowedOnBuckets()) {
                    if (ENTRANCED$DESCRIPTIONS_TO_MODIFY.contains(key)) {
                        append(new TranslatableText(key + ".entranced.extraDesc"));
                    }
                }
            }
        }
    }
}
