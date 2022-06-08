package net.darmo_creations.naissancee.entities.renderers;

import net.darmo_creations.naissancee.entities.LightOrbEntity;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

/**
 * Renders nothing as light orbs generate particles instead of having a model.
 */
public class LightOrbEntityRenderer extends EntityRenderer<LightOrbEntity> {
  public LightOrbEntityRenderer(EntityRendererFactory.Context ctx) {
    super(ctx);
  }

  @Override
  public Identifier getTexture(LightOrbEntity entity) {
    return null;
  }
}
