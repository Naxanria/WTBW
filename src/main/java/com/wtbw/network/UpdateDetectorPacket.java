package com.wtbw.network;

import com.wtbw.tile.BlockDetectorTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/*
  @author: Naxanria
*/
public class UpdateDetectorPacket extends Packet
{
  public final BlockPos pos;
  public final BlockState state;
  
  public UpdateDetectorPacket(BlockPos pos, BlockState state)
  {
    this.pos = pos;
    this.state = state;
  }
  
  public UpdateDetectorPacket(PacketBuffer buffer)
  {
    pos = buffer.readBlockPos();
    state = BufferHelper.readState(buffer);
  }
  
  @Override
  public void toBytes(PacketBuffer buffer)
  {
    buffer.writeBlockPos(pos);
    BufferHelper.writeState(buffer, state);
  }
  
  @Override
  public void handle(Supplier<NetworkEvent.Context> ctx)
  {
    ctx.get().enqueueWork(() ->
    {
      if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT)
      {
        TileEntity tileEntity = Minecraft.getInstance().world.getTileEntity(pos);
        if (tileEntity != null)
        {
          if (tileEntity instanceof BlockDetectorTileEntity)
          {
            ((BlockDetectorTileEntity) tileEntity).setTarget(state);
          }
        }
      }
    });
    ctx.get().setPacketHandled(true);
  }
}
