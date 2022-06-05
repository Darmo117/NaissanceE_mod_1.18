package net.darmo_creations.naissancee.blocks;

import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.Direction;

import javax.annotation.Nullable;

public enum VerticalSlabType implements StringIdentifiable {
  NORTH("north", Direction.NORTH),
  SOUTH("south", Direction.SOUTH),
  EAST("east", Direction.EAST),
  WEST("west", Direction.WEST),
  DOUBLE("double", null);

  private final String name;
  private final Direction direction;

  VerticalSlabType(final String name, Direction direction) {
    this.name = name;
    this.direction = direction;
  }

  @Nullable
  public Direction getDirection() {
    return this.direction;
  }

  @Override
  public String asString() {
    return this.name;
  }

  @Override
  public String toString() {
    return this.asString();
  }

  public static VerticalSlabType forDirection(Direction direction) {
    return switch (direction) {
      case NORTH -> NORTH;
      case SOUTH -> SOUTH;
      case WEST -> WEST;
      case EAST -> EAST;
      default -> null;
    };
  }
}
