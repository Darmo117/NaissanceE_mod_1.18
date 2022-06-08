package net.darmo_creations.naissancee.block_entities.renderers;

import net.darmo_creations.naissancee.block_entities.LightOrbControllerBlockEntity;
import net.darmo_creations.naissancee.block_entities.PathCheckpoint;
import net.darmo_creations.naissancee.blocks.LightOrbControllerBlock;
import net.darmo_creations.naissancee.blocks.ModBlocks;
import net.darmo_creations.naissancee.items.LightOrbTweakerItem;
import net.darmo_creations.naissancee.items.ModItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;

import java.util.List;

/**
 * Renderer for the tile entity associated to light orb controllers.
 * <p>
 * Renders the checkpoints as cubes and the path as straight lines between checkpoints.
 *
 * @see LightOrbControllerBlockEntity
 * @see LightOrbControllerBlock
 * @see ModBlocks#LIGHT_ORB_CONTROLLER
 */
public class LightOrbControllerBlockEntityRenderer implements BlockEntityRenderer<LightOrbControllerBlockEntity> {
  /**
   * Constructor required for registration.
   */
  public LightOrbControllerBlockEntityRenderer(BlockEntityRendererFactory.Context ignored) {
  }

  @Override
  public void render(LightOrbControllerBlockEntity be, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
    PlayerEntity player = MinecraftClient.getInstance().player;
    //noinspection ConstantConditions
    ItemStack stack = player.getMainHandStack();

    if ((player.isCreativeLevelTwoOp() || player.isSpectator())
        && stack.getItem() == ModItems.LIGHT_ORB_TWEAKER
        && LightOrbTweakerItem.getControllerTileEntity(stack, be.getWorld()).map(t -> t.getPos().equals(be.getPos())).orElse(false)) {
      this.renderControllerBox(matrices, vertexConsumers);

      List<PathCheckpoint> checkpoints = be.getCheckpoints();
      for (int i = 0, size = checkpoints.size(); i < size; i++) {
        PathCheckpoint checkpoint = checkpoints.get(i);
        PathCheckpoint nextCheckpoint = null;
        if (i == size - 1) {
          if (be.loops()) {
            nextCheckpoint = checkpoints.get(0);
          }
        } else {
          nextCheckpoint = checkpoints.get(i + 1);
        }
        this.renderCheckpoint(be, checkpoint, i == 0, i == size - 1, matrices, vertexConsumers);
        if (nextCheckpoint != null) {
          this.renderLine(be, checkpoint, nextCheckpoint, matrices, vertexConsumers);
        }
      }
    }
  }

  private void renderControllerBox(MatrixStack matrices, VertexConsumerProvider vertexConsumers) {
    final double offset = 0.01;
    double boxX1 = -offset;
    double boxY1 = -offset;
    double boxZ1 = -offset;
    double boxX2 = 1 + offset;
    double boxY2 = 1 + offset;
    double boxZ2 = 1 + offset;
    VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getLines());
    WorldRenderer.drawBox(matrices, vertexConsumer, boxX1, boxY1, boxZ1, boxX2, boxY2, boxZ2, 1, 1, 0, 1);
  }

  private void renderCheckpoint(LightOrbControllerBlockEntity be, PathCheckpoint checkpoint,
                                boolean isFirst, boolean isLast, MatrixStack matrices, VertexConsumerProvider vertexConsumers) {
    BlockPos bePos = be.getPos();
    BlockPos checkpointPos = checkpoint.getPos();

    final double boxSize = 0.25;
    double start = 0.5 - boxSize;
    double end = 0.5 + boxSize;

    double boxX1 = checkpointPos.getX() - bePos.getX() + start;
    double boxY1 = checkpointPos.getY() - bePos.getY() + start;
    double boxZ1 = checkpointPos.getZ() - bePos.getZ() + start;
    double boxX2 = checkpointPos.getX() - bePos.getX() + end;
    double boxY2 = checkpointPos.getY() - bePos.getY() + end;
    double boxZ2 = checkpointPos.getZ() - bePos.getZ() + end;

    float lineX = checkpointPos.getX() - bePos.getX() + 0.5f;
    float lineY1 = checkpointPos.getY() - bePos.getY();
    float lineZ = checkpointPos.getZ() - bePos.getZ() + 0.5f;
    float lineY2 = checkpointPos.getY() - bePos.getY() + 1;

    int boxR = 0, boxG = 0, boxB = 0;
    if (checkpoint.isStop()) {
      boxR = 1;
    } else {
      boxG = 1;
    }
    int lineR = 0, lineG = 0, lineB = 0;
    if (isFirst) {
      lineB = 255;
    } else if (isLast) {
      lineG = 255;
      lineB = 255;
    }

    VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getLines());
    if (isFirst || isLast) {
      Matrix4f matrix4f = matrices.peek().getPositionMatrix();
      Matrix3f matrix3f = matrices.peek().getNormalMatrix();
      vertexConsumer.vertex(matrix4f, lineX, lineY1, lineZ).color(lineR, lineG, lineB, 255).normal(matrix3f, 0, 1, 0).next();
      vertexConsumer.vertex(matrix4f, lineX, lineY2, lineZ).color(lineR, lineG, lineB, 255).normal(matrix3f, 0, 1, 0).next();
    }
    WorldRenderer.drawBox(matrices, vertexConsumer, boxX1, boxY1, boxZ1, boxX2, boxY2, boxZ2, boxR, boxG, boxB, 1);
  }

  private void renderLine(LightOrbControllerBlockEntity be, PathCheckpoint checkpoint1, PathCheckpoint checkpoint2,
                          MatrixStack matrices, VertexConsumerProvider vertexConsumers) {
    BlockPos tePos = be.getPos();
    BlockPos pos1 = checkpoint1.getPos();
    BlockPos pos2 = checkpoint2.getPos();
    final float offset = 0.5f;
    float x1 = pos1.getX() - tePos.getX() + offset;
    float y1 = pos1.getY() - tePos.getY() + offset;
    float z1 = pos1.getZ() - tePos.getZ() + offset;
    float x2 = pos2.getX() - tePos.getX() + offset;
    float y2 = pos2.getY() - tePos.getY() + offset;
    float z2 = pos2.getZ() - tePos.getZ() + offset;
    VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getLines());
    Matrix4f matrix4f = matrices.peek().getPositionMatrix();
    Matrix3f matrix3f = matrices.peek().getNormalMatrix();
    // Draw 3 lines with different normals to give the resulting line the same width from all camera angles
    vertexConsumer.vertex(matrix4f, x1, y1, z1).color(255, 255, 255, 255).normal(matrix3f, 0, 1, 0).next();
    vertexConsumer.vertex(matrix4f, x2, y2, z2).color(255, 255, 255, 255).normal(matrix3f, 0, 1, 0).next();
    vertexConsumer.vertex(matrix4f, x1, y1, z1).color(255, 255, 255, 255).normal(matrix3f, 1, 0, 0).next();
    vertexConsumer.vertex(matrix4f, x2, y2, z2).color(255, 255, 255, 255).normal(matrix3f, 1, 0, 0).next();
    vertexConsumer.vertex(matrix4f, x1, y1, z1).color(255, 255, 255, 255).normal(matrix3f, 0, 0, 1).next();
    vertexConsumer.vertex(matrix4f, x2, y2, z2).color(255, 255, 255, 255).normal(matrix3f, 0, 0, 1).next();
  }

  @Override
  public boolean rendersOutsideBoundingBox(LightOrbControllerBlockEntity blockEntity) {
    return true;
  }

  @Override
  public int getRenderDistance() {
    return 1000;
  }
}
