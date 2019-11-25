package com.wtbw;

import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
  @author: Naxanria
*/
@Mod(WTBW.MODID)
public class WTBW
{
  public static final String MODID = "wtbw";
  
  public static final Logger LOGGER = LogManager.getLogger(MODID);
  
  public WTBW()
  {
    IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
    
    eventBus.addGenericListener(Block.class, Registrator::registerBlocks);
    eventBus.addGenericListener(Item.class, Registrator::registerItems);
    eventBus.addGenericListener(TileEntityType.class, Registrator::registerTileEntity);
    eventBus.addGenericListener(ContainerType.class, Registrator::registerContainer);
  }
  
  public static ItemGroup GROUP = new ItemGroup(MODID)
  {
    @Override
    public ItemStack createIcon()
    {
      return new ItemStack(Items.KNOWLEDGE_BOOK);
    }
  };
}
