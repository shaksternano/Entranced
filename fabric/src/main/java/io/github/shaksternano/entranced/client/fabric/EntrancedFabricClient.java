package io.github.shaksternano.entranced.client.fabric;

import io.github.shaksternano.entranced.commonside.Entranced;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class EntrancedFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        Entranced.INSTANCE.clientInit();
    }
}
