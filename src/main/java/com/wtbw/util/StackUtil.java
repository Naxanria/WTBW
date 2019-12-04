package com.wtbw.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class StackUtil
{
  public static boolean contains(PlayerInventory inventory, boolean inHotBar, String search)
  {

    if (inHotBar)
    {
      return contains(inventory.offHandInventory, search) || contains(inventory.mainInventory, search, 9);
    }

    return contains(inventory.offHandInventory, search) || contains(inventory.mainInventory, search);
  }

  public static boolean contains(List<ItemStack> stacks, String search, int end)
  {
    return contains(stacks, search, 0, end);
  }

  public static boolean contains(List<ItemStack> stacks, String search, int start, int end)
  {
    for (int i = start; i < end; i++)
    {
      ItemStack stack = stacks.get(i);
      if (search.equalsIgnoreCase(getIdString(stack)))
      {
        return true;
      }
    }

    return false;
  }

  public static boolean contains(List<ItemStack> stacks, String search)
  {
    for (ItemStack stack :
      stacks)
    {
      String id = getIdString(stack);
      if (search.equalsIgnoreCase(id))
      {
        return true;
      }
    }

    return false;
  }

  public static int getIndexOf(IInventory inventory, Item item)
  {
    for (int i = 0; i < inventory.getSizeInventory(); i++)
    {
      ItemStack stackInSlot = inventory.getStackInSlot(i);
      if (stackInSlot.getItem() == item)
      {
        return i;
      }
    }

    return -1;
  }

  public static String getIdString(ItemStack stack)
  {
    if (stack.isEmpty())
    {
      return null;
    }

    ResourceLocation id = Registry.ITEM.getKey(stack.getItem());
    String fullID = id.toString();

    if (stack.isDamaged())
    {
      fullID = fullID + "@" + stack.getDamage();
    }


    return fullID;
  }
  
  public static boolean canInsert(LazyOptional<ItemStackHandler> handler, ItemStack stack, boolean requireFullFit)
  {
    AtomicBoolean insert = new AtomicBoolean(false);
    
    handler.ifPresent(handler1 -> insert.set(canInsert(handler1, stack, requireFullFit)));
    
    return insert.get();
  }
  
  public static boolean canInsert(ItemStackHandler handler, ItemStack stack, boolean requireFullFit)
  {
    List<ItemStack> stacks = new ArrayList<>();
    for (int i = 0; i < handler.getSlots(); i++)
    {
      stacks.add(handler.getStackInSlot(i));
    }
    
    return canInsert(stacks, stack, requireFullFit);
  }
  
  public static boolean canInsert(PlayerEntity player, ItemStack stack, boolean requireFullFit)
  {
    PlayerInventory inventory = player.inventory;
    boolean main = canInsert(inventory.mainInventory, stack, requireFullFit);
    if (!main)
    {
      ItemStack offhand = inventory.offHandInventory.get(0);

      if (offhand.isEmpty())
      {
        return false;
      }

      if (offhand.getItem() == stack.getItem())
      {
        if (requireFullFit)
        {
          if (stack.getCount() + offhand.getCount() < stack.getMaxStackSize())
          {
            return true;
          }
        }
        return offhand.getCount() < stack.getMaxStackSize();
      }
    }

    return true;
  }

  public static boolean canInsert(IInventory inventory, ItemStack stack, boolean requireFullFit)
  {
    List<ItemStack> stacks = new ArrayList<>();
    for (int slot = 0; slot < inventory.getSizeInventory(); slot++)
    {
      stacks.add(inventory.getStackInSlot(slot));
    }

    return canInsert(stacks, stack, requireFullFit);
  }

  public static boolean canInsert(List<ItemStack> list, ItemStack stack, boolean requireFullFit)
  {
    int count = stack.getCount();

    for (int i = 0; i < list.size(); i++)
    {
      ItemStack toMerge = list.get(i);
      if (toMerge.isEmpty())
      {
        return true;
      }

      if (doItemsStack(toMerge, stack))
      {
        if (!requireFullFit)
        {
          return true;
        }

        count -= toMerge.getMaxStackSize() - toMerge.getCount();
        if (count <= 0)
        {
          return true;
        }
      }
    }

    return false;
  }

  public static boolean doItemsStack(ItemStack onto, ItemStack stack)
  {
    return !isFull(onto) && areItemsAndTagsEqual(onto, stack);
  }

  public static boolean areItemsAndTagsEqual(ItemStack stack1, ItemStack stack2)
  {
    return Container.areItemsAndTagsEqual(stack1, stack2);
  }

  public static boolean isFull(ItemStack stack)
  {
    return stack.getCount() == stack.getMaxStackSize();
  }
}
