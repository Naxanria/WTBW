package com.wtbw.gui.screen;

import com.mojang.blaze3d.platform.GlStateManager;
import com.wtbw.gui.container.TieredFurnaceContainer;
import com.wtbw.tile.furnace.BaseFurnaceTileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

/*
  @author: Naxanria
*/
public class TieredFurnaceScreen extends BaseContainerScreen<TieredFurnaceContainer> {
    public static final ResourceLocation GUI = new ResourceLocation("minecraft", "textures/gui/container/furnace.png");

    private final BaseFurnaceTileEntity furnace;

    public TieredFurnaceScreen(TieredFurnaceContainer container, PlayerInventory inventory, ITextComponent title) {
        super(container, inventory, title);

        furnace = container.tileEntity;


    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1, 1, 1, 1);
        minecraft.getTextureManager().bindTexture(GUI);
        int xp = guiLeft;
        int yp = guiTop;

        blit(xp, yp, 0, 0, xSize, ySize);
        if (furnace.isBurning()) {
            float progress = furnace.getBurnTime() / (float) furnace.getBurnTimeTotal();
            int l = (int) (progress * 14);
            blit(xp + 56, yp + 36 + 12 - l, 176, 12 - l, 14, l + 1);
        }

        float progress = furnace.getCookTime() / (float) furnace.getCookTimeTotal();
        int l = (int) (progress * 24);
        this.blit(xp + 79, yp + 34, 176, 14, l + 1, 16);
    }
}
