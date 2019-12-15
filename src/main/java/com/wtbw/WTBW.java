package com.wtbw;

import com.wtbw.block.ModBlocks;
import com.wtbw.client.rendering.RenderManager;
import com.wtbw.config.WTBWConfig;
import com.wtbw.datagen.DataGenerators;
import com.wtbw.keybinds.KeyEventListener;
import com.wtbw.network.Networking;
import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
  @author: Naxanria
*/
@SuppressWarnings("Convert2MethodRef")
@Mod(WTBW.MODID)
public class WTBW
{
  public static final String MODID = "wtbw";
  
  public static final Logger LOGGER = LogManager.getLogger(MODID);
  public static ItemGroup GROUP = new ItemGroup(MODID)
  {
    @Override
    public ItemStack createIcon()
    {
      return new ItemStack(ModBlocks.REDSTONE_TIMER);
    }
  };
  
  public WTBW()
  {
    IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
    
    eventBus.addListener(this::setup);
    
    eventBus.addGenericListener(Block.class, Registrator::registerBlocks);
    eventBus.addGenericListener(Item.class, Registrator::registerItems);
    eventBus.addGenericListener(TileEntityType.class, Registrator::registerTileEntity);
    eventBus.addGenericListener(ContainerType.class, Registrator::registerContainer);
    
    eventBus.addListener(DataGenerators::gatherData);
    
    IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;
    
    DistExecutor.runWhenOn(Dist.CLIENT, () -> () ->
    {
      forgeEventBus.addListener(ClientEventHandler::onTooltip);
//      forgeEventBus.addListener(RenderManager::render);
      forgeEventBus.addListener(RenderManager::renderOutline);
      
      KeyEventListener.registerKeys();
      forgeEventBus.addListener(KeyEventListener::update);
      
      eventBus.addListener(ClientRegistration::onTextureStitch);
      eventBus.addListener(ClientRegistration::onModelBake);
    });
    
    WTBWConfig.register(ModLoadingContext.get());
  
    Flags.itemFiltersLoaded = ModList.get().isLoaded("itemfilters");
    Flags.jeiLoaded = ModList.get().isLoaded("jei");
  }
  
  private void setup(final FMLCommonSetupEvent event)
  {
    Networking.registerMessages();
    
    DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> ClientSetup.init());
  }
}
