package net.darmo_creations.naissancee.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

/**
 * Custom ladder that does not disappear if the block it is placed against is removed.
 */
public class TwoPartLadderBlock extends LadderBlock implements Colored {
  private final BlockColor color;

  /**
   * Creates a ladder with the given color.
   *
   * @param color Ladder’s color.
   */
  public TwoPartLadderBlock(final BlockColor color) {
    super(FabricBlockSettings.of(Material.STONE, color.getMapColor()).sounds(BlockSoundGroup.STONE).nonOpaque());
    this.color = color;
  }

  @Override
  public BlockColor getColor() {
    return this.color;
  }

  @Override
  public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
    return true;
  }
}
