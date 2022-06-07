package net.darmo_creations.naissancee;

import net.darmo_creations.naissancee.block_entities.ModBlockEntities;
import net.darmo_creations.naissancee.blocks.ModBlocks;
import net.fabricmc.api.ClientModInitializer;

public class ClientInitializer implements ClientModInitializer {
  @Override
  public void onInitializeClient() {
    ModBlocks.registerBlockRenderLayers();
    ModBlockEntities.registerRenderers();
  }
}
