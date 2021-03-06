package com.wtbw.gui.screen;

import com.mojang.blaze3d.platform.GlStateManager;
import com.wtbw.WTBW;
import com.wtbw.gui.container.VacuumChestContainer;
import com.wtbw.gui.util.GuiUtil;
import com.wtbw.gui.util.RedstoneButton;
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
  protected void init()
  {
    super.init();
    addButton(new RedstoneButton<>(guiLeft - 22 + 4, guiTop + 17, container.tileEntity));
  }
  
  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
  {
    GuiUtil.renderTexture(guiLeft - 22, guiTop, xSize + 22, ySize, 0, 0, 256, 256, GUI);
  }
}
