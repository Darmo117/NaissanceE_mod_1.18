package net.darmo_creations.naissancee.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

/**
 * A block acting as the top of a door frame.
 * A lamp is added when the player placing the block is sneaking.
 */
public class ColoredDoorFrameTop extends HorizontalFacingBlock implements Colored {
  public static final BooleanProperty LAMP = BooleanProperty.of("lamp");

  private static final VoxelShape NORTH_FRAME_SHAPE = createCuboidShape(-2, 0, 0, 18, 2, 2);
  private static final VoxelShape EAST_FRAME_SHAPE = createCuboidShape(14, 0, -2, 16, 2, 18);
  private static final VoxelShape SOUTH_FRAME_SHAPE = createCuboidShape(-2, 0, 14, 18, 2, 16);
  private static final VoxelShape WEST_FRAME_SHAPE = createCuboidShape(0, 0, -2, 2, 2, 18);

  public static final VoxelShape NORTH_LAMP_SHAPE = createCuboidShape(4, 4, 0, 12, 12, 2);
  public static final VoxelShape WEST_LAMP_SHAPE = createCuboidShape(0, 4, 4, 2, 12, 12);
  public static final VoxelShape SOUTH_LAMP_SHAPE = createCuboidShape(4, 4, 14, 12, 12, 16);
  public static final VoxelShape EAST_LAMP_SHAPE = createCuboidShape(14, 4, 4, 16, 12, 12);

  private final BlockColor color;

  /**
   * Create a door frame top of the given color.
   *
   * @param color Frame top’s color.
   */
  public ColoredDoorFrameTop(final BlockColor color) {
    super(NaissanceEBlock.getSettings(FabricBlockSettings.of(Material.STONE, color.getMapColor())
        .sounds(BlockSoundGroup.STONE)
        .luminance(state -> state.get(LAMP) ? 15 : 0)));
    this.color = color;
    this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH).with(LAMP, false));
  }

  @Override
  public BlockColor getColor() {
    return this.color;
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    super.appendProperties(builder.add(FACING, LAMP));
  }

  @Override
  public BlockState getPlacementState(ItemPlacementContext ctx) {
    //noinspection ConstantConditions
    return this.getDefaultState().with(FACING, ctx.getPlayerFacing()).with(LAMP, ctx.getPlayer().isSneaking());
  }

  @SuppressWarnings("deprecation")
  @Override
  public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    VoxelShape shape = switch (state.get(FACING)) {
      case NORTH -> NORTH_FRAME_SHAPE;
      case SOUTH -> SOUTH_FRAME_SHAPE;
      case WEST -> WEST_FRAME_SHAPE;
      case EAST -> EAST_FRAME_SHAPE;
      default -> VoxelShapes.empty();
    };
    if (state.get(LAMP)) {
      return VoxelShapes.union(shape, switch (state.get(FACING)) {
        case NORTH -> NORTH_LAMP_SHAPE;
        case SOUTH -> SOUTH_LAMP_SHAPE;
        case WEST -> WEST_LAMP_SHAPE;
        case EAST -> EAST_LAMP_SHAPE;
        default -> VoxelShapes.empty();
      });
    }
    return shape;
  }
}
