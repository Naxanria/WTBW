package com.wtbw.block.redstone;

import com.wtbw.WTBW;
import com.wtbw.block.BaseTileBlock;
import com.wtbw.config.CommonConfig;
import com.wtbw.tile.redstone.RedstoneTimerTileEntity;
import com.wtbw.util.TextComponentBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;
import java.util.List;

/*
  @author: Naxanria
*/
public class RedstoneTimerBlock extends BaseTileBlock<RedstoneTimerTileEntity>
{
  public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

  public RedstoneTimerBlock(Properties properties)
  {
    super(properties, (world, state) -> new RedstoneTimerTileEntity());

    setDefaultState(stateContainer.getBaseState().with(ACTIVE, false));
  }

  public int getStrongPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side)
  {
    return 0;
  }

  public int getWeakPower(BlockState state, IBlockReader world, BlockPos pos, Direction side)
  {
    RedstoneTimerTileEntity tileEntity = getTileEntity(world, pos);
    if (tileEntity != null)
    {
      return tileEntity.getPower();
    }

    return 0;
  }

  @Override
  protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
  {
    builder.add(ACTIVE);
  }

  @Override
  public boolean canProvidePower(BlockState state)
  {
    return true;
  }
  
  @Override
  public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
  {
    String baseKey = WTBW.MODID + ".tooltip.timer";
    tooltip.add(TextComponentBuilder.createTranslated(baseKey, CommonConfig.get().redstoneClockRepeat.get()).aqua().build());
    
    super.addInformation(stack, worldIn, tooltip, flagIn);
  }
}
