package net.darmo_creations.naissancee;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;

public final class Utils {
  /**
   * Returns the tile entity of the given class at the given position.
   *
   * @param blockEntityClass Tile entity’s class.
   * @param world           The world.
   * @param pos             Tile entity’s position.
   * @param <T>             Tile entity’s type.
   * @return The tile entity if found, an empty optional otherwise.
   */
  public static <T extends BlockEntity> Optional<T> getBlockEntity(Class<T> blockEntityClass, World world, BlockPos pos) {
    BlockEntity be = world.getBlockEntity(pos);
    if (blockEntityClass.isInstance(be)) {
      return Optional.of(blockEntityClass.cast(be));
    }
    return Optional.empty();
  }

  /**
   * Performs a true modulo operation using the mathematical definition of "a mod b".
   *
   * @param a Value to get the modulo of.
   * @param b The divisor.
   * @return a mod b
   */
  public static int trueModulo(int a, int b) {
    return ((a % b) + b) % b;
  }

  /**
   * Performs a true modulo operation using the mathematical definition of "a mod b".
   *
   * @param a Value to get the modulo of.
   * @param b The divisor.
   * @return a mod b
   */
  public static double trueModulo(double a, double b) {
    return ((a % b) + b) % b;
  }

  private Utils() {
  }
}
