package net.darmo_creations.naissancee.block_entities.renderers;

import net.darmo_creations.naissancee.block_entities.InvisibleLightBlockEntity;
import net.darmo_creations.naissancee.blocks.InvisibleLightBlock;
import net.darmo_creations.naissancee.blocks.ModBlocks;
import net.darmo_creations.naissancee.items.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3f;

/**
 * Renderer for the block entity associated to invisible lights.
 * <p>
 * Renders a rotating item to make the block visible and the block’s light level value.
 *
 * @see InvisibleLightBlockEntity
 * @see InvisibleLightBlock
 * @see ModBlocks#INVISIBLE_LIGHT
 */
public class InvisibleLightSourceBlockEntityRenderer implements BlockEntityRenderer<InvisibleLightBlockEntity> {
  private static final ItemStack STACK = new ItemStack(Item.BLOCK_ITEMS.get(ModBlocks.INVISIBLE_LIGHT));

  private static final float TEXT_SCALE = 0.01f;
  private static final float INVERSE_TEXT_SCALE = 1 / TEXT_SCALE;
  private static final int TEXT_COLOR = 0xffffff; // White

  public InvisibleLightSourceBlockEntityRenderer(BlockEntityRendererFactory.Context ignored) {
    // Required by registry
  }

  @Override
  public void render(InvisibleLightBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
    //noinspection ConstantConditions
    if (this.shouldDraw(MinecraftClient.getInstance().player)) {
      this.drawRotatingItem(blockEntity, tickDelta, matrices, vertexConsumers);
      this.drawLightLevelText(blockEntity, matrices);
    }
  }

  private boolean shouldDraw(PlayerEntity player) {
    return player.getMainHandStack().getItem() == ModItems.INVISIBLE_LIGHT_TWEAKER
        || player.getOffHandStack().getItem() == ModItems.INVISIBLE_LIGHT_TWEAKER
        || player.getMainHandStack().getItem() == Item.BLOCK_ITEMS.get(ModBlocks.INVISIBLE_LIGHT)
        || player.getOffHandStack().getItem() == Item.BLOCK_ITEMS.get(ModBlocks.INVISIBLE_LIGHT);
  }

  private void drawRotatingItem(InvisibleLightBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers) {
    //noinspection ConstantConditions
    int lightAbove = WorldRenderer.getLightmapCoordinates(blockEntity.getWorld(), blockEntity.getPos().up());
    matrices.push();
    matrices.translate(0.5, 0.4, 0.5);
    matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((blockEntity.getWorld().getTime() + tickDelta) * 2));
    MinecraftClient.getInstance().getItemRenderer()
        .renderItem(STACK, ModelTransformation.Mode.GROUND, lightAbove, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, 0);
    matrices.pop();
  }

  private void drawLightLevelText(InvisibleLightBlockEntity blockEntity, MatrixStack matrices) {
    //noinspection ConstantConditions
    BlockState blockState = blockEntity.getWorld().getBlockState(blockEntity.getPos());
    if (blockState.isOf(ModBlocks.INVISIBLE_LIGHT)) { // Block may have been removed
      final String lightLevelText = "" + blockState.get(InvisibleLightBlock.LIGHT_LEVEL);

      matrices.push();
      matrices.translate(0.75, 0.75, 0.25);
      // Text is draw upside-down in the XY plane, rotate it 180°
      matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(180));
      // Text is too big by default, shrink it down
      matrices.scale(TEXT_SCALE, TEXT_SCALE, TEXT_SCALE);

      // Draw text on all 4 sides
      for (int i = 0; i < 4; i++) {
        matrices.push();
        // Rotate text for the current side
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(i * 90));
        // Adjust text position
        switch (i) {
          case 1 -> matrices.translate(INVERSE_TEXT_SCALE * -0.5, 0, 0);
          case 2 -> matrices.translate(INVERSE_TEXT_SCALE * -0.5, 0, INVERSE_TEXT_SCALE * -0.5);
          case 3 -> matrices.translate(0, 0, INVERSE_TEXT_SCALE * -0.5);
        }
        MinecraftClient.getInstance().textRenderer.draw(matrices, lightLevelText, 0, 0, TEXT_COLOR);
        matrices.pop();
      }

      matrices.pop();
    }
  }
}
