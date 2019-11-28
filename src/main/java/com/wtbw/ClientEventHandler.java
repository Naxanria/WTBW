package com.wtbw;

import com.wtbw.config.ClientConfig;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

import java.util.Set;

/*
  @author: Naxanria
*/
public class ClientEventHandler
{
  private static Style white = new Style().setColor(TextFormatting.WHITE);
  private static Style darkGrey = new Style().setColor(TextFormatting.DARK_GRAY);
  public static void onTooltip(final ItemTooltipEvent event)
  {
    ItemStack stack = event.getItemStack();
    if (event.getFlags().isAdvanced())
    {
      if (ClientConfig.get().showFullDurabilityOfTools.get())
      {
        int max = stack.getMaxDamage();
        if (max > 0 && !stack.isDamaged())
        {
          event.getToolTip().add(new StringTextComponent("Durability: " + max + "/" + max).setStyle(white));
        }
      }
      
      if (ClientConfig.get().showTags.get())
      {
        Set<ResourceLocation> tags = stack.getItem().getTags();
        for (ResourceLocation location : tags)
        {
          event.getToolTip().add(new StringTextComponent("#" + location.toString()).setStyle(darkGrey));
        }
      }
    }
  }
}
