package com.wtbw.gui.util;

import com.mojang.blaze3d.platform.GlStateManager;
import com.wtbw.network.ButtonClickedPacket;
import com.wtbw.network.Networking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

/*
  @author: Naxanria
*/
public class GuiUtil extends AbstractGui
{
  public static void sendButton(int id, BlockPos pos, ClickType clickType)
  {
    Networking.INSTANCE.sendToServer(new ButtonClickedPacket(id, pos, clickType));
  }
  
  public static boolean inRegion(int x, int y, int rX, int rY, int rWidth, int rHeight)
  {
    return x >= rX && x < rX + rWidth
      && y >= rY && y < rY + rHeight;
  }
  
  public static void renderTexture(int x, int y, int width, int height, int u, int v, int textureWidth, int textureHeight, ResourceLocation textureLocation)
  {
    renderTexture(x, y, width, height, u, v, textureWidth, textureHeight, 0xffffffff, textureLocation);
  }
  
  public static void renderTexture(int x, int y, int width, int height, int u, int v, int textureWidth, int textureHeight, int color, ResourceLocation textureLocation)
  {
    float a = ((color >> 24) & 0xff) / 256f;
    float r = ((color >> 16) & 0xff) / 256f;
    float g = ((color >> 8) & 0xff) / 256f;
    float b = (color & 0xff) / 256f;
    Minecraft.getInstance().getTextureManager().bindTexture(textureLocation);
    GlStateManager.color4f(r, g, b, a);
    GlStateManager.enableBlend();
    GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
    GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    blit(x, y, u, v, width, height, textureWidth, textureHeight);
  }
}
