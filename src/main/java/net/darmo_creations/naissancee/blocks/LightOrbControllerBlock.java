package net.darmo_creations.naissancee.blocks;

import net.darmo_creations.naissancee.Utils;
import net.darmo_creations.naissancee.block_entities.LightOrbControllerBlockEntity;
import net.darmo_creations.naissancee.block_entities.ModBlockEntities;
import net.darmo_creations.naissancee.gui.LightOrbControllerScreen;
import net.darmo_creations.naissancee.items.LightOrbTweakerItem;
import net.darmo_creations.naissancee.items.ModItems;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;

/**
 * This block lets players configure light orbs through the use of a special tool and a configuration GUI.
 *
 * @see LightOrbTweakerItem
 * @see LightOrbControllerBlockEntity
 */
public class LightOrbControllerBlock extends BlockWithEntity implements OperatorBlock, NaissanceEBlock {
  public LightOrbControllerBlock() {
    // Same settings as command block
    super(NaissanceEBlock.getSettings(FabricBlockSettings.of(Material.METAL, MapColor.WHITE).sounds(BlockSoundGroup.METAL)));
  }

  @SuppressWarnings("deprecation")
  @Override
  public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
    super.onBlockAdded(state, world, pos, oldState, notify);
    Utils.getBlockEntity(LightOrbControllerBlockEntity.class, world, pos).ifPresent(LightOrbControllerBlockEntity::init);
  }

  @Override
  public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
    Utils.getBlockEntity(LightOrbControllerBlockEntity.class, world, pos).ifPresent(LightOrbControllerBlockEntity::onRemoved);
    super.onBreak(world, pos, state, player);
  }

  @SuppressWarnings("deprecation")
  @Override
  public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
    Optional<LightOrbControllerBlockEntity> be = Utils.getBlockEntity(LightOrbControllerBlockEntity.class, world, pos);
    if (be.isPresent() && player.isCreativeLevelTwoOp() && player.getStackInHand(hand).getItem() == ModItems.LIGHT_ORB_TWEAKER) {
      if (world.isClient()) {
        MinecraftClient.getInstance().setScreen(new LightOrbControllerScreen(be.get()));
      }
      return ActionResult.SUCCESS;
    } else {
      return ActionResult.FAIL;
    }
  }

  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
    return type == ModBlockEntities.LIGHT_ORB_CONTROLLER
        ? (world_, pos, state_, be) -> ((LightOrbControllerBlockEntity) be).tick()
        : null;
  }

  @Override
  public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
    return new LightOrbControllerBlockEntity(pos, state);
  }

  @Override
  public BlockRenderType getRenderType(BlockState state) {
    return BlockRenderType.MODEL;
  }
}
