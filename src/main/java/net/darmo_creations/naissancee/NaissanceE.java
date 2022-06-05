package net.darmo_creations.naissancee;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NaissanceE implements ModInitializer {
  public static final String MODID = "naissancee";
  public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

  @Override
  public void onInitialize() {
    LOGGER.info("Hello Fabric world!");
  }
}
