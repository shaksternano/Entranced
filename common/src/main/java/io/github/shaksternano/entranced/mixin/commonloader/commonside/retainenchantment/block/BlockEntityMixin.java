package io.github.shaksternano.entranced.mixin.commonloader.commonside.retainenchantment.block;

import io.github.shaksternano.entranced.commonside.Entranced;
import io.github.shaksternano.entranced.commonside.access.EnchantmentHolder;
import io.github.shaksternano.entranced.commonside.util.BlockEntityUtil;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEntity.class)
abstract class BlockEntityMixin implements EnchantmentHolder {

    private NbtElement entranced$enchantments;
    private Integer entranced$repairCost;

    // Adds enchantments and repair cost to NBT, for example this will allow the enchantments and repair cost to be shown when the /data command is used.
    @Inject(method = "writeNbt", at = @At("RETURN"))
    private void getEnchantmentsForNbt(NbtCompound nbt, CallbackInfo ci) {
        if (Entranced.getConfig().isBlockEntitiesStoreEnchantments()) {
            if (entranced$enchantments != null) {
                nbt.put("Enchantments", entranced$enchantments);
            }

            if (entranced$repairCost != null) {
                nbt.putInt("RepairCost", entranced$repairCost);
            }
        }
    }

    // Sets the enchantments and repair cost from NBT, for example when the enchantments and repair cost are specified when using the /setblock command.
    @Inject(method = "readNbt", at = @At("RETURN"))
    private void setEnchantmentsFromNbt(NbtCompound nbt, CallbackInfo ci) {
        if (Entranced.getConfig().isBlockEntitiesStoreEnchantments()) {
            NbtElement enchantments = nbt.get("Enchantments");
            if (enchantments != null) {
                this.entranced$enchantments = enchantments.copy();
            }

            if (nbt.contains("RepairCost", 3)) {
                entranced$repairCost = nbt.getInt("RepairCost");
            }
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Inject(method = "setStackNbt", at = @At("RETURN"))
    private void setBlockEntityItemEnchantments(ItemStack stack, CallbackInfo ci) {
        if (Entranced.getConfig().isBlockEntitiesStoreEnchantments()) {
            BlockEntityUtil.setDroppedItemStackEnchantments((BlockEntity) (Object) this, stack);
        }
    }

    @Override
    public NbtElement entranced$getEnchantments() {
        return entranced$enchantments.copy();
    }

    @Override
    public void entranced$setEnchantments(NbtElement enchantments) {
        entranced$enchantments = enchantments.copy();
    }

    @Override
    public Integer entranced$getRepairCost() {
        return entranced$repairCost;
    }

    @Override
    public void entranced$setRepairCost(Integer repairCost) {
        entranced$repairCost = repairCost;
    }
}
