package com.wtbw.item.tools;

import com.wtbw.util.Utilities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.ArrayList;
import java.util.List;

/*
  @author: Naxanria
*/
public class HammerItem extends PickaxeItem
{
  public HammerItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder)
  {
    super(tier, attackDamageIn, attackSpeedIn, builder);
  }
  
  @Override
  public boolean onBlockDestroyed(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity entity)
  {
    if (!world.isRemote)
    {
      if (state.getBlockHardness(world, pos) == 0)
      {
        return true;
      }
      
      if (entity instanceof PlayerEntity)
      {
        PlayerEntity player = (PlayerEntity) entity;
        if (!player.isSneaking())
        {
          breakNeighbours(world, pos, (ServerPlayerEntity) player, true);
          return true;
        }
        else
        {
//          stack.attemptDamageItem(1, world.rand, (ServerPlayerEntity) player);
          stack.damageItem(1, player, playerEntity -> playerEntity.sendBreakAnimation(EquipmentSlotType.MAINHAND));
        }
      }
      
      
    }
    
    return true;
  }
  
  @Override
  public boolean canHarvestBlock(BlockState state)
  {
    if (state.getBlock() == Blocks.BEDROCK)
    {
      return false;
    }
    
    return super.canHarvestBlock(state);
  }
  
  private void breakNeighbours(World world, BlockPos pos, ServerPlayerEntity player, boolean damageItem)
  {
    BlockRayTraceResult rayTraceResult = Utilities.getLookingAt(player, 6);
    Direction facing = rayTraceResult.getFace();
    
    List<BlockPos> brokenBlocks = Utilities.getBlocks(pos, facing, 3);
    for (BlockPos blockPos : brokenBlocks)
    {
      BlockState blockState = world.getBlockState(blockPos);
      if (!canHarvestBlock(blockState))
      {
        continue;
      }
  
      if (blockState.getBlock().hasTileEntity(blockState))
      {
        continue;
      }
  
      world.destroyBlock(blockPos, false);
      
      if (!player.isCreative())
      {
        if (damageItem)
        {
          player.getHeldItemMainhand().damageItem(1, player, playerEntity -> playerEntity.sendBreakAnimation(EquipmentSlotType.MAINHAND));//attemptDamageItem(1, world.rand, player instanceof ServerPlayerEntity ? (ServerPlayerEntity) player : null);
        }
        
        Utilities.dropItems(world, Block.getDrops(blockState, (ServerWorld) world, blockPos, null, player, player.getHeldItemMainhand()), blockPos);
        blockState.spawnAdditionalDrops(world, blockPos, player.getHeldItemMainhand());
      }
    }
  }
  
  @Override
  public float getDestroySpeed(ItemStack stack, BlockState state)
  {
    return super.getDestroySpeed(stack, state) / 6f;
  }
}
