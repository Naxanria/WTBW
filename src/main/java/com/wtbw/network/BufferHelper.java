package com.wtbw.network;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.Vec3d;

/*
  @author: Naxanria
*/
public class BufferHelper
{
  public static Vec3d readVec3d(PacketBuffer buffer)
  {
    return new Vec3d
    (
      buffer.readDouble(),
      buffer.readDouble(),
      buffer.readDouble()
    );
  }
  
  public static PacketBuffer writeVec3d(PacketBuffer buffer, Vec3d vec3d)
  {
    buffer.writeDouble(vec3d.x);
    buffer.writeDouble(vec3d.y);
    buffer.writeDouble(vec3d.z);
    
    return buffer;
  }
}
