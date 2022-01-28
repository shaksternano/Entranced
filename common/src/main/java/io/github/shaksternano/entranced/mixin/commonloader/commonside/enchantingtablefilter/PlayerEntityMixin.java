package io.github.shaksternano.entranced.mixin.commonloader.commonside.enchantingtablefilter;

import io.github.shaksternano.entranced.commonside.Entranced;
import io.github.shaksternano.entranced.commonside.access.EnchantingCatalystHolder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
abstract class PlayerEntityMixin implements EnchantingCatalystHolder {

    @Unique
    @Nullable
    private Item entranced$lastUsedEnchantingCatalyst;

    @Unique
    private static final String ENTRANCED$LAST_USED_ENCHANTING_CATALYST_KEY = "entranced$lastUsedEnchantingCatalyst";

    @Inject(method = "readCustomDataFromNbt", at = @At("HEAD"))
    private void entranced$loadLastUsedEnchantingCatalyst(NbtCompound nbt, CallbackInfo ci) {
        if (Entranced.getConfig().isEnchantingCatalystEnabled()) {
            if (nbt.contains(ENTRANCED$LAST_USED_ENCHANTING_CATALYST_KEY, 8)) {
                Identifier itemId = new Identifier(nbt.getString(ENTRANCED$LAST_USED_ENCHANTING_CATALYST_KEY));
                entranced$lastUsedEnchantingCatalyst = Registry.ITEM.get(itemId);
            }
        }
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
    private void entranced$readLastUsedEnchantingCatalyst(NbtCompound nbt, CallbackInfo ci) {
        if (Entranced.getConfig().isEnchantingCatalystEnabled()) {
            if (entranced$lastUsedEnchantingCatalyst != null) {
                Identifier itemId = Registry.ITEM.getId(entranced$lastUsedEnchantingCatalyst);
                nbt.putString(ENTRANCED$LAST_USED_ENCHANTING_CATALYST_KEY, itemId.toString());
            }
        }
    }

    @Unique
    @Nullable
    @Override
    public Item entranced$getEnchantingCatalyst() {
        return entranced$lastUsedEnchantingCatalyst;
    }

    @Unique
    @Override
    public void entranced$setEnchantingCatalyst(Item enchantingCatalyst) {
        entranced$lastUsedEnchantingCatalyst = enchantingCatalyst;
    }
}
