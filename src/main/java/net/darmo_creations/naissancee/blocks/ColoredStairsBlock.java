package net.darmo_creations.naissancee.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.StairsBlock;

public class ColoredStairsBlock extends StairsBlock implements Colored {
  private final BlockColor color;

  /**
   * Creates stairs block for the given colored block.
   *
   * @param baseBlock Colored block to use a base.
   */
  public ColoredStairsBlock(ColoredBlock baseBlock) {
    super(baseBlock.getDefaultState(),
        FabricBlockSettings.of(baseBlock.getDefaultState().getMaterial(), baseBlock.getColor().getMapColor()));
    this.color = baseBlock.getColor();
  }

  @Override
  public BlockColor getColor() {
    return this.color;
  }
}
