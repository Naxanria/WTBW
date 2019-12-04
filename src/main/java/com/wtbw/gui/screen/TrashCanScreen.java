package com.wtbw.gui.screen;

import com.mojang.blaze3d.platform.GlStateManager;
import com.wtbw.WTBW;
import com.wtbw.gui.container.TrashCanContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

/*
  @author: Naxanria
*/
public class TrashCanScreen extends BaseContainerScreen<TrashCanContainer>
{
  public static final ResourceLocation GUI = new ResourceLocation(WTBW.MODID, "textures/gui/trashcan.png");
  
  public TrashCanScreen(TrashCanContainer container, PlayerInventory inventory, ITextComponent title)
  {
    super(container, inventory, title);
  }
  
  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
  {
    GlStateManager.color4f(1, 1, 1, 1);
    minecraft.getTextureManager().bindTexture(GUI);
    int xp = guiLeft;
    int yp = guiTop;
    
    blit(xp, yp, 0, 0, xSize, ySize);
  }
}
