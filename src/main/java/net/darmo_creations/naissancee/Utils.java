package net.darmo_creations.naissancee;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.Optional;

/**
 * This class defines various utility functions.
 */
public final class Utils {
  /**
   * Returns the block entity of the given class at the given position.
   *
   * @param blockEntityClass Block entity’s class.
   * @param world            The world.
   * @param pos              Block entity’s position.
   * @param <T>              Block entity’s type.
   * @return The block entity if found, an empty optional otherwise.
   */
  public static <T extends BlockEntity> Optional<T> getBlockEntity(Class<T> blockEntityClass, World world, BlockPos pos) {
    BlockEntity be = world.getBlockEntity(pos);
    if (blockEntityClass.isInstance(be)) {
      return Optional.of(blockEntityClass.cast(be));
    }
    return Optional.empty();
  }

  /**
   * Checks whether the given player holds any of the given items in at least one of its hands.
   *
   * @param player The player.
   * @param items  The items.
   * @return True if the player holds at least one of the items in one or both of its hands, false otherwise.
   */
  public static boolean playerHoldsAnyItem(PlayerEntity player, Item... items) {
    return Arrays.stream(items).anyMatch(
        item -> player.getMainHandStack().getItem() == item || player.getOffHandStack().getItem() == item
    );
  }

  /**
   * Converts a block position to a string.
   */
  public static String blockPosToString(BlockPos pos) {
    return "%d %d %d".formatted(pos.getX(), pos.getY(), pos.getZ());
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
   * Sends a chat message to a player. Does nothing if the world is remote (i.e. client-side).
   *
   * @param world       The world the player is in.
   * @param player      The player to send the message to.
   * @param text        Message’s text.
   * @param inActionBar Whether to send the message in the player’s action bar.
   */
  public static void sendMessage(final World world, PlayerEntity player, final Text text, final boolean inActionBar) {
    if (!world.isClient()) {
      player.sendMessage(text, inActionBar);
    }
  }

  private Utils() {
  }
}
