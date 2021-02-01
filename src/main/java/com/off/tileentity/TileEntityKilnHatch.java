package com.off.tileentity;

import com.off.blocks.machines.BlockKilnHatch;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityKilnHatch extends TileEntity implements ITickable
{
	private BlockPos corePos;
	
	private TileEntityKilnCore getMachineCore(World worldIn, BlockPos pos)
	{
		EnumFacing facing = worldIn.getBlockState(pos).getValue(BlockHorizontal.FACING);
		
		if (facing == EnumFacing.NORTH)
		{
			TileEntity coreEntity = worldIn.getTileEntity(pos.add(0, 0, 3));
			if (coreEntity instanceof TileEntityKilnCore)
			{
				if (((TileEntityKilnCore) coreEntity).isStructureValid(worldIn))
				{
					return (TileEntityKilnCore)coreEntity;
				}
				else
				{
					return null;
				}
			}
		}
		if (facing == EnumFacing.SOUTH)
		{
			TileEntity coreEntity = worldIn.getTileEntity(pos.add(0, 0, -3));
			if (coreEntity instanceof TileEntityKilnCore)
			{
				if (((TileEntityKilnCore) coreEntity).isStructureValid(worldIn))
				{
					return (TileEntityKilnCore)coreEntity;
				}
				else
				{
					return null;
				}
			}
		}
		if (facing == EnumFacing.WEST)
		{
			TileEntity coreEntity = worldIn.getTileEntity(pos.add(3, 0, 0));
			if (coreEntity instanceof TileEntityKilnCore)
			{
				if (((TileEntityKilnCore) coreEntity).isStructureValid(worldIn))
				{
					return (TileEntityKilnCore)coreEntity;
				}
				else
				{
					return null;
				}
			}
		}
		if (facing == EnumFacing.EAST)
		{
			TileEntity coreEntity = worldIn.getTileEntity(pos.add(-3, 0, 0));
			if (coreEntity instanceof TileEntityKilnCore)
			{
				if (((TileEntityKilnCore) coreEntity).isStructureValid(worldIn))
				{
					return (TileEntityKilnCore)coreEntity;
				}
				else
				{
					return null;
				}
			}
		}
		return null;
	}
	
	@Override
	public void update()
	{
		if (corePos == null)
		{
			return;
		}
		
		if (!world.isRemote)
		{
			BlockKilnHatch.updateBlockState(false, this.world, pos);
		}
	}
}
