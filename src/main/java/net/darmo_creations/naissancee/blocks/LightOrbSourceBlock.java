package net.darmo_creations.naissancee.blocks;

import net.darmo_creations.naissancee.entities.LightOrbEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

/**
 * An invisible, light-emitting block placed by light orb entities.
 *
 * @see LightOrbEntity
 */
public class LightOrbSourceBlock extends VariableLightBlock {
  public LightOrbSourceBlock() {
    super(FabricBlockSettings.of(Material.AIR).air());
  }

  @SuppressWarnings("deprecation")
  @Override
  public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    return VoxelShapes.empty();
  }

  @SuppressWarnings("deprecation")
  @Override
  public BlockRenderType getRenderType(BlockState state) {
    return BlockRenderType.INVISIBLE;
  }
}
