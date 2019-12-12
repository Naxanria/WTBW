package com.wtbw.gui.screen;

import com.mojang.blaze3d.platform.GlStateManager;
import com.wtbw.gui.util.ClickType;
import com.wtbw.gui.util.ITooltipProvider;
import com.wtbw.network.ButtonClickedPacket;
import com.wtbw.network.Networking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.List;

/*
  @author: Naxanria
*/
public abstract class BaseContainerScreen<C extends Container> extends ContainerScreen<C>
{
  private List<ITooltipProvider> tooltipProviders = new ArrayList<>();

  public BaseContainerScreen(C container, PlayerInventory inventory, ITextComponent title)
  {
    super(container, inventory, title);
  }

  protected void addTooltipProvider(ITooltipProvider provider)
  {
    tooltipProviders.add(provider);
  }

  @Override
  public void render(int mouseX, int mouseY, float partialTicks)
  {
    renderBackground();
    super.render(mouseX, mouseY, partialTicks);
    renderTooltip(mouseX, mouseY);
  }

  protected void renderTooltip(int mouseX, int mouseY)
  {
    if (this.minecraft.player.inventory.getItemStack().isEmpty())
    {
      if (this.hoveredSlot != null && this.hoveredSlot.getHasStack())
      {
        this.renderTooltip(this.hoveredSlot.getStack(), mouseX, mouseY);
      }
      else
      {
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;

        for (ITooltipProvider provider : tooltipProviders)
        {
          if (provider.isHover(mouseX - i, mouseY - j))
          {
            renderTooltip(provider.getTooltip(), mouseX, mouseY);
            break;
          }
        }
      }
    }
  }
  
  @Override
  protected void init()
  {
    super.init();
    
    tooltipProviders.clear();
  }
  
  @Override
  protected <T extends Widget> T addButton(T widget)
  {
    if (widget instanceof ITooltipProvider)
    {
      tooltipProviders.add((ITooltipProvider) widget);
    }
    return super.addButton(widget);
  }
}
