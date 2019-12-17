package com.wtbw.gui.util;

import com.mojang.blaze3d.platform.GlStateManager;
import com.wtbw.WTBW;
import com.wtbw.tile.util.IRedstoneControlled;
import com.wtbw.tile.util.RedstoneMode;
import com.wtbw.util.Utilities;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiButtonExt;

import java.util.ArrayList;
import java.util.List;

/*
  @author: Naxanria
*/
public class RedstoneButton<TE extends TileEntity & IRedstoneControlled> extends GuiButtonExt implements ITooltipProvider
{
  public static final ResourceLocation ICONS = new ResourceLocation(WTBW.MODID, "textures/gui/redstone_buttons.png");
  private final static SpriteMap SPRITE_MAP = new SpriteMap(64, ICONS);
  private final static Sprite SPRITE_IGNORE = SPRITE_MAP.getSprite(0, getTextureYOffset(RedstoneMode.IGNORE), 16);
  private final static Sprite SPRITE_ON = SPRITE_MAP.getSprite(0, getTextureYOffset(RedstoneMode.ON), 16);
  private final static Sprite SPRITE_OFF = SPRITE_MAP.getSprite(0, getTextureYOffset(RedstoneMode.OFF), 16);
  private final static Sprite SPRITE_PULSE = SPRITE_MAP.getSprite(0, getTextureYOffset(RedstoneMode.PULSE), 16);
  
  
  private final IRedstoneControlled control;
  private final TE tile;

  public RedstoneButton(int xPos, int yPos, TE tile)
  {
    super(xPos, yPos, 18, 18, "", null);
  
    this.control = tile;
    this.tile = tile;
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
    GuiUtil.sendButton(control.getControl().getButtonId(getNextMode()), tile.getPos(), ClickType.LEFT);
  }
  
  @Override
  public void renderButton(int mouseX, int mouseY, float partial)
  {
//    Minecraft minecraft = Minecraft.getInstance();
//    minecraft.getTextureManager().bindTexture(WIDGETS_LOCATION);
//    GlStateManager.color4f(1.0F, 1.0F, 1.0F, this.alpha);
    int yOff = this.getYImage(this.isHovered());
//    GlStateManager.enableBlend();
//    GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
//    GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
//    this.blit(this.x, this.y, 0, 46 + yOff * 20, this.width / 2, this.height);
//    this.blit(this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + yOff * 20, this.width / 2, this.height);
//
    GuiUtil.renderTexture(x, y, width / 2, height, 0, 46 + yOff * 20, 256, 256, WIDGETS_LOCATION);
    GuiUtil.renderTexture(x + width / 2, y, width / 2, width / 2, 200 - width / 2, 46 + yOff * 20, 256, 256, WIDGETS_LOCATION);
    
    Sprite sprite;
    switch (control.getRedstoneMode())
    {
      default:
      case IGNORE:
        sprite = SPRITE_IGNORE;
        break;
      case ON:
        sprite = SPRITE_ON;
        break;
      case OFF:
        sprite = SPRITE_OFF;
        break;
      case PULSE:
        sprite = SPRITE_PULSE;
        break;
    }
    
    sprite.render(x + 1, y + 2);

    this.renderBg(Minecraft.getInstance(), mouseX, mouseY);
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
