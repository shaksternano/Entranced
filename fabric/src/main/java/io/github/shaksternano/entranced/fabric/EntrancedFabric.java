package io.github.shaksternano.entranced.fabric;

import io.github.shaksternano.entranced.EntrancedCommon;
import net.fabricmc.api.ModInitializer;

public class EntrancedFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        EntrancedCommon.init();
    }
}
