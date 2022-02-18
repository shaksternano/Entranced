package io.github.shaksternano.entranced.mixin.commonloader.commonside.enchantingtablefilter;

import com.google.common.base.Enums;
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

import java.util.Optional;

@Mixin(PlayerEntity.class)
abstract class PlayerEntityMixin implements EnchantingCatalystTypeHolder {

    @Unique
    @Nullable
    private EnchantingCatalystConfig.EnchantingCatalystType entranced$lastUsedEnchantingCatalystType;

    @Unique
    private static final String ENTRANCED$LAST_USED_ENCHANTING_CATALYST_TYPE_KEY = "Entranced:LastUsedEnchantingCatalystType";

    /**
     * Reads the last used enchanting catalyst from disk.
     */
    @Inject(method = "readCustomDataFromNbt", at = @At("HEAD"))
    private void entranced$loadLastUsedEnchantingCatalyst(NbtCompound nbt, CallbackInfo ci) {
        if (Entranced.INSTANCE.getConfig().isEnchantingCatalystEnabled()) {
            if (nbt.contains(ENTRANCED$LAST_USED_ENCHANTING_CATALYST_TYPE_KEY, 8)) {
                String catalystTypeString = nbt.getString(ENTRANCED$LAST_USED_ENCHANTING_CATALYST_TYPE_KEY);

                Enums.getIfPresent(EnchantingCatalystConfig.EnchantingCatalystType.class, catalystTypeString).toJavaUtil().ifPresentOrElse(
                        this::entranced$setEnchantingCatalystType,
                        () -> Entranced.LOGGER.info("The previously used enchanting catalyst type " + catalystTypeString + " doesn't exist anymore!")
                );
            }
        }
    }

    /**
     * Saves the last used enchanting catalyst to disk.
     */
    @Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
    private void entranced$readLastUsedEnchantingCatalyst(NbtCompound nbt, CallbackInfo ci) {
        if (Entranced.INSTANCE.getConfig().isEnchantingCatalystEnabled()) {
            entranced$getEnchantingCatalystType().ifPresent(lastUsedEnchantingCatalystType -> nbt.putString(ENTRANCED$LAST_USED_ENCHANTING_CATALYST_TYPE_KEY, lastUsedEnchantingCatalystType.toString()));
        }
    }

    @Override
    public Optional<EnchantingCatalystConfig.EnchantingCatalystType> entranced$getEnchantingCatalystType() {
        return Optional.ofNullable(entranced$lastUsedEnchantingCatalystType);
    }

    @Unique
    @Override
    public void entranced$setEnchantingCatalystType(@Nullable EnchantingCatalystConfig.EnchantingCatalystType catalystType) {
        entranced$lastUsedEnchantingCatalystType = catalystType;
    }
}
