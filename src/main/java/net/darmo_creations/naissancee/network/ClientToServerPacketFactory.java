package net.darmo_creations.naissancee.network;

import io.netty.buffer.Unpooled;
import net.darmo_creations.naissancee.NaissanceE;
import net.darmo_creations.naissancee.block_entities.PathCheckpoint;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.List;

/**
 * A class that defines static methods that create packets to be sent from the client to the server.
 */
public class ClientToServerPacketFactory {
  public static final Identifier LIGHT_ORB_CONTROLLER_DATA_PACKET_ID =
      new Identifier(NaissanceE.MODID, "light_orb_controller_data_packet");

  /**
   * Creates a byte buffer to serve as payload for a packet to send light orb controller’s data to the server.
   */
  public static PacketByteBuf createLightOrbControllerBEPacketByteBuffer(
      final BlockPos pos, final boolean active, final boolean loops, final boolean invisible, final int lightLevel,
      final double speed, final List<PathCheckpoint> checkpoints
  ) {
    PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
    buf.writeBlockPos(pos);
    buf.writeBoolean(active);
    buf.writeBoolean(loops);
    buf.writeBoolean(invisible);
    buf.writeInt(lightLevel);
    buf.writeDouble(speed);
    buf.writeInt(checkpoints.size());
    checkpoints.forEach(checkpoint -> buf.writeNbt(checkpoint.toNBT()));
    return buf;
  }
}
