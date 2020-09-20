package voronoiaoc.byg.common.world.feature.features.overworld;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.common.Tags;
import voronoiaoc.byg.BYG;
import voronoiaoc.byg.common.world.feature.config.BYGBoulderFeatureConfig;
import voronoiaoc.byg.common.world.worldtype.noise.fastnoise.FastNoise;

import java.util.Random;

public class StackableBoulders extends Feature<BYGBoulderFeatureConfig> {

    public StackableBoulders(Codec<BYGBoulderFeatureConfig> configCodec) {
        super(configCodec);
    }

    protected long seed;
    protected static FastNoise fastNoise;

    public static int stopSpamInt = 0;

    @Override
    public boolean func_241855_a(ISeedReader world, ChunkGenerator chunkGenerator, Random random, BlockPos position, BYGBoulderFeatureConfig config) {
        setSeed(world.getSeed());

        BlockPos.Mutable mutable = new BlockPos.Mutable().setPos(position.down(2 + random.nextInt(10)));
        BlockPos.Mutable mutable2 = new BlockPos.Mutable().setPos(mutable);
        int stackHeight = random.nextInt(config.getMaxPossibleHeight()) + config.getMinHeight();
        int radius = random.nextInt(config.getMaxPossibleRadius()) + config.getMinRadius();


        for (int boulderIDX = 0; boulderIDX < stackHeight; boulderIDX++) {
            //Randomize the movement.
            int moveOnX = random.nextInt(4);

            if (random.nextInt(2) == 0)
                moveOnX = -moveOnX;

            int moveOnZ = random.nextInt(4);

            if (random.nextInt(2) == 1)
                moveOnZ = -moveOnZ;

            mutable.move(moveOnX,(int) (random.nextInt(Math.abs(radius) + 1) * 0.2f + radius * 0.8f) - 2, moveOnZ);

            for (int x = -radius; x <= radius; x++) {
                for (int y = -radius; y <= radius; y++) {
                    for (int z = -radius; z <= radius; z++) {

                        int squaredDistance = x * x + y * y + z * z;
                        if (squaredDistance <= radius * radius) {

                            mutable2.setPos(mutable).move(x, y, z);

                            // Rough the surface of the boulders a bit
                            double boulderRoughnessNoise = fastNoise.GetNoise(mutable2.getX() * 0.04F, mutable2.getY() * 0.01F, mutable2.getZ() * 0.04F);

                            if (squaredDistance > radius * radius * 0.8f && boulderRoughnessNoise > -0.3D && boulderRoughnessNoise < 0.3D)
                                continue;


                            BlockState blockState = world.getBlockState(mutable2);
                            if (this.canBlockPlaceHere(blockState))
                                world.setBlockState(mutable2, config.getBlockProvider().getBlockState(random, mutable2), 3);
                        }
                    }
                }
            }

            while (mutable.getY() < world.getHeight() && !world.getBlockState(mutable).isAir()) {
                mutable.move(Direction.UP);
            }

            if (random.nextInt(9) == 0)
                radius = (int) (radius * 1.75);
            else
                radius = (int) (radius / 1.2);

            if (3 > radius) {
                if (stopSpamInt == 0) {
                    BYG.LOGGER.debug("BYG: Boulder Radius is too small to continue stacking! Stack stopping at stack height: " + boulderIDX + "\nPlease lower the stack height or increase the boulder radius.");
                    stopSpamInt++;
                }
                break;
            }
        }
        return true;
    }


    public void setSeed(long seed) {
        if (this.seed != seed || fastNoise == null) {
            fastNoise = new FastNoise((int) seed);
            fastNoise.SetNoiseType(FastNoise.NoiseType.Simplex);
            this.seed = seed;
        }
    }

    private boolean canBlockPlaceHere(BlockState state) {
        return state.isAir() || state.getMaterial() == Material.EARTH || state.getMaterial() == Material.PLANTS ||
                state.getMaterial() == Material.TALL_PLANTS || state.getMaterial() == Material.LEAVES ||
                state.getMaterial() == Material.SAND || state.getMaterial() == Material.BAMBOO || state.getMaterial() == Material.CACTUS
                || state.getMaterial() == Material.WATER || state.getMaterial() == Material.LAVA || state.isIn(Tags.Blocks.DIRT);
    }
}