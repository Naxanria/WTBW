package com.wtbw.block.redstone;

import com.wtbw.block.SixWayTileBlock;
import com.wtbw.tile.BlockDetectorTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

/*
  @author: Naxanria
*/
public class BlockDetectorBlock extends SixWayTileBlock<BlockDetectorTileEntity>
{
  public BlockDetectorBlock(Properties properties)
  {
    super(properties, ((world, state) -> new BlockDetectorTileEntity()));
  }
  
  public int getStrongPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side)
  {
    return getWeakPower(blockState, blockAccess, pos, side);
  }
  
  public int getWeakPower(BlockState state, IBlockReader world, BlockPos pos, Direction side)
  {
    Direction facing = state.get(FACING);
    if (side != facing)
    {
      return 0;
    }
    BlockDetectorTileEntity tileEntity = getTileEntity(world, pos);
    if (tileEntity != null)
    {
      return tileEntity.getPower();
    }
    
    return 0;
  }
  
  @Override
  public boolean canProvidePower(BlockState state)
  {
    return true;
  }
  
  @Override
  protected void onGuiOpen(BlockState state, World world, BlockPos pos, ServerPlayerEntity player, Hand hand, BlockRayTraceResult hit)
  {
    getTileEntity(world, pos).requestUpdate(player);
  }
}
