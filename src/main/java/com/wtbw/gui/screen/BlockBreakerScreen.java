package com.wtbw.gui.screen;

import com.mojang.blaze3d.platform.GlStateManager;
import com.wtbw.WTBW;
import com.wtbw.gui.container.BlockBreakerContainer;
import com.wtbw.gui.util.RedstoneButton;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

/*
  @author: Naxanria
*/
public class BlockBreakerScreen extends BaseContainerScreen<BlockBreakerContainer>
{
  public static final ResourceLocation GUI = new ResourceLocation(WTBW.MODID, "textures/gui/basic3x3with1butt.png");
  
  public BlockBreakerScreen(BlockBreakerContainer container, PlayerInventory inventory, ITextComponent title)
  {
    super(container, inventory, title);
  }
  
  @Override
  protected void init()
  {
    super.init();
    addButton(new RedstoneButton<>(guiLeft - 21 + 5, guiTop + 17, container.tileEntity));
  }
  
  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
  {
    GlStateManager.color4f(1, 1, 1, 1);
    minecraft.getTextureManager().bindTexture(GUI);
  
    blit(guiLeft - 21, guiTop, 0, 0, xSize + 21, ySize);
  }
}
