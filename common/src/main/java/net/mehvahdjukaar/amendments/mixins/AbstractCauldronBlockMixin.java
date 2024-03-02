package net.mehvahdjukaar.amendments.mixins;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.mehvahdjukaar.amendments.configs.CommonConfigs;
import net.mehvahdjukaar.amendments.events.behaviors.CauldronConversion;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractCauldronBlock.class)
public class AbstractCauldronBlockMixin extends Block {

    public AbstractCauldronBlockMixin(Properties properties) {
        super(properties);
    }

    // why is this not an event? because it's better for compatibility
    @ModifyReturnValue(method = "use", at = @At("RETURN"))
    public InteractionResult use(InteractionResult original,
                                 @Local BlockState state, @Local Level level, @Local BlockPos pos, @Local Player player,
                                 @Local InteractionHand hand, @Local ItemStack stack) {
        // do something
        if (original == InteractionResult.PASS && this == Blocks.CAULDRON && CommonConfigs.LIQUID_CAULDRON.get()) {
            return CauldronConversion.convert(state, pos, level, player, hand, stack);
        }
        return original;
    }

}
