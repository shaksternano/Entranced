package io.github.shaksternano.entranced.mixin.commonloader.commonside.plugin.enchdesc;

import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(TranslatableText.class)
abstract class TranslatableTextMixin implements MutableText {

    @Shadow @Final private String key;

    private static final Set<String> entranced$descriptionsToModify = Set.of(
            "enchantment.minecraft.infinity.desc"
    );

    @Inject(method = "<init>*", at = @At(value = "RETURN"))
    private void entranced$addExtraDescription(String string, CallbackInfo ci) {
        if (key != null) {
            if (entranced$descriptionsToModify.contains(key)) {
                append(new TranslatableText(key + ".entranced.extraDesc"));
            }
        }
    }
}
