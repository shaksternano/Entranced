package io.github.shaksternano.entranced.mixin.commonloader.commonside.retainenchantment.block;

import io.github.shaksternano.entranced.commonside.Entranced;
import io.github.shaksternano.entranced.commonside.access.EnchantmentHolder;
import io.github.shaksternano.entranced.commonside.util.BlockEntityUtil;
import io.github.shaksternano.entranced.mixin.commonloader.commonside.accessor.ItemStackAccessor;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEntity.class)
abstract class BlockEntityMixin implements EnchantmentHolder {

    @Unique
    @Nullable
    private NbtElement entranced$enchantments;
    @Unique
    @Nullable
    private Integer entranced$repairCost;

    /*
    Adds enchantments and repair cost to NBT, for example this will allow the
    enchantments and repair cost to be shown when the /data command is used.
     */
    @Inject(method = "writeNbt", at = @At("RETURN"))
    private void entranced$getEnchantmentsForNbt(NbtCompound nbt, CallbackInfo ci) {
        if (Entranced.getConfig().isBlockEntitiesStoreEnchantments()) {
            if (entranced$enchantments != null) {
                nbt.put(ItemStack.ENCHANTMENTS_KEY, entranced$enchantments);
            }

            if (entranced$repairCost != null) {
                nbt.putInt(ItemStackAccessor.entranced$getRepairCostKey(), entranced$repairCost);
            }
        }
    }

    /*
    Sets the enchantments and repair cost from NBT, for example when the
    enchantments and repair cost are specified when using the /setblock command.
     */
    @Inject(method = "readNbt", at = @At("RETURN"))
    private void entranced$setEnchantmentsFromNbt(NbtCompound nbt, CallbackInfo ci) {
        if (Entranced.getConfig().isBlockEntitiesStoreEnchantments()) {
            NbtElement enchantments = nbt.get(ItemStack.ENCHANTMENTS_KEY);
            if (enchantments != null) {
                this.entranced$enchantments = enchantments.copy();
            }

            if (nbt.contains(ItemStackAccessor.entranced$getRepairCostKey(), 3)) {
                entranced$repairCost = nbt.getInt(ItemStackAccessor.entranced$getRepairCostKey());
            }
        }
    }

    /*
    Sets the enchantments and repair cost of the shulker box item dropped when a
    Shulker Box with enchantments and repair cost is broken by a player in creative mode.
     */
    @SuppressWarnings("ConstantConditions")
    @Inject(method = "setStackNbt", at = @At("RETURN"))
    private void entranced$setBlockEntityItemEnchantments(ItemStack stack, CallbackInfo ci) {
        if (Entranced.getConfig().isBlockEntitiesStoreEnchantments()) {
            BlockEntityUtil.setDroppedItemStackEnchantments((BlockEntity) (Object) this, stack);
        }
    }

    @Unique
    @Nullable
    @Override
    public NbtElement entranced$getEnchantments() {
        return entranced$enchantments == null ? null : entranced$enchantments.copy();
    }

    @Unique
    @Override
    public void entranced$setEnchantments(NbtElement enchantments) {
        entranced$enchantments = enchantments.copy();
    }

    @Unique
    @Nullable
    @Override
    public Integer entranced$getRepairCost() {
        return entranced$repairCost;
    }

    @Unique
    @Override
    public void entranced$setRepairCost(Integer repairCost) {
        entranced$repairCost = repairCost;
    }
}
