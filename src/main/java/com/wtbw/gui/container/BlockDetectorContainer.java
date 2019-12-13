package com.wtbw.gui.container;

import com.wtbw.tile.BlockDetectorTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/*
  @author: Naxanria
*/
public class BlockDetectorContainer extends BaseTileContainer<BlockDetectorTileEntity>
{
  public BlockDetectorContainer(int id, World world, BlockPos pos, PlayerInventory playerInventory)
  {
    super(ModContainers.BLOCK_DETECTOR, id, world, pos, playerInventory);
    
    trackInt(new IntReferenceHolder()
    {
      @Override
      public int get()
      {
        return tileEntity.isExactMatch() ? 1 : 0;
      }
  
      @Override
      public void set(int value)
      {
        tileEntity.setExactMatch(value == 1);
      }
    });

    trackInt(new IntReferenceHolder()
    {
      @Override
      public int get()
      {
        return tileEntity.getPower();
      }

      @Override
      public void set(int value)
      {
        tileEntity.setPower(value);
      }
    });
  }
  
  @Override
  public boolean canInteractWith(PlayerEntity playerIn)
  {
    return true;
  }
}
