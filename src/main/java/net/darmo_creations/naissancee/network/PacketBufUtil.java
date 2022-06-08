package net.darmo_creations.naissancee.network;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.Vec3d;

public final class PacketBufUtil {
  /**
   * Writes a {@link Vec3d} to a {@link PacketByteBuf}.
   *
   * @param byteBuf destination buffer
   * @param vec3d   vector
   */
  public static void writeVec3d(PacketByteBuf byteBuf, final Vec3d vec3d) {
    byteBuf.writeDouble(vec3d.x);
    byteBuf.writeDouble(vec3d.y);
    byteBuf.writeDouble(vec3d.z);
  }

  /**
   * Reads a {@link Vec3d} from a {@link PacketByteBuf}.
   *
   * @param byteBuf source buffer
   * @return vector
   */
  public static Vec3d readVec3d(final PacketByteBuf byteBuf) {
    double x = byteBuf.readDouble();
    double y = byteBuf.readDouble();
    double z = byteBuf.readDouble();
    return new Vec3d(x, y, z);
  }

  private PacketBufUtil() {
  }
}
