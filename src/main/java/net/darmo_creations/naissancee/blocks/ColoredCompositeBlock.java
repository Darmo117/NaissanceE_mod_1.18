package net.darmo_creations.naissancee.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

public class ColoredCompositeBlock extends CompositeBlock implements Colored {
  private final BlockColor color;

  /**
   * Creates a corner block for the given color.
   *
   * @param color Block’s color.
   */
  public ColoredCompositeBlock(final BlockColor color) {
    super(FabricBlockSettings.of(Material.STONE, color.getMapColor()).sounds(BlockSoundGroup.STONE));
    this.color = color;
  }

  @Override
  public BlockColor getColor() {
    return this.color;
  }
}
