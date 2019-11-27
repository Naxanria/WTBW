package com.wtbw.block.redstone;

import com.wtbw.block.BaseTileBlock;
import com.wtbw.tile.redstone.RedstoneTimerTileEntity;

/*
  @author: Naxanria
*/
public class RedstoneTimerBlock extends BaseTileBlock<RedstoneTimerTileEntity>
{
  public RedstoneTimerBlock(Properties properties)
  {
    super(properties, (world, state) -> new RedstoneTimerTileEntity());
  }
}
