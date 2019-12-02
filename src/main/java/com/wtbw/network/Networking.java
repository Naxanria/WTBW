package com.wtbw.network;

import com.wtbw.WTBW;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

/*
  @author: Naxanria
*/
public class Networking {
    public static SimpleChannel INSTANCE;
    private static int id = 0;

    protected static int id() {
        return id++;
    }

    public static void registerMessages() {
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(WTBW.MODID, "network"), () -> "1.0", s -> true, s -> true);

        INSTANCE.registerMessage(id(), CycleToolPacket.class, CycleToolPacket::toBytes, CycleToolPacket::new, CycleToolPacket::handle);
    }


}
