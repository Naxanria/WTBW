package com.wtbw.gui.container;

import com.wtbw.block.ModBlocks;
import com.wtbw.tile.furnace.TileEntityBaseFurnace;
import com.wtbw.util.Utilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

/*
  @author: Naxanria
*/
public class IronFurnaceContainer extends BaseTileContainer<TileEntityBaseFurnace>
{
  public IronFurnaceContainer( int id, World world, BlockPos pos, PlayerInventory playerInventory)
  {
    super(ModContainers.IRON_FURNACE, id, world, pos, playerInventory);
    
    tileEntity.getInputHandler().ifPresent(
      handler -> addSlot
      (
        new SlotItemHandler(handler, 0, 56, 17)
        {
          @Override
          public void putStack(@Nonnull ItemStack stack)
          {
            super.putStack(stack);
          }
  
          @Override
          public boolean canTakeStack(PlayerEntity playerIn)
          {
            return true;
          }
  
          @Nonnull
          @Override
          public ItemStack decrStackSize(int amount)
          {
            int slotNumber = getSlotIndex();
            ItemStack stackInSlot = handler.getStackInSlot(slotNumber);
            if (amount > stackInSlot.getCount())
            {
              handler.setStackInSlot(slotNumber, ItemStack.EMPTY);
              return stackInSlot;
            }
  
            ItemStack toReturn = stackInSlot.copy();
            toReturn.setCount(amount);
            stackInSlot.setCount(stackInSlot.getCount() - amount);
            handler.setStackInSlot(slotNumber, stackInSlot.getCount() == 0 ? ItemStack.EMPTY : stackInSlot);
  
            return toReturn;
          }
        }
    ));
    
    tileEntity.getFuelHandler().ifPresent(
      handler -> addSlot
      (
        new SlotItemHandler(handler, 0, 56, 53)
        {
          @Override
          public void putStack(@Nonnull ItemStack stack)
          {
            super.putStack(stack);
          }
  
          @Override
          public boolean canTakeStack(PlayerEntity playerIn)
          {
            return true;
          }
  
          @Nonnull
          @Override
          public ItemStack decrStackSize(int amount)
          {
            int slotNumber = getSlotIndex();
            ItemStack stackInSlot = handler.getStackInSlot(slotNumber);
            if (amount > stackInSlot.getCount())
            {
              handler.setStackInSlot(slotNumber, ItemStack.EMPTY);
              return stackInSlot;
            }
  
            ItemStack toReturn = stackInSlot.copy();
            toReturn.setCount(amount);
            stackInSlot.setCount(stackInSlot.getCount() - amount);
            handler.setStackInSlot(slotNumber, stackInSlot.getCount() == 0 ? ItemStack.EMPTY : stackInSlot);
  
            return toReturn;
          }
        }
    ));
    
    tileEntity.getOutputHandler().ifPresent(handler -> addSlot(new SlotItemHandler(handler, 0, 116, 35)));
  
    trackInt(new IntReferenceHolder()
    {
      @Override
      public int get()
      {
        return tileEntity.getBurnTime();
      }
    
      @Override
      public void set(int value)
      {
        tileEntity.setBurnTime(value);
      }
    });
    
    trackInt(new IntReferenceHolder()
    {
      @Override
      public int get()
      {
        return tileEntity.getBurnTimeTotal();
      }
    
      @Override
      public void set(int value)
      {
        tileEntity.setBurnTimeTotal(value);
      }
    });
    
    trackInt(new IntReferenceHolder()
    {
      @Override
      public int get()
      {
        return tileEntity.getCookTime();
      }
    
      @Override
      public void set(int value)
      {
        tileEntity.setCookTime(value);
      }
    });
    
    trackInt(new IntReferenceHolder()
    {
      @Override
      public int get()
      {
        return tileEntity.getCookTimeTotal();
      }
    
      @Override
      public void set(int value)
      {
        tileEntity.setCookTimeTotal(value);
      }
    });
  
    layoutPlayerInventorySlots(8, 84);
  }
  
  @Override
  public boolean canInteractWith(PlayerEntity playerIn)
  {
    return canInteractWith(playerIn, ModBlocks.IRON_FURNACE);
  }
  
  public ItemStack transferStackInSlot(PlayerEntity playerIn, int index)
  {
    ItemStack itemstack = ItemStack.EMPTY;
    Slot slot = this.inventorySlots.get(index);
    if (slot != null && slot.getHasStack())
    {
      ItemStack slotStack = slot.getStack();
      itemstack = slotStack.copy();
      if (index == 2)
      {
        if (!this.mergeItemStack(slotStack, 3, 39, true))
        {
          return ItemStack.EMPTY;
        }
  
        slot.onSlotChange(slotStack, itemstack);
      }
      else if (index != 1 && index != 0)
      {
        if (this.hasRecipe(slotStack))
        {
          if (!this.mergeItemStack(slotStack, 0, 1, false))
          {
            return ItemStack.EMPTY;
          }
        }
        else if (this.isFuel(slotStack))
        {
          if (!this.mergeItemStack(slotStack, 1, 2, false))
          {
            return ItemStack.EMPTY;
          }
        }
        else if (index >= 3 && index < 30)
        {
          if (!this.mergeItemStack(slotStack, 30, 39, false))
          {
            return ItemStack.EMPTY;
          }
        }
        else if (index >= 30 && index < 39 && !this.mergeItemStack(slotStack, 3, 30, false))
        {
          return ItemStack.EMPTY;
        }
      }
      else if (!this.mergeItemStack(slotStack, 3, 39, false))
      {
        return ItemStack.EMPTY;
      }
  
      if (slotStack.isEmpty())
      {
        slot.putStack(ItemStack.EMPTY);
      }
      else
      {
        slot.onSlotChanged();
      }
  
      if (slotStack.getCount() == itemstack.getCount())
      {
        return ItemStack.EMPTY;
      }
  
      slot.onTake(playerIn, slotStack);
    }
    
    return itemstack;
  }
  
  private boolean isFuel(ItemStack stack)
  {
    return Utilities.getBurnTime(stack) > 0;
  }
  
  private boolean hasRecipe(ItemStack stack)
  {
    return world.getRecipeManager().getRecipe(tileEntity.recipeType, new Inventory(stack), this.world).isPresent();
  }
}
