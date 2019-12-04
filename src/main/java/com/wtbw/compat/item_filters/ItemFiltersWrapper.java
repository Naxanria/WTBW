package com.wtbw.compat.item_filters;

import dev.latvian.mods.itemfilters.api.ItemFiltersAPI;
import net.minecraft.item.ItemStack;

/*
  @author: Naxanria
*/
public class ItemFiltersWrapper
{
  public static boolean filter(ItemStack filter, ItemStack insert)
  {
    return ItemFiltersAPI.filter(filter, insert);
  }
}
