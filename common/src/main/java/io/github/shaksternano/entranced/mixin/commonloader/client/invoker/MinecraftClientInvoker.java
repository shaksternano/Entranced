package io.github.shaksternano.entranced.mixin.commonloader.client.invoker;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public interface MinecraftClientInvoker {

    @SuppressWarnings("UnusedReturnValue")
    @Invoker("doAttack")
    boolean entranced$invokeDoAttack();
}
