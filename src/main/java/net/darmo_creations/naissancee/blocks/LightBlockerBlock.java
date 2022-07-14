package net.darmo_creations.naissancee.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Invisible block that entities can walk through but blocks light.
 * <p>
 * Implemented as a vertical slab as having a full hitbox would mess up render of neighboring blocks.
 */
public class LightBlockerBlock extends VerticalSlabBlock {
  protected static final VoxelShape NORTH_SHAPE = createCuboidShape(0, 0, 0, 16, 16, 0.1);
  protected static final VoxelShape SOUTH_SHAPE = createCuboidShape(0, 0, 15.9, 16, 16, 16);
  protected static final VoxelShape WEST_SHAPE = createCuboidShape(0, 0, 0, 0.1, 16, 16);
  protected static final VoxelShape EAST_SHAPE = createCuboidShape(15.9, 0, 0, 16, 16, 16);

  public LightBlockerBlock() {
    super(FabricBlockSettings.of(Material.AIR).sounds(BlockSoundGroup.STONE));
  }

  @SuppressWarnings("deprecation")
  @Override
  public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    return VoxelShapes.empty();
  }

  @Override
  public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    return switch (state.get(TYPE)) {
      case NORTH -> NORTH_SHAPE;
      case SOUTH -> SOUTH_SHAPE;
      case EAST -> EAST_SHAPE;
      case WEST -> WEST_SHAPE;
      case DOUBLE -> VoxelShapes.fullCube();
    };
  }

  @Override
  public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
    // Display particle whenever player is holding this block, as with minecraft:barrier and minecraft:light
    ClientPlayerEntity player = MinecraftClient.getInstance().player;
    if (player != null && player.isHolding(this.asItem())) {
      world.addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK_MARKER, state),
          pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0, 0, 0);
    }
  }

  @SuppressWarnings("deprecation")
  @Override
  public BlockRenderType getRenderType(BlockState state) {
    return BlockRenderType.INVISIBLE;
  }
}
