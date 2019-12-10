package com.wtbw.gui.container;

import com.wtbw.tile.BlockPlacerTileEntity;
import com.wtbw.tile.util.RedstoneMode;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/*
  @author: Naxanria
*/
public class BlockPlacerContainer extends BaseTileContainer<BlockPlacerTileEntity>
{
  public BlockPlacerContainer(int id, World world, BlockPos pos, PlayerInventory playerInventory)
  {
    super(ModContainers.BLOCK_PLACER, id, world, pos, playerInventory);
  
    trackInt(new IntReferenceHolder()
    {
      @Override
      public int get()
      {
        return tileEntity.getRedstoneMode().ordinal();
      }
    
      @Override
      public void set(int value)
      {
        tileEntity.getControl().setMode(RedstoneMode.values()[value % RedstoneMode.values().length]);
      }
    });
  
    tileEntity.getInventory().ifPresent(handler ->
    {
      addSlotBox(handler, 0, 84 - 21, 17, 3, 3, 18, 18);
    });
  
    layoutPlayerInventorySlots(30 - 21, 84);
  }
  
  @Override
  public boolean canInteractWith(PlayerEntity playerIn)
  {
    return true;
  }
}
