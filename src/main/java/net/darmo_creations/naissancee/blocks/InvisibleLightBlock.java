package net.darmo_creations.naissancee.blocks;

import net.darmo_creations.naissancee.Utils;
import net.darmo_creations.naissancee.block_entities.InvisibleLightBlockEntity;
import net.darmo_creations.naissancee.items.InvisibleLightTweakerItem;
import net.darmo_creations.naissancee.items.ModItems;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.function.Function;

/**
 * An invisible, non-tengible block that emits light. Light level can be set using
 * a {@link ModItems#INVISIBLE_LIGHT_TWEAKER} item.
 *
 * @see InvisibleLightBlockEntity
 * @see InvisibleLightTweakerItem
 */
public class InvisibleLightBlock extends BlockWithEntity {
  public static final IntProperty LIGHT_LEVEL = IntProperty.of("light_level", 0, 15);

  private static final VoxelShape SHAPE = Block.createCuboidShape(4, 4, 4, 12, 12, 12);

  public InvisibleLightBlock() {
    super(FabricBlockSettings.of(Material.AIR)
        .sounds(BlockSoundGroup.GLASS)
        .nonOpaque()
        .noCollision()
        .luminance(state -> state.get(LIGHT_LEVEL)));
    this.setDefaultState(this.getDefaultState().with(LIGHT_LEVEL, 15));
  }

  public void increaseLightLevel(World world, BlockPos pos) {
    this.setLightLevel(world, pos, l -> (l + 1) % 16);
  }

  public void decreaseLightLevel(World world, BlockPos pos) {
    this.setLightLevel(world, pos, l -> Utils.trueModulo(l - 1, 16));
  }

  private void setLightLevel(World world, BlockPos pos, Function<Integer, Integer> transformer) {
    BlockState blockState = world.getBlockState(pos);
    int lightLevel = blockState.get(LIGHT_LEVEL);
    world.setBlockState(pos, blockState.with(LIGHT_LEVEL, transformer.apply(lightLevel)));
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    super.appendProperties(builder.add(LIGHT_LEVEL));
  }

  @SuppressWarnings("deprecation")
  @Override
  public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    return VoxelShapes.empty();
  }

  @SuppressWarnings("deprecation")
  @Override
  public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    return SHAPE;
  }

  @Override
  public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
    return new InvisibleLightBlockEntity(pos, state);
  }

  @Override
  public BlockRenderType getRenderType(BlockState state) {
    return BlockRenderType.INVISIBLE;
  }
}
