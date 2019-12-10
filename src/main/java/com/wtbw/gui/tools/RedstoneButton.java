package com.wtbw.gui.tools;

import com.mojang.blaze3d.platform.GlStateManager;
import com.wtbw.WTBW;
import com.wtbw.gui.screen.BaseContainerScreen;
import com.wtbw.tile.util.IRedstoneControlled;
import com.wtbw.tile.util.RedstoneMode;
import com.wtbw.util.Utilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.client.config.GuiButtonExt;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/*
  @author: Naxanria
*/
public class RedstoneButton<TE extends TileEntity & IRedstoneControlled> extends GuiButtonExt implements ITooltipProvider
{
  public static final ResourceLocation ICONS = new ResourceLocation(WTBW.MODID, "textures/gui/redstone_buttons.png");
  
  private final IRedstoneControlled control;
  private final TE tile;

  public RedstoneButton(int xPos, int yPos, TE tile)
  {
    super(xPos, yPos, 18, 18, "", null);
  
    this.control = tile;
    this.tile = tile;
  }
  
  public int getTextureYOffset()
  {
    return getTextureYOffset(control.getRedstoneMode());
  }
  
  @Override
  public boolean isHover(int mouseX, int mouseY)
  {
    return isHovered();
  }
  
  @Override
  public List<String> getTooltip()
  {
    List<String> tooltip = new ArrayList<>();
    tooltip.add(control.getRedstoneMode().toString());
    return tooltip;
  }
  
  @Override
  public void onPress()
  {
    BaseContainerScreen.sendButton(control.getControl().getButtonId(getNextMode()), tile.getPos(), ClickType.LEFT);
  }
  
  @Override
  public void renderButton(int mouseX, int mouseY, float partial)
  {
    Minecraft minecraft = Minecraft.getInstance();
    minecraft.getTextureManager().bindTexture(WIDGETS_LOCATION);
    GlStateManager.color4f(1.0F, 1.0F, 1.0F, this.alpha);
    int yOff = this.getYImage(this.isHovered());
    GlStateManager.enableBlend();
    GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
    GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    this.blit(this.x, this.y, 0, 46 + yOff * 20, this.width / 2, this.height);
    this.blit(this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + yOff * 20, this.width / 2, this.height);

    minecraft.getTextureManager().bindTexture(ICONS);
    yOff = getTextureYOffset();

    blit(x + 1, y + 2, 0, yOff, 16, 16, 64, 64);



    this.renderBg(minecraft, mouseX, mouseY);
  }
  
  private RedstoneMode getNextMode()
  {
    RedstoneMode mode = control.getRedstoneMode();
    RedstoneMode[] redstoneModes = control.availableModes();
    int index = (Utilities.getIndex(redstoneModes, mode) + 1) % redstoneModes.length;
    return redstoneModes[index];
  }
  
  public static int getTextureYOffset(RedstoneMode mode)
  {
    switch (mode)
    {
      default:
      case IGNORE:
        return 48;
      case ON:
        return 0;
      case OFF:
        return 16;
      case PULSE:
        return 32;
    }
  }
}
