package com.wtbw.util;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import java.util.List;

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
}
