package net.darmo_creations.naissancee.network;

import io.netty.buffer.Unpooled;
import net.darmo_creations.naissancee.NaissanceE;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * A class that defines static methods that create packets to be sent from the server to a client.
 */
public class ServerToClientPacketFactory {
  public static final Identifier ENTITY_SPAWN_PACKET_ID = new Identifier(NaissanceE.MODID, "entity_spawn_packet");

  public static Packet<?> createEntitySpawnPacket(final Entity e) {
    if (e.world.isClient()) {
      throw new IllegalStateException("ServerToClientPacketFactory.createEntitySpawnPacket called on client side");
    }

    PacketByteBuf byteBuf = new PacketByteBuf(Unpooled.buffer());
    byteBuf.writeVarInt(Registry.ENTITY_TYPE.getRawId(e.getType()));
    byteBuf.writeUuid(e.getUuid());
    byteBuf.writeVarInt(e.getId());
    PacketBufUtil.writeVec3d(byteBuf, e.getPos());
    byteBuf.writeFloat(e.getPitch());
    byteBuf.writeFloat(e.getYaw());

    return ServerPlayNetworking.createS2CPacket(ENTITY_SPAWN_PACKET_ID, byteBuf);
  }
}
