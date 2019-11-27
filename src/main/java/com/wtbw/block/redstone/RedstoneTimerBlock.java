package com.wtbw.block.redstone;

import com.wtbw.block.BaseTileBlock;
import com.wtbw.tile.redstone.RedstoneTimerTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

/*
  @author: Naxanria
*/
public class RedstoneTimerBlock extends BaseTileBlock<RedstoneTimerTileEntity>
{
  public RedstoneTimerBlock(Properties properties)
  {
    super(properties, (world, state) -> new RedstoneTimerTileEntity());
  }
  
  public int getStrongPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side)
  {
    return 0;
  }

  public int getWeakPower(BlockState state, IBlockReader world, BlockPos pos, Direction side)
  {
    RedstoneTimerTileEntity tileEntity = getTileEntity(world, pos);
    if (tileEntity != null)
    {
      return tileEntity.getPower();
    }
    
    return 0;
  }
  
//  @Override
//  public void updateNeighbors(BlockState stateIn, IWorld worldIn, BlockPos pos, int flags)
//  {
//    super.updateNeighbors(stateIn, worldIn, pos, flags);
//  }
//
//  @Override
//  public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving)
//  {
//    super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
//  }
  
  
  @Override
  public boolean canProvidePower(BlockState state)
  {
    return true;
  }
}
