package com.wtbw.gui.container;

import com.wtbw.block.ModBlocks;
import com.wtbw.tile.VacuumChestTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/*
  @author: Naxanria
*/
public class VacuumChestContainer extends BaseTileContainer<VacuumChestTileEntity>
{
  public VacuumChestContainer(int id, World world, BlockPos pos, PlayerInventory playerInventory)
  {
    super(ModContainers.VACUUM_CHEST, id, world, pos, playerInventory);
    
    tileEntity.getInventory().ifPresent(handler -> addSlotBox(handler, 0, 62, 26, 3, 2, 18, 18));
    
    layoutPlayerInventorySlots(8, 84);
  }
  
  @Override
  public boolean canInteractWith(PlayerEntity playerIn)
  {
    return canInteractWith(playerIn, ModBlocks.VACUUM_CHEST);
  }
  
  @Override
  public ItemStack transferStackInSlot(PlayerEntity playerIn, int index)
  {
    ItemStack stack = ItemStack.EMPTY;
    Slot slot = inventorySlots.get(index);
    
    if (slot != null && slot.getHasStack())
    {
      ItemStack itemStack = slot.getStack();
      stack = itemStack.copy();
  
      if (index < 6)
      {
        // insert in player inventory
        if (!mergeItemStack(itemStack, 6, inventorySlots.size(), true))
        {
          return ItemStack.EMPTY;
        }
      }
      // insert into chest
      else if (!mergeItemStack(itemStack, 0, 6, false))
      {
        return ItemStack.EMPTY;
      }
  
  
      if (stack.isEmpty())
      {
        slot.putStack(ItemStack.EMPTY);
      }
      else
      {
        slot.onSlotChanged();
      }
    }
    
    return stack;
  }
}
