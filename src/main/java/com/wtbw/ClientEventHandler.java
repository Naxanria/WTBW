package com.wtbw;

import com.wtbw.config.ClientConfig;
import com.wtbw.util.Utilities;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

import java.util.List;
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
    List<ITextComponent> toolTip = event.getToolTip();
  
    if (ClientConfig.get().showBurnTime.get())
    {
      int burnTime = Utilities.getBurnTime(stack);
      if (burnTime > 0)
      {
        int index = toolTip.size() - 1;
        Utilities.insert(toolTip, index, new StringTextComponent("Burns for " + burnTime + " ticks").setStyle(darkGrey));
      }
    }
    
    if (event.getFlags().isAdvanced())
    {
      if (ClientConfig.get().showFullDurabilityOfTools.get())
      {
        int max = stack.getMaxDamage();
        if (max > 0 && !stack.isDamaged())
        {
          int index = toolTip.size() - 1;
          Utilities.insert(toolTip, index, new StringTextComponent("Durability: " + max + "/" + max).setStyle(white));
        }
      }
      
      if (ClientConfig.get().showTags.get())
      {
        if (!ClientConfig.get().showTagsRequireShift.get() || Screen.hasShiftDown())
        {
          Set<ResourceLocation> tags = stack.getItem().getTags();
          for (ResourceLocation location : tags)
          {
            toolTip.add(new StringTextComponent("#" + location.toString()).setStyle(darkGrey));
          }
        }
      }
    }
  }
  
  private static void insertAt(List<ITextComponent> toolTip, int index, ITextComponent textComponent)
  {
  
  }
}
