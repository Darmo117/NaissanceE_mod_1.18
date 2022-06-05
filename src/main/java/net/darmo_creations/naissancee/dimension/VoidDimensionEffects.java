package net.darmo_creations.naissancee.dimension;

import net.minecraft.client.render.DimensionEffects;
import net.minecraft.util.math.Vec3d;

public class VoidDimensionEffects extends DimensionEffects {
  public VoidDimensionEffects() {
    super(Float.NaN, false, DimensionEffects.SkyType.NONE, false, false);
  }

  @Override
  public Vec3d adjustFogColor(Vec3d color, float sunHeight) {
    return color;
  }

  @Override
  public boolean useThickFog(int camX, int camY) {
    return false;
  }
}
