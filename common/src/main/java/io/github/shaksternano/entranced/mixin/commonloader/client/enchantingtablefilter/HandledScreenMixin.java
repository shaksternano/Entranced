package io.github.shaksternano.entranced.mixin.commonloader.client.enchantingtablefilter;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(HandledScreen.class)
abstract class HandledScreenMixin extends Screen {

    @SuppressWarnings("unused")
    protected HandledScreenMixin(Text title) {
        super(title);
    }

    @SuppressWarnings("CancellableInjectionUsage")
    @Inject(method = "isClickOutsideBounds", at = @At("HEAD"), cancellable = true)
    protected void entranced$expandBounds(double mouseX, double mouseY, int left, int top, int button, CallbackInfoReturnable<Boolean> cir) {
    }
}
