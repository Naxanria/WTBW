package com.wtbw.gui.screen;

import com.mojang.blaze3d.platform.GlStateManager;
import com.wtbw.WTBW;
import com.wtbw.gui.container.VacuumChestContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

/*
  @author: Naxanria
*/
public class VacuumChestScreen extends BaseContainerScreen<VacuumChestContainer>
{
  public static final ResourceLocation GUI = new ResourceLocation(WTBW.MODID, "textures/gui/vacuum_chest.png");
  
  public VacuumChestScreen(VacuumChestContainer container, PlayerInventory inventory, ITextComponent title)
  {
    super(container, inventory, title);
  }
  
  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
  {
    GlStateManager.color4f(1, 1, 1, 1);
    minecraft.getTextureManager().bindTexture(GUI);
    
    blit(guiLeft, guiTop, 0, 0, xSize, ySize);
  }
}
