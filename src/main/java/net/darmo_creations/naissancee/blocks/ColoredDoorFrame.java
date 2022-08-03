package net.darmo_creations.naissancee.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

/**
 * A block acting as the sides of a door frame.
 */
public class ColoredDoorFrame extends HorizontalFacingBlock implements Colored {
  private final BlockColor color;

  private static final VoxelShape NORTH_SHAPE = createCuboidShape(-2, 0, 0, 18, 16, 2);
  private static final VoxelShape EAST_SHAPE = createCuboidShape(14, 0, -2, 16, 16, 18);
  private static final VoxelShape SOUTH_SHAPE = createCuboidShape(-2, 0, 14, 18, 16, 16);
  private static final VoxelShape WEST_SHAPE = createCuboidShape(0, 0, -2, 2, 16, 18);

  private static final VoxelShape NORTH_COLL_SHAPE = VoxelShapes.union(
      createCuboidShape(-2, 0, 0, 0, 16, 2),
      createCuboidShape(16, 0, 0, 18, 16, 2)
  );
  private static final VoxelShape EAST_COLL_SHAPE = VoxelShapes.union(
      createCuboidShape(14, 0, -2, 16, 16, 0),
      createCuboidShape(14, 0, 16, 16, 16, 18)
  );
  private static final VoxelShape SOUTH_COLL_SHAPE = VoxelShapes.union(
      createCuboidShape(-2, 0, 14, 0, 16, 16),
      createCuboidShape(16, 0, 14, 18, 16, 16)
  );
  private static final VoxelShape WEST_COLL_SHAPE = VoxelShapes.union(
      createCuboidShape(0, 0, -2, 2, 16, 0),
      createCuboidShape(0, 0, 16, 2, 16, 18)
  );

  /**
   * Create a door frame of the given color.
   *
   * @param color Frame’s color.
   */
  public ColoredDoorFrame(final BlockColor color) {
    super(NaissanceEBlock.getSettings(FabricBlockSettings.of(Material.STONE, color.getMapColor()).sounds(BlockSoundGroup.STONE)));
    this.color = color;
  }

  @Override
  public BlockColor getColor() {
    return this.color;
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    super.appendProperties(builder.add(FACING));
  }

  @Override
  public BlockState getPlacementState(ItemPlacementContext ctx) {
    return this.getDefaultState().with(FACING, ctx.getPlayerFacing());
  }

  @SuppressWarnings("deprecation")
  @Override
  public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    return switch (state.get(FACING)) {
      case NORTH -> NORTH_SHAPE;
      case SOUTH -> SOUTH_SHAPE;
      case WEST -> WEST_SHAPE;
      case EAST -> EAST_SHAPE;
      default -> VoxelShapes.empty();
    };
  }

  @SuppressWarnings("deprecation")
  @Override
  public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    return switch (state.get(FACING)) {
      case NORTH -> NORTH_COLL_SHAPE;
      case SOUTH -> SOUTH_COLL_SHAPE;
      case WEST -> WEST_COLL_SHAPE;
      case EAST -> EAST_COLL_SHAPE;
      default -> VoxelShapes.empty();
    };
  }
}
