package io.github.shaksternano.entranced.commonside.fabric;

import io.github.shaksternano.entranced.commonside.Entranced;
import net.fabricmc.api.ModInitializer;

public class EntrancedFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        Entranced.INSTANCE.init();
    }
}
