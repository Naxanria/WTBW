package com.wtbw.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/*
  @author: Naxanria
*/
public class ColoredRedstoneParticlePacket extends Packet
{
  public final Vec3d pos;
  public final Vec3d motion;
  public final int color;
  
  public ColoredRedstoneParticlePacket(Vec3d pos, Vec3d motion, int color)
  {
    this.pos = pos;
    this.motion = motion;
    this.color = color;
  }
  
  public ColoredRedstoneParticlePacket(PacketBuffer buffer)
  {
    pos = BufferHelper.readVec3d(buffer);
    motion = BufferHelper.readVec3d(buffer);
    color = buffer.readInt();
  }
  
  @Override
  public void toBytes(PacketBuffer buffer)
  {
    BufferHelper.writeVec3d(buffer, pos);
    BufferHelper.writeVec3d(buffer, motion);
    buffer.writeInt(color);
  }
  
  @Override
  public void handle(Supplier<NetworkEvent.Context> ctx)
  {
    if (ctx.get().getDirection().getReceptionSide() == LogicalSide.CLIENT)
    {
      final float a = ((color >> 24) & 0xff) / 256f;
      final float r = ((color >> 16) & 0xff) / 256f;
      final float g = ((color >> 8) & 0xff) / 256f;
      final float b = (color & 0xff) / 256f;
      ctx.get().enqueueWork(() -> Minecraft.getInstance().world.addParticle(new RedstoneParticleData(r, g, b, a), pos.x, pos.y, pos.z, motion.x, motion.y, motion.z));
    }
    ctx.get().setPacketHandled(true);
  }
}
