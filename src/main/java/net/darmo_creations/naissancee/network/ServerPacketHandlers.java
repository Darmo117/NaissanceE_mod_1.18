package net.darmo_creations.naissancee.network;

import net.darmo_creations.naissancee.NaissanceE;
import net.darmo_creations.naissancee.Utils;
import net.darmo_creations.naissancee.block_entities.LightOrbControllerBlockEntity;
import net.darmo_creations.naissancee.block_entities.PathCheckpoint;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

import java.util.LinkedList;
import java.util.List;

/**
 * Gathers all server-side packet handlers.
 */
public final class ServerPacketHandlers {
  /**
   * Handler for light orb controller block entity packets.
   *
   * @param server Server instance.
   * @param player Player that sent the packet.
   * @param buf    Packet’s payload.
   * @see ClientToServerPacketFactory#createLightOrbControllerBEPacketByteBuffer(BlockPos, boolean, boolean, boolean, int, double, List)
   */
  public static void handleLightOrbControllerBEPacket(MinecraftServer server, final ServerPlayerEntity player, final PacketByteBuf buf) {
    BlockPos pos = buf.readBlockPos();
    boolean active = buf.readBoolean();
    boolean loops = buf.readBoolean();
    boolean invisible = buf.readBoolean();
    int lightLevel = buf.readInt();
    double speed = buf.readDouble();
    final int checkpointsNb = buf.readInt();
    List<PathCheckpoint> checkpoints = new LinkedList<>();
    for (int i = 0; i < checkpointsNb; i++) {
      //noinspection ConstantConditions
      checkpoints.add(new PathCheckpoint(buf.readNbt()));
    }

    server.execute(() -> Utils.getBlockEntity(LightOrbControllerBlockEntity.class, player.world, pos)
        .ifPresent(controller -> {
          controller.setActive(active);
          controller.setLoops(loops);
          controller.setEntityInvisible(invisible);
          try {
            controller.setLightLevel(lightLevel);
            controller.setSpeed(speed);
            controller.setCheckpoints(checkpoints);
          } catch (IllegalArgumentException e) {
            NaissanceE.LOGGER.error(e.getMessage(), e);
            return;
          }
          controller.resetOrb();
        })
    );
  }

  private ServerPacketHandlers() {
  }
}
