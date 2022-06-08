package net.darmo_creations.naissancee;

import net.darmo_creations.naissancee.block_entities.ModBlockEntities;
import net.darmo_creations.naissancee.blocks.ModBlocks;
import net.darmo_creations.naissancee.network.ClientPacketHandlers;
import net.darmo_creations.naissancee.network.ServerToClientPacketFactory;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

/**
 * Client-side mod initializer.
 */
public class ClientInitializer implements ClientModInitializer {
  @Override
  public void onInitializeClient() {
    ModBlocks.registerBlockRenderLayers();
    ModBlockEntities.registerRenderers();
    this.registerPacketReceivers();
  }

  /**
   * Registers all client-side packet handlers.
   */
  private void registerPacketReceivers() {
    ClientPlayNetworking.registerGlobalReceiver(ServerToClientPacketFactory.ENTITY_SPAWN_PACKET_ID,
        (client, handler, buf, responseSender) -> ClientPacketHandlers.handleEntitySpawnPacket(client, buf));
  }
}
