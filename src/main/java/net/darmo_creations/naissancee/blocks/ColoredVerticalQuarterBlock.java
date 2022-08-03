package net.darmo_creations.naissancee.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

public class ColoredVerticalQuarterBlock extends VerticalQuarterBlock implements Colored {
  private final BlockColor color;

  public ColoredVerticalQuarterBlock(final BlockColor color) {
    super(FabricBlockSettings.of(Material.STONE, color.getMapColor()).sounds(BlockSoundGroup.STONE));
    this.color = color;
  }

  @Override
  public BlockColor getColor() {
    return this.color;
  }
}
