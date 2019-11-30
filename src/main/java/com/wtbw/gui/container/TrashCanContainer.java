package com.wtbw.gui.container;

import com.wtbw.block.ModBlocks;
import com.wtbw.tile.trashcan.TrashCanTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

/*
  @author: Naxanria
*/
public class TrashCanContainer extends BaseTileContainer<TrashCanTileEntity>
{
  public TrashCanContainer(int id, World world, BlockPos pos, PlayerInventory playerInventory)
  {
    super(ModContainers.TRASHCAN, id, world, pos, playerInventory);
    
    tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> addSlot(new SlotItemHandler(handler, 0, 80, 35)));
    
    layoutPlayerInventorySlots(8, 84);
  }
  
  @Override
  public boolean canInteractWith(PlayerEntity playerIn)
  {
    return canInteractWith(playerIn, ModBlocks.TRASHCAN);
  }
  
  @Override
  public ItemStack transferStackInSlot(PlayerEntity playerIn, int index)
  {
    ItemStack stack = ItemStack.EMPTY;
    Slot slot = inventorySlots.get(index);
  
    if (slot != null && slot.getHasStack())
    {
      ItemStack stack1 = slot.getStack();
      stack = stack1.copy();
    
      if (index < 1)
      {
        if (!mergeItemStack(stack1, 1, inventorySlots.size(), true))
        {
          return ItemStack.EMPTY;
        }
      }
      else if (!mergeItemStack(stack1, 0, 1, false))
      {
        return ItemStack.EMPTY;
      }
    
      if (stack1.isEmpty())
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
