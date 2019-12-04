package com.wtbw.compat.jei;

import com.wtbw.WTBW;
import com.wtbw.block.ModBlocks;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/*
  @author: Naxanria
*/
@JeiPlugin
public class JEIImpl implements IModPlugin
{
  @Override
  public ResourceLocation getPluginUid()
  {
    return new ResourceLocation(WTBW.MODID, "jei_plugin");
  }

  @Override
  public void registerRecipeCatalysts(IRecipeCatalystRegistration registration)
  {
    registration.addRecipeCatalyst(new ItemStack(ModBlocks.IRON_FURNACE), VanillaRecipeCategoryUid.FURNACE);
    registration.addRecipeCatalyst(new ItemStack(ModBlocks.GOLD_FURNACE), VanillaRecipeCategoryUid.FURNACE);
    registration.addRecipeCatalyst(new ItemStack(ModBlocks.DIAMOND_FURNACE), VanillaRecipeCategoryUid.FURNACE);
    registration.addRecipeCatalyst(new ItemStack(ModBlocks.END_FURNACE), VanillaRecipeCategoryUid.FURNACE);
  }
}
