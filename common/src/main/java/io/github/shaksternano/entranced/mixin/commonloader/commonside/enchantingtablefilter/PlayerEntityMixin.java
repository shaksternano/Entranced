package io.github.shaksternano.entranced.mixin.commonloader.commonside.enchantingtablefilter;

import io.github.shaksternano.entranced.commonside.Entranced;
import io.github.shaksternano.entranced.commonside.access.enchantingtablefilter.EnchantingCatalystTypeHolder;
import io.github.shaksternano.entranced.commonside.config.EnchantingCatalystConfig;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
abstract class PlayerEntityMixin implements EnchantingCatalystTypeHolder {

    @Unique
    @Nullable
    private EnchantingCatalystConfig.EnchantingCatalystType entranced$lastUsedEnchantingCatalystType;

    @Unique
    private static final String ENTRANCED$LAST_USED_ENCHANTING_CATALYST_TYPE_KEY = "entranced:lastUsedEnchantingCatalystType";

    @Inject(method = "readCustomDataFromNbt", at = @At("HEAD"))
    private void entranced$loadLastUsedEnchantingCatalyst(NbtCompound nbt, CallbackInfo ci) {
        if (Entranced.getConfig().isEnchantingCatalystEnabled()) {
            if (nbt.contains(ENTRANCED$LAST_USED_ENCHANTING_CATALYST_TYPE_KEY, 8)) {
                String catalystType = nbt.getString(ENTRANCED$LAST_USED_ENCHANTING_CATALYST_TYPE_KEY);

                try {
                    entranced$lastUsedEnchantingCatalystType = EnchantingCatalystConfig.EnchantingCatalystType.valueOf(catalystType);
                } catch (IllegalArgumentException e) {
                    Entranced.LOGGER.warn("The previously used enchanting catalyst type " + catalystType + " doesn't exist!");
                }
            }
        }
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
    private void entranced$readLastUsedEnchantingCatalyst(NbtCompound nbt, CallbackInfo ci) {
        if (Entranced.getConfig().isEnchantingCatalystEnabled()) {
            if (entranced$lastUsedEnchantingCatalystType != null) {
                nbt.putString(ENTRANCED$LAST_USED_ENCHANTING_CATALYST_TYPE_KEY, entranced$lastUsedEnchantingCatalystType.toString());
            }
        }
    }

    @Unique
    @Nullable
    @Override
    public EnchantingCatalystConfig.EnchantingCatalystType entranced$getEnchantingCatalystType() {
        return entranced$lastUsedEnchantingCatalystType;
    }

    @Unique
    @Override
    public void entranced$setEnchantingCatalystType(@Nullable EnchantingCatalystConfig.EnchantingCatalystType catalystType) {
        entranced$lastUsedEnchantingCatalystType = catalystType;
    }
}
