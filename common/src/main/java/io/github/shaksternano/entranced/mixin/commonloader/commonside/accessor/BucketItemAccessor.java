package io.github.shaksternano.entranced.mixin.commonloader.commonside.accessor;

import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BucketItem.class)
public interface BucketItemAccessor {

    @Accessor("fluid")
    Fluid entranced$getFluid();
}
