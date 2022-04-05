package io.github.shaksternano.entranced.mixin.commonloader.client.accessor;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Environment(EnvType.CLIENT)
@Mixin(HandledScreen.class)
public interface HandledScreenAccessor {

    @Accessor("x")
    int entranced$getX();

    @Accessor("y")
    int entranced$getY();

    @Accessor("backgroundWidth")
    int entranced$getBackgroundWidth();

    @Accessor("backgroundHeight")
    int entranced$getBackgroundHeight();
}
