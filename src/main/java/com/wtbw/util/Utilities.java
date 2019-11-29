package com.wtbw.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/*
  @author: Naxanria
*/
@SuppressWarnings("WeakerAccess")
public class Utilities
{
  public static int getBurnTime(ItemStack itemStack)
  {
    // copied form AbstractFurnaceTileEntity
    if (itemStack.isEmpty())
    {
      return 0;
    }
    else
    {
      Item item = itemStack.getItem();
      int ret = itemStack.getBurnTime();
      return net.minecraftforge.event.ForgeEventFactory.getItemBurnTime(itemStack, ret == -1 ? AbstractFurnaceTileEntity.getBurnTimes().getOrDefault(item, 0) : ret);
    }
  }
  
  public static List<IRecipe<?>> getRecipes(RecipeManager manager, IRecipeType<?> type)
  {
    Collection<IRecipe<?>> recipes = manager.getRecipes();
    return recipes.stream().filter(iRecipe -> iRecipe.getType() == type).collect(Collectors.toList());
  }
  
  public static Direction getFacingFromVector(Vec3d vec)
  {
    return Direction.getFacingFromVector(vec.x, vec.y, vec.z);
  }
  
  public static List<ItemStack> getHotbar(PlayerEntity player)
  {
    return getHotbar(player, -1);
  }
  
  public static List<ItemStack> getHotbar(PlayerEntity player, int exclude)
  {
    List<ItemStack> hotbar = new ArrayList<>();
    for (int i = 0; i < 9; i++)
    {
      if (i == exclude)
      {
        continue;
      }
      
      hotbar.add(player.inventory.mainInventory.get(i));
    }
    return hotbar;
  }
  
  public static <T> List<T> insert(List<T> list, int index, T item)
  {
    if (index >= list.size())
    {
      list.add(item);
      
      return list;
    }
    
    list.add(list.get(list.size() - 1));
    
    for (int i = list.size() - 1; i > index; i--)
    {
      swap(list, i, i - 1);
    }
    
    list.set(index, item);
  
    return list;
  }
  
  public static <T> List<T> swap(List<T> list, int indexA, int indexB)
  {
    T temp = list.get(indexA);
    list.set(indexA, list.get(indexB));
    list.set(indexB, temp);
    
    return list;
  }
  
  
}
