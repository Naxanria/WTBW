package com.wtbw.block;

import com.wtbw.WTBW;
import com.wtbw.config.CommonConfig;
import com.wtbw.tile.EntityPusherTileEntity;
import com.wtbw.util.TextComponentBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapeCube;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;
import java.util.List;

/*
  @author: Naxanria
*/
public class PushBlock extends BaseTileBlock<EntityPusherTileEntity>
{
  private static final VoxelShape SHAPE = VoxelShapes.or
  (
    Block.makeCuboidShape(0, 0, 0, 16, 2, 16),
    Block.makeCuboidShape(1, 2, 1, 15, 3, 15)
  );
  
  private final EntityPusherTileEntity.PushMode mode;
  
  public PushBlock(Properties properties, EntityPusherTileEntity.PushMode mode)
  {
    super(properties, (world, state) -> new EntityPusherTileEntity(mode));
    
    this.mode = mode;
  }
  
  @Override
  public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
  {
    return SHAPE;
  }
  
  @Override
  public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
  {
    CommonConfig config = CommonConfig.get();
    String baseKey = WTBW.MODID + ".tooltip." + (mode == EntityPusherTileEntity.PushMode.PUSH ? "pusher_" : "puller_");
    
    tooltip.add(TextComponentBuilder.createTranslated(baseKey + "range", config.pusherRange.get()).aqua().build());
    
    super.addInformation(stack, worldIn, tooltip, flagIn);
  }
}
