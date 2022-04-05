package io.github.shaksternano.entranced.mixin.commonloader.client.enchantingtablefilter;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.shaksternano.entranced.commonside.gui.EnchantingCatalystPanel;
import io.github.shaksternano.entranced.mixin.commonloader.client.accessor.HandledScreenAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.EnchantmentScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(EnchantmentScreen.class)
abstract class EnchantmentScreenMixin extends HandledScreenMixin {

    @SuppressWarnings("unused")
    private EnchantmentScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "drawBackground", at = @At("RETURN"))
    private void entranced$addEnchantingCatalystGui(MatrixStack matrices, float delta, int mouseX, int mouseY, CallbackInfo ci) {
        RenderSystem.setShaderTexture(0, EnchantingCatalystPanel.INSTANCE.getGuiTexture());

        /*
        Need to use this instead of using a @Shadow on the x and y fields to avoid a NoSuchFieldError on Forge.
         */
        HandledScreenAccessor handledScreenAccessor = (HandledScreenAccessor) this;
        int x = handledScreenAccessor.entranced$getX();
        int y = handledScreenAccessor.entranced$getY();

        drawTexture(
                matrices,
                EnchantingCatalystPanel.INSTANCE.getTextureX(x),
                EnchantingCatalystPanel.INSTANCE.getTextureY(y),
                0,
                0,
                EnchantingCatalystPanel.INSTANCE.getGuiTextureWidth(),
                EnchantingCatalystPanel.INSTANCE.getGuiTextureHeight()
        );
    }

    @Override
    protected void entranced$expandBounds(double mouseX, double mouseY, int left, int top, int button, CallbackInfoReturnable<Boolean> cir) {
        /*
        Need to use this instead of using a @Shadow on the x and y fields to avoid a NoSuchFieldError on Forge.
         */
        HandledScreenAccessor handledScreenAccessor = (HandledScreenAccessor) this;
        int x = handledScreenAccessor.entranced$getX();
        int y = handledScreenAccessor.entranced$getY();

        if (mouseX >= EnchantingCatalystPanel.INSTANCE.getTextureX(x)) {
            if (mouseX <= EnchantingCatalystPanel.INSTANCE.getTextureX(x) + EnchantingCatalystPanel.INSTANCE.getGuiTextureWidth()) {
                if (mouseY >= EnchantingCatalystPanel.INSTANCE.getTextureY(y)) {
                    if (mouseY <= EnchantingCatalystPanel.INSTANCE.getTextureY(y) + EnchantingCatalystPanel.INSTANCE.getGuiTextureHeight()) {
                        cir.setReturnValue(false);
                    }
                }
            }
        }
    }
}
