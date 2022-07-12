package net.darmo_creations.naissancee.network;

import net.darmo_creations.naissancee.network.packets.Packet;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

public interface ServerPacketHandler<T extends Packet> {
  void onPacket(MinecraftServer server, ServerPlayerEntity player, final T packet);
}
