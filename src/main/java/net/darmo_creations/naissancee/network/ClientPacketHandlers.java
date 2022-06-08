package net.darmo_creations.naissancee.network;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;

import java.util.UUID;

/**
 * Gathers all client-side packet handlers.
 */
public final class ClientPacketHandlers {
  /**
   * Handler for entity spawn packets.
   *
   * @param client  Game client instance.
   * @param byteBuf Packet’s payload.
   * @see ServerToClientPacketFactory#createEntitySpawnPacket(Entity)
   */
  public static void handleEntitySpawnPacket(MinecraftClient client, final PacketByteBuf byteBuf) {
    EntityType<?> entityType = Registry.ENTITY_TYPE.get(byteBuf.readVarInt());
    UUID uuid = byteBuf.readUuid();
    int entityId = byteBuf.readVarInt();
    Vec3d pos = PacketBufUtil.readVec3d(byteBuf);
    float pitch = byteBuf.readFloat();
    float yaw = byteBuf.readFloat();
    client.executeTask(() -> {
      ClientWorld world = MinecraftClient.getInstance().world;
      if (world == null) {
        throw new IllegalStateException("tried to spawn entity in a null world");
      }
      Entity e = entityType.create(world);
      if (e == null) {
        throw new IllegalStateException("failed to create instance of entity \"%s\""
            .formatted(Registry.ENTITY_TYPE.getId(entityType)));
      }
      e.updateTrackedPosition(pos);
      e.setPos(pos.x, pos.y, pos.z);
      e.setPitch(pitch);
      e.setYaw(yaw);
      e.setId(entityId);
      e.setUuid(uuid);
      world.addEntity(entityId, e);
    });
  }

  private ClientPacketHandlers() {
  }
}
