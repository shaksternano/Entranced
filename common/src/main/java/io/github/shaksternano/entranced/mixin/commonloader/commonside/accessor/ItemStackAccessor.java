package io.github.shaksternano.entranced.mixin.commonloader.commonside.accessor;

import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemStack.class)
public interface ItemStackAccessor {

    @Accessor("REPAIR_COST_KEY")
    static String entranced$getRepairCostKey() {
        throw new AssertionError();
    }
}
