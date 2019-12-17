package com.wtbw.client.rendering;

import com.wtbw.item.tools.ExcavatorItem;
import com.wtbw.item.tools.GreatAxeItem;
import com.wtbw.item.tools.HammerItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;

/*
  @author: Naxanria
*/
public class RenderManager
{
  
  public static void renderOutline(final DrawBlockHighlightEvent event)
  {
    Minecraft instance = Minecraft.getInstance();
    ClientPlayerEntity player = instance.player;
    ItemStack heldStack = player.getHeldItem(Hand.MAIN_HAND);
    // todo: fix outline rendering
    Item item = heldStack.getItem();
    if (item instanceof HammerItem || item instanceof ExcavatorItem || item instanceof GreatAxeItem)
    {
//      if (Renderers.SELECTION_RENDERER.render(heldStack, player, event.getTarget(), event.getContext(), event.getInfo()))
//      {
//        event.setCanceled(true);
//      }
    }
  }
}
