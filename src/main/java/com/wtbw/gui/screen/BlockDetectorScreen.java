package com.wtbw.gui.screen;

import com.google.common.collect.ImmutableMap;
import com.wtbw.gui.container.BlockDetectorContainer;
import com.wtbw.gui.util.ClickType;
import com.wtbw.gui.util.GuiUtil;
import com.wtbw.network.BufferHelper;
import com.wtbw.tile.BlockBreakerTileEntity;
import com.wtbw.tile.BlockDetectorTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.state.IProperty;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.client.config.GuiButtonExt;

import java.util.Collection;
import java.util.Map;

/*
  @author: Naxanria
*/
public class BlockDetectorScreen extends BaseContainerScreen<BlockDetectorContainer>
{
  private GuiButtonExt currentButton;
  private GuiButtonExt matchButton;
  
  public BlockDetectorScreen(BlockDetectorContainer container, PlayerInventory inventory, ITextComponent title)
  {
    super(container, inventory, title);
  }
  
  @Override
  protected void init()
  {
    super.init();
  
    currentButton = addButton(new GuiButtonExt(guiLeft + 10, guiTop + 10, 60, 22, "Set to current",
      button -> GuiUtil.sendButton(BlockDetectorTileEntity.BUTTON_CURRENT, container.tileEntity.getPos(), ClickType.LEFT)));
    matchButton = addButton(new GuiButtonExt(guiLeft + 10 + 60 + 1, guiTop + 10, 60, 22, "Exact Match",
      button -> GuiUtil.sendButton(BlockDetectorTileEntity.BUTTON_MATCH, container.tileEntity.getPos(), ClickType.LEFT)));
    matchButton.setFGColor(container.tileEntity.isExactMatch() ? 0xff00ff00 : 0xffff0000);
  }
  
  @Override
  public void tick()
  {
    super.tick();
    if (matchButton != null)
    {
      matchButton.setFGColor(container.tileEntity.isExactMatch() ? 0xff00ff00 : 0xffff0000);
    }
  }
  
  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
  {
//    GuiUtil.renderTexture();
    fill(guiLeft, guiTop, guiLeft + xSize, guiTop + ySize, 0xff888888);
//    drawString(font, "Target: " + container.tileEntity.getTarget().toString(), guiLeft + 10, guiTop + 10 + 22 + 1, 0xffffffff);
    drawBlockState(guiLeft + 10, guiTop + 10 + 22 + 1, container.tileEntity.getTarget());
    drawString(font, container.tileEntity.getPower() + "", guiLeft + 10, guiTop + ySize - 10, 0xff8888FF);
  }
  
  private void drawBlockState(int x, int y, BlockState state)
  {
    Block block = state.getBlock();
    drawString(font, I18n.format(block.getTranslationKey()), x, y, 0xffffffff);
    ImmutableMap<IProperty<?>, Comparable<?>> values = state.getValues();
    int yp = y + 10;
    int xp = x + 10;
    for(Map.Entry<IProperty<?>, Comparable<?>> entry : values.entrySet())
    {
      IProperty<?> property = entry.getKey();
      String name = property.getName();
      String value = BufferHelper.getName(property, entry.getValue());
      
      drawString(font, name + "=" + value, xp, yp, 0xffffffff);
      yp += 10;
    }
  }
}
