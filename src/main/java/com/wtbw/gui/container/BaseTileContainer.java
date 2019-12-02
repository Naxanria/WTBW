package com.wtbw.gui.container;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nullable;

/*
  @author: Naxanria
*/
public abstract class BaseTileContainer<TE extends TileEntity> extends Container {
    public final TE tileEntity;
    public final PlayerInventory playerInventory;
    protected final World world;

    public BaseTileContainer(@Nullable ContainerType<?> type, int id, World world, BlockPos pos, PlayerInventory playerInventory) {
        super(type, id);

        tileEntity = (TE) world.getTileEntity(pos);
        this.playerInventory = playerInventory;

        this.world = world;
    }

    protected int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0; i < amount; i++) {
            addSlot(handler, index, x, y);
            index++;
            x += dx;
        }

        return index;
    }

    protected void addSlot(IItemHandler handler, int index, int x, int y) {
        addSlot(new SlotItemHandler(handler, index, x, y));
    }

    protected int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int verAmount, int dy, int dx) {
        for (int i = 0; i < verAmount; i++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }

        return index;
    }

    protected void layoutPlayerInventorySlots(int leftCol, int topRow) {
        InvWrapper wrapper = new InvWrapper(playerInventory);
        addSlotBox(wrapper, 9, leftCol, topRow, 9, 3, 18, 18);

        topRow += 58;
        addSlotRange(wrapper, 0, leftCol, topRow, 9, 18);
    }

    protected IWorldPosCallable getWorldPos() {
        if (tileEntity == null) {
            return IWorldPosCallable.DUMMY;
        }

        return IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());
    }

    protected boolean canInteractWith(PlayerEntity entity, Block block) {
        return isWithinUsableDistance(getWorldPos(), entity, block);
    }
}
