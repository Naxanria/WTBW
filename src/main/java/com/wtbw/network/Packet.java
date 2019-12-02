package com.wtbw.network;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/*
  @author: Naxanria
*/
public abstract class Packet {
    public abstract void toBytes(PacketBuffer buffer);

    public abstract void handle(Supplier<NetworkEvent.Context> ctx);

}
