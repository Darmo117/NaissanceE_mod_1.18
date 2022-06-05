package net.darmo_creations.naissancee.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;

public class ColoredVerticalSlabBlock extends VerticalSlabBlock implements Colored {
  private final BlockColor color;

  public ColoredVerticalSlabBlock(final BlockColor color) {
    super(FabricBlockSettings.of(Material.STONE, color.getMapColor()));
    this.color = color;
  }

  @Override
  public BlockColor getColor() {
    return this.color;
  }
}
