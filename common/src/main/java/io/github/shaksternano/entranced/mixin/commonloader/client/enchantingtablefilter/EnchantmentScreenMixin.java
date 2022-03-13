package io.github.shaksternano.entranced.mixin.commonloader.client.enchantingtablefilter;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.shaksternano.entranced.commonside.Entranced;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.EnchantmentScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.EnchantmentScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(EnchantmentScreen.class)
abstract class EnchantmentScreenMixin extends HandledScreen<EnchantmentScreenHandler> {

    private static final Identifier ENCHANTING_CATALYST_GUI_TEXTURE = new Identifier(Entranced.MOD_ID, "textures/gui/container/test.png");

    @SuppressWarnings("unused")
    private EnchantmentScreenMixin(EnchantmentScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Inject(method = "drawBackground", at = @At("RETURN"))
    private void addEnchantingCatalystGui(MatrixStack matrices, float delta, int mouseX, int mouseY, CallbackInfo ci) {
        /*
        int x = (this.width - this.backgroundWidth) / 2;
        int y = (this.height - this.backgroundHeight) / 2;
        RenderSystem.setShaderTexture(0, ENCHANTING_CATALYST_GUI_TEXTURE);
        drawTexture(matrices, x + 100, y, 0, 0, backgroundWidth, backgroundHeight);

         */
    }
}
