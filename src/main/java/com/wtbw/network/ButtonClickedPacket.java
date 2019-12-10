package com.wtbw.network;

import com.wtbw.gui.tools.ClickType;
import com.wtbw.tile.util.IButtonHandler;
import net.minecraft.entity.player.ServerPlayerEntity;

import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/*
  @author: Naxanria
*/
public class ButtonClickedPacket extends Packet
{
  public final int buttonID;
  public final BlockPos pos;
  public final ClickType clickType;
  
  public ButtonClickedPacket(int buttonID, BlockPos pos, ClickType clickType)
  {
    this.buttonID = buttonID;
    this.pos = pos;
    this.clickType = clickType;
  }
  
  public ButtonClickedPacket(PacketBuffer buffer)
  {
    buttonID = buffer.readInt();
    pos = buffer.readBlockPos();
    clickType = ClickType.fromByte(buffer.readByte());
  }
  
  @Override
  public void toBytes(PacketBuffer buffer)
  {
    buffer.writeInt(buttonID);
    buffer.writeBlockPos(pos);
    buffer.writeByte(clickType.toByte());
  }
  
  @Override
  public void handle(Supplier<NetworkEvent.Context> ctx)
  {
    ctx.get().enqueueWork(() ->
    {
      ServerPlayerEntity sender = ctx.get().getSender();
      if (sender != null)
      {
        TileEntity te = sender.world.getTileEntity(pos);
        if (te instanceof IButtonHandler)
        {
          ((IButtonHandler) te).handleButton(buttonID, clickType);
        }
      }
    });
    
    ctx.get().setPacketHandled(true);
  }
}
