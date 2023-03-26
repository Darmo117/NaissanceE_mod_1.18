package net.darmo_creations.naissancee.network.packets;

import net.minecraft.network.PacketByteBuf;

/**
 * Packets are objects that can be sent between clients and server to synchronize data.
 */
public interface Packet {
  /**
   * Serializes this packet into a {@link PacketByteBuf} object.
   */
  PacketByteBuf getBuffer();
}
