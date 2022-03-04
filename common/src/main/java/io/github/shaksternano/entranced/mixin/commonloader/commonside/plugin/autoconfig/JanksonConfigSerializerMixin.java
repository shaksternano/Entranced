package io.github.shaksternano.entranced.mixin.commonloader.commonside.plugin.autoconfig;

import io.github.shaksternano.entranced.commonside.Entranced;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(value = JanksonConfigSerializer.class, remap = false)
abstract class JanksonConfigSerializerMixin<T extends ConfigData> {

    @Inject(method = "createDefault", at = @At("HEAD"), require = 0)
    private void entranced$detectDefault(CallbackInfoReturnable<T> cir) {
        Entranced.setCreatedNewConfigFile(true);
    }
}
