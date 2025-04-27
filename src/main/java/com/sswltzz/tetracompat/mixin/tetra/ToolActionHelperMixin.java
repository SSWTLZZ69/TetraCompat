package com.sswltzz.tetracompat.mixin.tetra;

import com.sswltzz.tetracompat.integration.ExDeorumUtil;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import se.mickelus.tetra.TetraToolActions;
import se.mickelus.tetra.util.ToolActionHelper;


@Mixin(ToolActionHelper.class)
public abstract class ToolActionHelperMixin {
    @Inject(method = "isEffectiveOn(Lnet/minecraftforge/common/ToolAction;Lnet/minecraft/world/level/block/state/BlockState;)Z", at = @At(value = "RETURN"), cancellable = true, remap = false)
    private static void isEffectiveOnMixin(ToolAction action, BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue())
            if (action == TetraToolActions.hammer && ExDeorumUtil.getHammerRecipe(state.getBlock().asItem()) != null)
                cir.setReturnValue(true);
    }
}
