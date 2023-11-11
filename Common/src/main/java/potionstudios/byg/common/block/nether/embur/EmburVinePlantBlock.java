package potionstudios.byg.common.block.nether.embur;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import potionstudios.byg.common.block.BYGBlocks;

public class EmburVinePlantBlock extends GrowingPlantBodyBlock {
    public static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

    public EmburVinePlantBlock(Properties properties) {
        super(properties, Direction.DOWN, SHAPE, true);
    }

    protected @NotNull GrowingPlantHeadBlock getHeadBlock() {
        return (GrowingPlantHeadBlock) BYGBlocks.EMBUR_GEL_VINES.get();
    }

    public void entityInside(@NotNull BlockState state, @NotNull Level worldIn, @NotNull BlockPos pos, Entity entityIn) {
        entityIn.makeStuckInBlock(state, new Vec3(0.8F, 0.75D, 0.8F));
    }
}

