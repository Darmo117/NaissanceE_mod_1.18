package net.darmo_creations.naissancee;

import net.darmo_creations.naissancee.block_entities.ModBlockEntities;
import net.darmo_creations.naissancee.blocks.ModBlocks;
import net.darmo_creations.naissancee.entities.ModEntities;
import net.darmo_creations.naissancee.network.PacketBufUtil;
import net.darmo_creations.naissancee.network.ServerToClientPacketFactory;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;

import java.util.UUID;

/**
 * Client-side mod’s initializer.
 */
public class ClientInitializer implements ClientModInitializer {
  @Override
  public void onInitializeClient() {
    ModBlocks.registerBlockRenderLayers();
    ModBlockEntities.registerRenderers();
    ModEntities.registerRenderers();
    this.registerPacketReceivers();
  }

  private void registerPacketReceivers() {
    ClientPlayNetworking.registerGlobalReceiver(ServerToClientPacketFactory.ENTITY_SPAWN_PACKET_ID, ClientInitializer::handleEntitySpawnPacket);
  }

  private static void handleEntitySpawnPacket(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf byteBuf, PacketSender responseSender) {
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
}
